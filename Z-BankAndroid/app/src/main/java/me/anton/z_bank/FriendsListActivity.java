package me.anton.z_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;

import me.anton.z_bank.AccessLayer.FirebaseRepository;
import me.anton.z_bank.AccessLayer.UserService;
import me.anton.z_bank.DAO.Transaction;
import me.anton.z_bank.DAO.User;
import me.anton.z_bank.callbacks.UserCallback;

public class FriendsListActivity extends AppCompatActivity {
    ListView friendsList;
    Button addFriendBtn;
    TextView nameDisplay;
    TextView balanceTop;
    TextView savingsTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        friendsList = findViewById(R.id.friendsList);
        refreshList();
        addFriendBtn = findViewById(R.id.addFriendBtn);
        nameDisplay = findViewById(R.id.nameDisplay);
        balanceTop = findViewById(R.id.balanceTop);
        savingsTop = findViewById(R.id.savingsTop);

        nameDisplay.setText(MainActivity.currentUser.getUserName());
        balanceTop.setText(MainActivity.currentUser.getBalance() + "$");
        savingsTop.setText(MainActivity.currentUser.getSavingsBalance()+ "$");

        friendsList.setOnItemClickListener((adapterView, view, i, l) -> {
            showDialog(MainActivity.currentUser.getFriendsUserNames().get(i).trim());
        });

        addFriendBtn.setOnClickListener(view -> {
            Dialog d = new Dialog(FriendsListActivity.this);
            d.setContentView(R.layout.add_friend_dialog);

            EditText friend = d.findViewById(R.id.friendToAdd);
            Button addFriendBtn = d.findViewById(R.id.addFriend);

            addFriendBtn.setOnClickListener(view1 -> {
                UserService.getUserData(friend.getText().toString(), new UserCallback() {
                    @Override
                    public void onUserFound(User user) {
                        MainActivity.currentUser.getFriendsUserNames().add(user.getUserName());
                        Toast.makeText(FriendsListActivity.this, "Friend added", Toast.LENGTH_SHORT).show();
                        UserService.addFriend(MainActivity.currentUser.getUserName(),user.getUserName());
                        refreshList();
                        d.dismiss();
                    }

                    @Override
                    public void onUserNotFound() {
                        Toast.makeText(FriendsListActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    }

                    @Override
                    public void onFailure(DatabaseError error) {} //Checked With Broadcast
                });
            });
            d.show();
        });
    }

    private void refreshList(){
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.currentUser.getFriendsUserNames());
        friendsList.setAdapter(namesAdapter);
    }

    private void showDialog(String userName){
        Dialog dialog = new Dialog(FriendsListActivity.this);
        dialog.setContentView(R.layout.transaction_dialog);

        TextView toDisplay = dialog.findViewById(R.id.toDisplay);
        EditText amountEt = dialog.findViewById(R.id.amountEt);
        Button sendBtn = dialog.findViewById(R.id.sendBtn);

        toDisplay.setText(userName);

        sendBtn.setOnClickListener(view -> {
            if(amountEt.getText().toString().isEmpty()){
                dialog.dismiss();
            }

            User[] userFound = new User[1];
            System.out.println(userName);
            UserService.getUserData(userName, new UserCallback() {
                @Override
                public void onUserFound(User user) {
                    userFound[0] = user;
                }

                @Override
                public void onUserNotFound() {
                    System.out.println("NOT FOUND");
                } //Garanteed to be found

                @Override
                public void onFailure(DatabaseError error) {} //Handaled in BRC
            });

            if(MainActivity.currentUser.getBalance() < Double.parseDouble(amountEt.getText().toString())){
                Toast.makeText(FriendsListActivity.this, "Too low balance", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }else{
                new Transaction(MainActivity.currentUser, userFound[0], Double.parseDouble(amountEt.getText().toString())).transact(); //The transaction is automatically stored in both ends of the transaction
                sendBtn.setEnabled(false);
                amountEt.setVisibility(View.INVISIBLE);
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
                        toDisplay.setText(loadingAnim[(int) ((5000 - l) / 100) % loadingAnim.length]);
                    }

                    @Override
                    public void onFinish() {
//                    dialog.dismiss();
                    }
                }.start();
            }


        });

        dialog.show();
    }
}