package me.anton.z_bank.callbacks;

import com.google.firebase.database.DatabaseError;

import me.anton.z_bank.DAO.User;

public interface UserCallback {
    void onUserFound(User user);

    void onUserNotFound();
    void onFailure(DatabaseError error);
}
