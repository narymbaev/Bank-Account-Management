import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

public class BankTest {

    @Test
    public void testAddAccount() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount("111", "Alice", 500.0);
        bank.addAccount(account);
        List<BankAccount> accounts = bank.getAccounts();
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
        assertEquals(account, accounts.get(0));
    }

    @Test
    public void testAddNullAccount() {
        Bank bank = new Bank();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bank.addAccount(null);
        });
        assertTrue(exception.getMessage().contains("Account cannot be null")); // (30)
    }

    @Test
    public void testRemoveAccount() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount("222", "Bob", 700.0);
        bank.addAccount(account);
        bank.removeAccount("222");
        assertEquals(0, bank.getAccounts().size());
        assertNull(bank.getAccount("222"));
    }

    @Test
    public void testGetAccount() {
        Bank bank = new Bank();
        BankAccount account1 = new BankAccount("333", "Charlie", 300.0);
        BankAccount account2 = new BankAccount("444", "Dave", 600.0);
        bank.addAccount(account1);
        bank.addAccount(account2);
        BankAccount foundAccount = bank.getAccount("444");
        assertNotNull(foundAccount);
        assertEquals("Dave", foundAccount.getAccountHolder());
    }

    @Test
    public void testGetAccountsIsolation() {
        Bank bank = new Bank();
        BankAccount account1 = new BankAccount("555", "Emma", 800.0);
        BankAccount account2 = new BankAccount("666", "Frank", 900.0);
        bank.addAccount(account1);
        bank.addAccount(account2);

        List<BankAccount> accountsCopy = bank.getAccounts();
        assertEquals(2, accountsCopy.size());

        // Modify the returned list and ensure the bank's internal list remains unchanged.
        accountsCopy.clear();
        assertEquals(2, bank.getAccounts().size());
        assertNotEquals(0, bank.getAccounts().size());
    }

    @Test
    public void testRemoveNonExistingAccount() {
        Bank bank = new Bank();
        bank.removeAccount("nonexistent");
        // Expect the accounts list to remain empty.
        assertEquals(0, bank.getAccounts().size());
    }

    @Test
    public void testGetAccountNonExisting() {
        Bank bank = new Bank();
        BankAccount account = bank.getAccount("nonexistent");
        assertNull(account);
    }

    @Test
    public void testAddMultipleAccounts() {
        Bank bank = new Bank();
        BankAccount account1 = new BankAccount("A1", "User1", 100.0);
        BankAccount account2 = new BankAccount("A2", "User2", 200.0);
        BankAccount account3 = new BankAccount("A3", "User3", 300.0);
        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(account3);
        assertEquals(3, bank.getAccounts().size());
    }

    @Test
    public void testRemoveAccountTwice() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount("B1", "UserB", 500.0);
        bank.addAccount(account);
        bank.removeAccount("B1");
        // Attempt to remove again.
        bank.removeAccount("B1");
        assertNull(bank.getAccount("B1"));
        assertEquals(0, bank.getAccounts().size());
    }

    @Test
    public void testAddDuplicateAccounts() {
        Bank bank = new Bank();
        BankAccount account1 = new BankAccount("C1", "UserC", 100.0);
        BankAccount account2 = new BankAccount("C1", "UserC", 200.0); // duplicate account number
        bank.addAccount(account1);
        bank.addAccount(account2);
        // Both accounts are added (if no duplicate check is enforced).
        assertEquals(2, bank.getAccounts().size());
    }

    @Test
    public void testAddAndRemoveMultipleAccounts() {
        Bank bank = new Bank();
        BankAccount account1 = new BankAccount("D1", "UserD1", 100.0);
        BankAccount account2 = new BankAccount("D2", "UserD2", 200.0);
        BankAccount account3 = new BankAccount("D3", "UserD3", 300.0);
        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(account3);
        bank.removeAccount("D2");
        List<BankAccount> remaining = bank.getAccounts();
        assertEquals(2, remaining.size());
        assertNotNull(bank.getAccount("D1"));
        assertNotNull(bank.getAccount("D3"));
        assertNull(bank.getAccount("D2"));
    }

    @Test
    public void testBankEmptyAfterInitialization() {
        Bank bank = new Bank();
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void testAccountsOrder() {
        Bank bank = new Bank();
        BankAccount account1 = new BankAccount("E1", "First", 100.0);
        BankAccount account2 = new BankAccount("E2", "Second", 200.0);
        bank.addAccount(account1);
        bank.addAccount(account2);
        List<BankAccount> accounts = bank.getAccounts();
        assertEquals("E1", accounts.get(0).getAccountNumber());
        assertEquals("E2", accounts.get(1).getAccountNumber());
    }

    @Test
    public void testUpdateAccountViaBank() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount("F1", "Updater", 1000.0);
        bank.addAccount(account);
        // Update the account.
        account.deposit(500.0);
        BankAccount retrieved = bank.getAccount("F1");
        assertEquals(1500.0, retrieved.getBalance());
    }

    @Test
    public void testAddAccountWithSameAccountNumber() {
        Bank bank = new Bank();
        BankAccount account1 = new BankAccount("G1", "UserG1", 100.0);
        BankAccount account2 = new BankAccount("G1", "UserG2", 200.0);
        bank.addAccount(account1);
        bank.addAccount(account2);
        // Check that both accounts exist (assuming duplicates are allowed).
        assertEquals(2, bank.getAccounts().size());
    }
}
