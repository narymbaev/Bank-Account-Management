import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BankAccountTest {

    // --- Constructor and Getter Tests ---

    @Test
    public void testValidConstructor() {
        BankAccount account = new BankAccount("123", "Alice", 100.0);
        assertNotNull(account);                           // assertNotNull
        assertEquals("123", account.getAccountNumber());  // assertEquals
        assertEquals("Alice", account.getAccountHolder());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testConstructorNegativeBalance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new BankAccount("456", "Bob", -50.0);
        });
        assertTrue(exception.getMessage().contains("Initial balance cannot be negative")); // assertTrue
    }

    // --- Deposit Tests ---

    @Test
    public void testDeposit() {
        BankAccount account = new BankAccount("789", "Charlie", 200.0);
        account.deposit(50.0);
        assertEquals(250.0, account.getBalance());        // assertEquals
    }

    @Test
    public void testDepositInvalid() {
        BankAccount account = new BankAccount("101", "Dave", 300.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(0);
        });
        // assertFalse to ensure balance is not erroneously modified.
        assertFalse(account.getBalance() == 0, "Balance should not be zero after failed deposit");
    }

    // --- Withdrawal Tests ---

    @Test
    public void testWithdraw() {
        BankAccount account = new BankAccount("202", "Eve", 500.0);
        account.withdraw(100.0);
        assertEquals(400.0, account.getBalance());        // assertEquals
    }

    @Test
    public void testWithdrawInvalidAmount() {
        BankAccount account = new BankAccount("303", "Frank", 500.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-50);
        });
        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        BankAccount account = new BankAccount("404", "Grace", 100.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(150);
        });
        assertNotNull(exception); // assertNotNull
        assertTrue(exception.getMessage().contains("Insufficient balance"));
    }

    // --- Parameterized Test for Deposits ---

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 20.5, 30.75})
    public void testParameterizedDeposit(double depositAmount) {
        BankAccount account = new BankAccount("505", "Hank", 100.0);
        account.deposit(depositAmount);
        assertEquals(100.0 + depositAmount, account.getBalance());
    }

    // --- Repeatable Test for Withdrawals ---

    @RepeatedTest(3)
    public void testRepeatedWithdraw() {
        BankAccount account = new BankAccount("606", "Ivy", 300.0);
        account.withdraw(50.0);
        assertTrue(account.getBalance() < 300.0);
    }

    // --- Additional Tests to Reach 20+ Assertions ---

    @Test
    public void testMultipleAssertions() {
        BankAccount account = new BankAccount("707", "Jack", 400.0);
        account.deposit(100.0);
        account.withdraw(50.0);
        assertAll("Account operations",
                () -> assertEquals("707", account.getAccountNumber()),
                () -> assertEquals("Jack", account.getAccountHolder()),
                () -> assertEquals(450.0, account.getBalance()),
                () -> assertNotEquals(500.0, account.getBalance())
        );
    }

    @Test
    public void testDepositMultipleAssertions() {
        BankAccount account = new BankAccount("808", "Kate", 1000.0);
        account.deposit(200.0);
        assertEquals(1200.0, account.getBalance());
        account.deposit(300.0);
        assertEquals(1500.0, account.getBalance());
        account.deposit(100.0);
        assertEquals(1600.0, account.getBalance());
    }

    @Test
    public void testAccountHolderNotNull() {
        BankAccount account = new BankAccount("909", "Leo", 800.0);
        assertNotNull(account.getAccountHolder());
    }
}
