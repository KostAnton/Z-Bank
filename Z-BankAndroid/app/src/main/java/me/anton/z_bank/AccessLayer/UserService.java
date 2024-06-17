package me.anton.z_bank.AccessLayer;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import me.anton.z_bank.DAO.Transaction;
import me.anton.z_bank.DAO.User;
import me.anton.z_bank.MainActivity;
import me.anton.z_bank.callbacks.UserCallback;

/**
 * UserService is a service class that extends FirebaseRepository and
 * provides functionality for user registration and retrieval of user data.
 */
public class UserService extends FirebaseRepository {

    /**
     * Registers a new user by saving the user details to the Firebase database.
     *
     * @param name The name of the user to be registered.
     * @param pass The password of the user to be registered.
     */
    public static void registerNewUser(String name, String pass) {
        System.out.println("Added");
        firebaseDatabase.child("Users").child(name).setValue(new User(name, pass, 0, 0));
    }

    public static void addFriend(String name, String friendName){
        DatabaseReference friends = firebaseDatabase.child("Users").child(name).child("friends");
        friends.child(friendName).setValue(true);
    }

    public static void makeTransaction(Transaction t){
        t.transact();
        firebaseDatabase.child("Users").child(t.getFrom().getUserName()).setValue(t.getFrom());
    }
    /**
     * Retrieves user data from the Firebase database based on the user's name.
     *
     * @param name The name of the user whose data is to be retrieved.
     * @return The User object containing the user's data.
     */
    public static void getUserData(String name, UserCallback callback) {
        firebaseDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dt : dataSnapshot.getChildren()){
                    User user = dt.getValue(User.class);
                    if(user.getUserName().equals(name))
                        callback.onUserFound(user);
                }
//                callback.onUserNotFound();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
