package me.anton.z_bank.DAO;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.UUID;

@IgnoreExtraProperties
public class User {
    private String id;
    private String userName;
    private String password;
    private double balance;
    private double savingsBalance;
    private ArrayList<String> friendsUserNames = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public User(String userName, String password, double balance, double savingsBalance){
        this.userName = userName;
        this.password = password;
        this.balance = balance;
        this.savingsBalance = savingsBalance;
        this.id = UUID.randomUUID().toString();
    }

    public User(){}
    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public double getSavingsBalance() {
        return savingsBalance;
    }

    public ArrayList<String> getFriendsUserNames() {
        return friendsUserNames;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public String getPassword() {
        return password;
    }
}
