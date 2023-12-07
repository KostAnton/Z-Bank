package me.anton.z_bank.DAO;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.UUID;

import me.anton.z_bank.AccessLayer.APIAccessLayer;

@IgnoreExtraProperties
public class Transaction {
    private User from;
    private User to;
    private double amount;
    private String id;

    public Transaction(){}

    public Transaction(User from, User to, double amount){
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.id = UUID.randomUUID().toString();
    }

    public void transact(){
        APIAccessLayer.makeTransaction(this);
        this.from.getTransactions().add(this);
        this.to.getTransactions().add(this);
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public double getAmount() {
        return amount;
    }
}
