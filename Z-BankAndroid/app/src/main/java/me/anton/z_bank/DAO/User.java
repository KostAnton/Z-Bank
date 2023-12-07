package me.anton.z_bank.DAO;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.UUID;

@IgnoreExtraProperties
/**
 * The User class represents a user in a banking system.
 * Each user has a unique identifier, a username, password, balance, savings balance,
 * a list of friends' usernames, and a list of transactions.
 */
public class User {

    /** Unique identifier for the user. */
    private String id;

    /** User's username. */
    private String userName;

    /** User's password. */
    private String password;

    /** Current balance in the user's main account. */
    private double balance;

    /** Current balance in the user's savings account. */
    private double savingsBalance;

    /** List of usernames of friends associated with the user. */
    private ArrayList<String> friendsUserNames = new ArrayList<>();

    /** List of transactions associated with the user. */
    private ArrayList<Transaction> transactions = new ArrayList<>();

    /**
     * Constructs a new User with the specified parameters.
     *
     * @param userName        The username for the user.
     * @param password        The password for the user.
     * @param balance         The initial balance in the user's main account.
     * @param savingsBalance  The initial balance in the user's savings account.
     */
    public User(String userName, String password, double balance, double savingsBalance) {
        this.userName = userName;
        this.password = password;
        this.balance = balance;
        this.savingsBalance = savingsBalance;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Default constructor for the User class.
     * Used for firebase purposes
     */
    public User() {}

    /**
     * Returns the unique identifier of the user.
     *
     * @return The unique identifier of the user.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the current balance in the user's main account.
     *
     * @return The current balance in the user's main account.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the current balance in the user's savings account.
     *
     * @return The current balance in the user's savings account.
     */
    public double getSavingsBalance() {
        return savingsBalance;
    }

    /**
     * Returns the list of usernames of friends associated with the user.
     *
     * @return The list of usernames of friends associated with the user.
     */
    public ArrayList<String> getFriendsUserNames() {
        return friendsUserNames;
    }

    /**
     * Returns the username of the user.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the list of transactions associated with the user.
     *
     * @return The list of transactions associated with the user.
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }
}
