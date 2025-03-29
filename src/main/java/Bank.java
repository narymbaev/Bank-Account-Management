import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<BankAccount> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void addAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        accounts.add(account);
    }

    public void removeAccount(String accountNumber) {
        accounts.removeIf(acc -> acc.getAccountNumber().equals(accountNumber));
    }

    public BankAccount getAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public List<BankAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }
}
