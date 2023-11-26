package sdf.day02.shoppingcart;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.List;

public class BankAccount {
    private final String accountHolderName;  // Read-only property
    private final String accountNumber;  // Read-only property
    private float accountBalance;  // Current balance
    private List<String> transactions;  // List to store transaction history
    private boolean closed;  // Indicates if the account is closed
    private final Date accountCreationDate;  // Date of account creation
    private Date accountClosingDate;  // Date of account closure

    // Constructor with the account holder's name (balance initialized to 0)
    public BankAccount(String accountHolderName) {
        this(accountHolderName, 0.0f);
    }

    // Constructor with account holder's name and initial balance
    public BankAccount(String accountHolderName, float initialBalance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = generateAccountNumber(); // Generate a random account number
        this.accountBalance = initialBalance;
        this.transactions = new ArrayList<>(); // Initialize the list to store transactions
        this.closed = false;  // Account is not closed initially
        this.accountCreationDate = new Date(); // Set the creation date
        this.accountClosingDate = null;  // No closure date initially
    }

    // Getter for account holder's name
    public String getAccountHolderName() {
        return accountHolderName;
    }

    // Getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter for account balance
    public float getAccountBalance() {
        return accountBalance;
    }

    // Getter for transaction history
    public List<String> getTransactions() {
        return transactions;
    }

    // Getter for the account closure status
    public boolean isClosed() {
        return closed;
    }

    // Getter for the account creation date
    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    // Getter for the account closing date
    public Date getAccountClosingDate() {
        return accountClosingDate;
    }

    // Deposit method to add funds to the account
    public void deposit(float amount) {
        if (amount > 0 && !closed) {
            accountBalance += amount;  // Increase the balance
            transactions.add("Deposit $" + amount + " at " + new Date()); // Add transaction to the list
        } else {
            throw new IllegalArgumentException("Invalid deposit amount or account is closed.");
        }
    }

    // Withdraw method to deduct funds from the account
    public void withdraw(float amount) {
        if (amount > 0 && !closed) {
            if (accountBalance >= amount) {
                accountBalance -= amount;  // Deduct the amount from the balance
                transactions.add("Withdraw $" + amount + " at " + new Date()); // Add transaction
            } else {
                throw new IllegalArgumentException("Insufficient funds.");
            }
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount or account is closed.");
        }
    }

    // Close the account
    public void closeAccount() {
        if (!closed) {
            closed = true;  // Mark the account as closed
            accountClosingDate = new Date();  // Set the closing date
        } else {
            throw new IllegalStateException("Account is already closed.");
        }
    }

    // Private method to generate a random account number 
    private String generateAccountNumber() {
        return "ACCT" + Math.random();
    }
}
public class FixedDepositAccount extends BankAccount {
        private float interest;  // Interest rate
        private int durationInMonths;  // Duration of the fixed deposit
        private boolean interestChanged = false;  // Flag to track interest changes
        private boolean durationChanged = false;  // Flag to track duration changes
    
        // Constructor with account holder's name, initial balance, default interest (3.0), & default duration (6 months).
        public FixedDepositAccount(String accountHolderName, float initialBalance) {
            this(accountHolderName, initialBalance, 3.0f, 6);
        }
    
        // Constructor with account holder's name, balance, and interest
        public FixedDepositAccount(String accountHolderName, float initialBalance, float interest) {
            this(accountHolderName, initialBalance, interest, 6);
        }
    
        // Constructor with account holder's name, balance, interest, and duration
        public FixedDepositAccount(String accountHolderName, float initialBalance, float interest, int duration) {
            super(accountHolderName, initialBalance);  // Call the parent class (BankAccount) constructor
            setInterest(interest);
            setDurationInMonths(duration);
        }
    
        // Getter for interest rate
        public float getInterest() {
            return interest;
        }
    
        // Getter for duration in months
        public int getDurationInMonths() {
            return durationInMonths;
        }
    
        // Setter for interest rate (can only be set once)
        public void setInterest(float interest) {
            if (!interestChanged) {
                this.interest = interest;
                interestChanged = true;
            } else {
                throw new IllegalArgumentException("Interest rate can only be set once.");
            }
        }
    
        // Setter for duration (can only be set once)
        public void setDurationInMonths(int duration) {
            if (!durationChanged) {
                this.durationInMonths = duration;
                durationChanged = true;
            } else {
                throw new IllegalArgumentException("Duration can only be set once.");
            }
        }
    
        // Override the getAccountBalance method to include interest
        @Override
        public float getAccountBalance() {
            // Return the balance plus interest
            return super.getAccountBalance() + (super.getAccountBalance() * interest / 100);
        }
    
        // Override deposit method (deposits are not allowed for fixed deposit accounts)
        @Override
        public void deposit(float amount) {
                throw new UnsupportedOperationException("Deposits are not allowed for Fixed Deposit Accounts.");
        }
        
        // // Override withdraw method (withdraw are not allowed for fixed deposit accounts)
        @Override
        public void withdraw(float amount) {
                throw new UnsupportedOperationException("Withdrawals are not allowed for Fixed Deposit Accounts.");
        }
}

// The provided Java code defines two classes, BankAccount and FixedDepositAccount, which model a basic bank account and a fixed deposit account. I'll explain each class and its components in detail:

// BankAccount Class:
// Members:
// accountHolderName (String): This is the name of the account holder. It is set during the creation of the object and marked as final, meaning it cannot be changed once set.
// accountNumber (String): Represents a randomly generated account number. Like accountHolderName, it is set during object creation and marked as final.
// accountBalance (float): Indicates the balance in the account.
// transactions (List<String>): This is a list that keeps track of transactions performed on the account, represented as strings.
// closed (boolean): A flag indicating whether the account is closed.
// accountCreationDate (Date): Stores the date and time when the account was created.
// accountClosingDate (Date): Records the date and time when the account was closed.

// Constructors:
// BankAccount(String accountHolderName): This constructor takes the account holder's name and initializes the balance to 0.0, generating an account number and setting other properties.
// BankAccount(String accountHolderName, float initialBalance): This constructor allows specifying an initial balance in addition to the account holder's name.

// Methods:
// deposit(float amount): This method is used to deposit a positive amount into the account. It updates the accountBalance and adds a transaction string to the transactions list. If the deposit amount is not positive or the account is closed, an IllegalArgumentException is thrown.
// withdraw(float amount): Similar to deposit, this method allows withdrawing a positive amount from the account, updates the balance, and records the transaction. It also checks for valid amounts and the closed status of the account.
// closeAccount(): Closes the account by setting the closed flag to true and recording the closing date. If the account is already closed, an IllegalStateException is thrown.

// Getters:
// getAccountHolderName(): Provides read-only access to the account holder's name.
// getAccountNumber(): Provides read-only access to the account number.
// getAccountBalance(): Returns the current account balance.
// getTransactions(): Returns the list of transactions.
// isClosed(): Returns whether the account is closed.
// getAccountCreationDate(): Provides the creation date of the account.
// getAccountClosingDate(): Provides the closing date if the account is closed.

// Private Method:
// generateAccountNumber(): A private method (not exposed to external code) for generating a random account number. In the provided code, a simple placeholder value is used.

// FixedDepositAccount Class:
// Additional Members:
// interest (float): Represents the interest rate for the fixed deposit account.
// durationInMonths (int): Represents the duration of the fixed deposit in months.
// interestChanged (boolean): A flag to ensure that interest can be set only once.
// durationChanged (boolean): A flag to ensure that the duration can be set only once.

// Constructors:
// FixedDepositAccount(String accountHolderName, float initialBalance): Initializes a fixed deposit account with the account holder's name, initial balance, default interest (3.0), and default duration (6 months).
// FixedDepositAccount(String accountHolderName, float initialBalance, float interest): Similar to the first constructor but allows specifying the interest rate.
// FixedDepositAccount(String accountHolderName, float initialBalance, float interest, int duration): Initializes a fixed deposit account with more parameters, allowing specification of interest and duration.

// Methods:
// setInterest(float interest): Allows setting the interest rate, but only once. If an attempt is made to change it again, an IllegalArgumentException is thrown.
// setDurationInMonths(int duration): Allows setting the duration, but only once. If an attempt is made to change it again, an IllegalArgumentException is thrown.
// getAccountBalance(): Overrides the getAccountBalance method from the BankAccount class to return the balance plus interest, effectively showing the total amount in the fixed deposit account.
// deposit(float amount): Overrides the deposit method to disallow deposits for fixed deposit accounts and throws an UnsupportedOperationException.
// withdraw(float amount): Overrides the withdraw method to disallow withdrawals for fixed deposit accounts and throws an UnsupportedOperationException.
// These classes and their methods and members provide a basic model for managing bank accounts and fixed deposit accounts, adhering to the specified requirements and constraints. The FixedDepositAccount class extends the BankAccount class and adds specialized behavior for fixed deposit accounts.//

