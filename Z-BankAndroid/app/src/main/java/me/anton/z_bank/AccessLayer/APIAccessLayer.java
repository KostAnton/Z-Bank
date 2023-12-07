package me.anton.z_bank.AccessLayer;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;

import me.anton.z_bank.DAO.Transaction;
import me.anton.z_bank.DAO.User;

public class APIAccessLayer {

    private static DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    public APIAccessLayer(){}

    public static boolean verifyLogin(String name, String pass){

        return false;
    }

    public static User getUserData(String name){
     return null;
    }

    public static void registerNewUser(String name, String pass, String email){

    }

    public static String generateResponse(String id){
        return null;
    }

    public static String getCurrentWeather(){
        return null;
    }

    public static void makeTransaction(Transaction transaction){

    }

    //
}
