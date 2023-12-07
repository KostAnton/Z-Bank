package me.anton.z_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import me.anton.z_bank.AccessLayer.APIAccessLayer;
import me.anton.z_bank.DAO.Transaction;
import me.anton.z_bank.DAO.User;


public class AccountInfoActivity extends AppCompatActivity {
    TextView nameDisplay;
    TextView weatherDisplay;
    TextView balanceDisplay;
    TextView lastTransactionDisplay;
    TextView savingsDisplay;
    Button transactionBtn;
    Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        nameDisplay = findViewById(R.id.nameDisplay);
        weatherDisplay = findViewById(R.id.weatherDisplay);
        balanceDisplay = findViewById(R.id.balanceDisplay);
        lastTransactionDisplay = findViewById(R.id.lastTransactionDisplay);
        savingsDisplay = findViewById(R.id.savingsDisplay);
        transactionBtn = findViewById(R.id.transactionBtn);
        settingsBtn = findViewById(R.id.settingsBtn);

        User curr = MainActivity.currentUser;
        Transaction lastTransaction = null;
        try {
            lastTransaction = curr.getTransactions().get(curr.getTransactions().size() - 1);
        }catch(ArrayIndexOutOfBoundsException e){}

        nameDisplay.setText(curr.getUserName().replace(" ", "\n"));
        weatherDisplay.setText(APIAccessLayer.getCurrentWeather());
        balanceDisplay.setText(curr.getBalance() + "$");
        if(lastTransaction == null){
            lastTransactionDisplay.setText("Waiting On Your First Transaction");
        }else {
            lastTransactionDisplay.setText("Last Transaction" + "\n" + lastTransaction.getAmount() + " " + (lastTransaction.getFrom() == curr ? "to "+lastTransaction.getTo() : "from "+lastTransaction.getFrom()));
        }
        savingsDisplay.setText("Savings: \n" +curr.getSavingsBalance());

        transactionBtn.setOnClickListener(view -> {
            Intent i = new Intent(AccountInfoActivity.this, FriendsListActivity.class);
            startActivity(i);
        });

    }
}