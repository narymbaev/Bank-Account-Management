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
        assertTrue(exception.getMessage().contains("Account cannot be null"));
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
}
