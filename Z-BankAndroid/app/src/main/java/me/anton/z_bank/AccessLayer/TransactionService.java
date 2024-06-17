package me.anton.z_bank.AccessLayer;

import java.util.UUID;

import me.anton.z_bank.DAO.Transaction;

/**
 * TransactionService is a service class that extends FirebaseRepository and
 * provides functionality to make transactions by saving them to Firebase.
 */
public class TransactionService extends FirebaseRepository {

    /**
     * Makes a transaction by saving it to the Firebase database.
     * A new unique ID is generated for each transaction.
     *
     * @param transaction The transaction object to be saved.
     */
    public static void makeTransaction(Transaction transaction) {
        firebaseDatabase.child("Transactions").child(UUID.randomUUID().toString()).setValue(transaction);
    }
}
