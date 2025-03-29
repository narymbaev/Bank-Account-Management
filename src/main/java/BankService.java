// src/BankService.java
public class BankService {
    private Bank bank;

    public BankService(Bank bank) {
        this.bank = bank;
    }

    /**
     * Transfers an amount from one account to another.
     * Returns true if successful; false if any account is not found or if an exception occurs.
     */
    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        BankAccount fromAccount = bank.getAccount(fromAccountNumber);
        BankAccount toAccount = bank.getAccount(toAccountNumber);
        if (fromAccount == null || toAccount == null) {
            return false;
        }
        try {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
