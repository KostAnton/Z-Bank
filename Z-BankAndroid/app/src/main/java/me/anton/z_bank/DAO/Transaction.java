package me.anton.z_bank.DAO;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.UUID;

import me.anton.z_bank.AccessLayer.FirebaseRepository;
import me.anton.z_bank.AccessLayer.TransactionService;

@IgnoreExtraProperties
public class Transaction {

    /** The user initiating the transaction (sender). */
    private User from;

    /** The user receiving the transaction (receiver). */
    private User to;

    /** The amount of the transaction. */
    private double amount;

    /** Unique identifier for the transaction. */
    private String id;

    /**
     * Default constructor for the Transaction class.
     * Used for firebase purposes
     */
    public Transaction() {}

    /**
     * Constructs a new Transaction with the specified parameters.
     *
     * @param from    The user initiating the transaction (sender).
     * @param to      The user receiving the transaction (receiver).
     * @param amount  The amount of the transaction.
     */
    public Transaction(User from, User to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Initiates the transaction, making the necessary updates in the system.
     * Calls an API layer to execute the transaction and updates the transaction lists for both sender and receiver.
     */
    public void transact() {
        TransactionService.makeTransaction(this);
        this.from.getTransactions().add(this);
        this.to.getTransactions().add(this);
    }

    /**
     * Returns the user initiating the transaction (sender).
     *
     * @return The user initiating the transaction (sender).
     */
    public User getFrom() {
        return from;
    }

    /**
     * Returns the user receiving the transaction (receiver).
     *
     * @return The user receiving the transaction (receiver).
     */
    public User getTo() {
        return to;
    }

    /**
     * Returns the amount of the transaction.
     *
     * @return The amount of the transaction.
     */
    public double getAmount() {
        return amount;
    }
}
