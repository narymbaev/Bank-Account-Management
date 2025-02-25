public class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

//    public void withdraw(double amount) {
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Withdrawal amount must be positive");
//        }
//        if (amount > balance) {
//            throw new IllegalArgumentException("Insufficient balance");
//        }
//        balance -= amount;
//    }

    public void withdraw(double amount) {
        if (amount > 0) {
            double currentBalance = getBalance();
            if (amount > currentBalance) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            if (amount > 10000) { // Added: max withdrawal limit
                throw new IllegalArgumentException("Exceeds max withdrawal limit");
            }
            if (currentBalance - amount < 50) { // Added: minimum balance requirement
                throw new IllegalArgumentException("Below minimum balance");
            }
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }
}
