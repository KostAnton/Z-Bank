package me.anton.z_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import me.anton.z_bank.AccessLayer.APIAccessLayer;
import me.anton.z_bank.DAO.Transaction;

public class FriendsListActivity extends AppCompatActivity {
    ListView friendsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        friendsList = findViewById(R.id.friendsList);
        refreshList();

        friendsList.setOnItemClickListener((adapterView, view, i, l) -> {
            showDialog(MainActivity.currentUser.getFriendsUserNames().get(i));
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
                return;
            }

            new Transaction(MainActivity.currentUser, APIAccessLayer.getUserData(userName), Double.parseDouble(amountEt.getText().toString())).transact(); //The transaction is automatically stored in both ends of the transaction

            dialog.dismiss();
        });

        dialog.show();
    }
}