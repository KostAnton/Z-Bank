package me.anton.z_bank.AccessLayer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.anton.z_bank.DAO.Transaction;
import me.anton.z_bank.DAO.User;

public class FirebaseRepository {

    protected static DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    protected FirebaseRepository(){}
}
