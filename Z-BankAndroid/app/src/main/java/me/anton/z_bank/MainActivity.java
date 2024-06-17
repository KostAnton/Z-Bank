package me.anton.z_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.anton.z_bank.AccessLayer.UserService;
import me.anton.z_bank.DAO.User;
import me.anton.z_bank.callbacks.UserCallback;


public class MainActivity extends AppCompatActivity {
    static User currentUser;
    static DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    EditText userField;
    EditText passField;
    Button loginBtn;
    Button registerBtn;

    TextView loadingText;

    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userField = findViewById(R.id.userField);
        passField = findViewById(R.id.passField);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        loadingText = findViewById(R.id.loadingText);
        logo = findViewById(R.id.logo);


        userField.setVisibility(View.INVISIBLE);
        passField.setVisibility(View.INVISIBLE);
        loginBtn.setText("");
        registerBtn.setText("");
        logo.setAlpha(0.1f);

        SharedPreferences sh = getSharedPreferences("sessionControl", MODE_PRIVATE);
        new CountDownTimer(5000, 100){

            @Override
            public void onTick(long l) {
                String[] loadingAnim = new String[8];
                loadingAnim[0] = "-";
                loadingAnim[1] = "\\";
                loadingAnim[2] = "|";
                loadingAnim[3] = "/";
                loadingAnim[4] = "-";
                loadingAnim[5] = "\\";
                loadingAnim[6] = "|";
                loadingAnim[7] = "/";
                loadingText.setText(loadingAnim[(int) ((5000 - l) / 100) % loadingAnim.length]);
            }
            @Override
            public void onFinish() {

                Toast.makeText(MainActivity.this, "Looking for network", Toast.LENGTH_LONG).show();

                ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(MainActivity.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if(!netInfo.isConnected()){
                    loadingText.setText("Not Connected To Internet");
                    return;
                }else{
                    Toast.makeText(MainActivity.this, "Network Found", Toast.LENGTH_LONG).show();

                }

                userField.setText(sh.getString("user", ""));
                passField.setText(sh.getString("pass", ""));

                if (sh.getString("user", null) == null) {
                    Toast.makeText(MainActivity.this, "Session Not Found...", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "Found Session...", Toast.LENGTH_LONG).show();

                userField.setVisibility(View.VISIBLE);
                passField.setVisibility(View.VISIBLE);
                loginBtn.setText("LOG IN");
                registerBtn.setText("REGISTER");
                logo.setAlpha(1f);
                loadingText.setText("");
            }
        }.start();




        loginBtn.setOnClickListener(view -> {
           UserService.getUserData(userField.getText().toString(), new UserCallback() {
               @Override
               public void onUserFound(User user) {
                   if(!user.getPassword().equals(passField.getText().toString())){
                       Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                       return;
                   }
                   SharedPreferences.Editor edit = sh.edit();
                   edit.putString("user", userField.getText().toString());
                   edit.putString("pass", passField.getText().toString());

                   edit.apply();

                   MainActivity.currentUser = user;
                   Intent i = new Intent(MainActivity.this, AccountInfoActivity.class);
                   startActivity(i);
               }

               @Override
               public void onUserNotFound() {
                   Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onFailure(DatabaseError error) {}
           });

        });

        registerBtn.setOnClickListener(view -> {

            Dialog registerDialog = new Dialog(this);
            registerDialog.setContentView(R.layout.register_dialog);

            EditText registerUser = registerDialog.findViewById(R.id.friendToAdd);
            EditText registerPass = registerDialog.findViewById(R.id.registerPass);
            Button registerBtn2 = registerDialog.findViewById(R.id.addFriend);

            registerBtn2.setOnClickListener(v -> {
                String userName = registerUser.getText().toString();
                String pass = registerPass.getText().toString();

//                if(userName.isEmpty() || !(userName.length() < 3)
//                        || pass.isEmpty() || !pass.matches("\n" +
//                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$\n")){
//                    Toast.makeText(registerDialog.getContext(), "Your username should be longer or the password is missing a capital or special char", Toast.LENGTH_LONG).show();
//                    registerDialog.dismiss();
//                }

                db.child("Users").orderByChild("userName").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Username exists, handle duplication
                            Toast.makeText(registerDialog.getContext(), "Username already exists", Toast.LENGTH_LONG).show();
                        }else {
                            // Username does not exist! Register

                            UserService.registerNewUser(userName, pass);
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


                UserService.registerNewUser(userName,pass);
                Toast.makeText(registerDialog.getContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                registerDialog.dismiss();
            });

            registerDialog.show();

        });

    }
}