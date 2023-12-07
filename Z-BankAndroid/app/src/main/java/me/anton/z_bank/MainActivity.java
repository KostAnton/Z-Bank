package me.anton.z_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.anton.z_bank.AccessLayer.APIAccessLayer;
import me.anton.z_bank.DAO.User;

public class MainActivity extends AppCompatActivity {
    static  User currentUser;
    static DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    EditText userField;
    EditText passField;
    Button loginBtn;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userField = findViewById(R.id.userField);
        passField = findViewById(R.id.passField);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(view -> {
            db.child("Users").orderByChild("userName").equalTo(userField.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Username exists, handle duplication
                        if(!dataSnapshot.getValue(User.class).getPassword().equals(passField.getText().toString())){
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                            return;
                        }
                        MainActivity.currentUser = dataSnapshot.getValue(User.class);
                        Intent i = new Intent(MainActivity.this, AccountInfoActivity.class);
                        startActivity(i);
                    } else {
                        // Username doesn't exist, it's unique
                        Toast.makeText(MainActivity.this, "Wrong Account Details", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors here
                    Log.e("Firebase", "Error checking on login", databaseError.toException());
                }
            });
//           if(userField.getText().toString().length() != 0 && passField.getText().toString().length() != 0){
//               if(APIAccessLayer.verifyLogin(userField.getText().toString(), passField.getText().toString())){
//                   currentUser = APIAccessLayer.getUserData(userField.getText().toString());
//                   //Moving to Account Info activity
//                   Intent i = new Intent(MainActivity.this, AccountInfoActivity.class);
//                   startActivity(i);
//               }
//           }
        });

        registerBtn.setOnClickListener(view -> {

            Dialog registerDialog = new Dialog(this);
            registerDialog.setContentView(R.layout.register_dialog);

            EditText registerUser = registerDialog.findViewById(R.id.registerUser);
            EditText registerPass = registerDialog.findViewById(R.id.registerPass);
            EditText registerBtn2 = registerDialog.findViewById(R.id.registerBtn2);

            registerBtn2.setOnClickListener(v -> {
                String userName = registerUser.getText().toString();
                String pass = registerPass.getText().toString();

                if(userName.isEmpty() || !(userName.length() < 3)
                        || pass.isEmpty() || !pass.matches("^(?=.*[A-Z])(?=.*\\\\d)(?=.*[!@#$%^&*()-_=+\\\\\\\\|[{]};:'\\\",<.>/?]).+$")){
                    Toast.makeText(registerDialog.getContext(), "Your username should be longer or the password is missing a capital or special char", Toast.LENGTH_LONG).show();
                    registerDialog.dismiss();
                    return;
                }

                db.child("Users").orderByChild("userName").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Username exists, handle duplication
                            Toast.makeText(registerDialog.getContext(), "Username already exists", Toast.LENGTH_LONG).show();
                        }else {
                            // Username does not exist! Register
                            User newUser = new User(userName,pass,0,0);
                            db.child("Users").child(userName).setValue(newUser);
                            Toast.makeText(registerDialog.getContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                            registerDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle errors here
                        Log.e("Firebase", "Error checking on register", databaseError.toException());
                    }
                });


//                APIAccessLayer.registerNewUser(userName,pass,mail);
//                Toast.makeText(registerDialog.getContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
//                registerDialog.dismiss();
            });

            registerDialog.show();

        });

    }
}