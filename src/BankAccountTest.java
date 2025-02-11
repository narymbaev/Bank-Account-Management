import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

public class BankAccountTest {

    @Test
    public void testValidConstructor() {
        BankAccount account = new BankAccount("123", "Alice", 100.0);
        assertNotNull(account);
        assertEquals("123", account.getAccountNumber());
        assertEquals("Alice", account.getAccountHolder());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testConstructorNegativeBalance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new BankAccount("456", "Bob", -50.0);
        });
        assertTrue(exception.getMessage().contains("Initial balance cannot be negative"));
    }

    @Test
    public void testDeposit() {
        BankAccount account = new BankAccount("789", "Charlie", 200.0);
        account.deposit(50.0);
        assertEquals(250.0, account.getBalance());
    }

    @Test
    public void testDepositInvalid() {
        BankAccount account = new BankAccount("101", "Dave", 300.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(0);
        });
        // Check that the balance remains unchanged
        assertFalse(account.getBalance() == 0, "Balance should not be zero after failed deposit"); // (9)
    }

    @Test
    public void testWithdraw() {
        BankAccount account = new BankAccount("202", "Eve", 500.0);
        account.withdraw(100.0);
        assertEquals(400.0, account.getBalance());
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
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Insufficient balance"));
    }

    // Parameterized Test for Deposits
    @ParameterizedTest
    @ValueSource(doubles = {10.0, 20.5, 30.75})
    public void testParameterizedDeposit(double depositAmount) {
        BankAccount account = new BankAccount("505", "Hank", 100.0);
        account.deposit(depositAmount);
        assertEquals(100.0 + depositAmount, account.getBalance());
    }

    @ParameterizedTest
    @CsvSource({
            "500.0, 100.0, 400.0",
            "300.0, 50.0, 250.0",
            "1000.0, 200.0, 800.0"
    })
    public void testParameterizedWithdraw(double initialBalance, double withdrawAmount, double expectedBalance) {
        BankAccount account = new BankAccount("600", "Ivy", initialBalance);
        account.withdraw(withdrawAmount);
        assertEquals(expectedBalance, account.getBalance());
    }

    // Repeatable Test for Withdrawals
    @RepeatedTest(3)
    public void testRepeatedWithdraw() {
        BankAccount account = new BankAccount("606", "Ivy", 300.0);
        account.withdraw(50.0);
        assertTrue(account.getBalance() < 300.0);
    }

    // Repeatable Test for Deposits
    @RepeatedTest(3)
    public void testRepeatedDeposit() {
        BankAccount account = new BankAccount("RP2", "RepeatedDeposit", 100.0);
        account.deposit(5.0);
        assertEquals(105.0, account.getBalance());
    }

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

    @Test
    public void testDepositBoundary() {
        BankAccount account = new BankAccount("1001", "BoundaryTest", 100.0);
        account.deposit(0.0001);
        // Allow a small tolerance when comparing doubles.
        assertEquals(100.0001, account.getBalance(), 1e-6);
    }

    @Test
    public void testWithdrawExactBalance() {
        BankAccount account = new BankAccount("1002", "ExactBalance", 150.0);
        account.withdraw(150.0);
        assertEquals(0.0, account.getBalance());
    }

    @Test
    public void testWithdrawMultipleTimes() {
        BankAccount account = new BankAccount("1003", "MultiWithdraw", 300.0);
        account.withdraw(50.0);
        assertEquals(250.0, account.getBalance());
        account.withdraw(100.0);
        assertEquals(150.0, account.getBalance());
        account.withdraw(75.0);
        assertEquals(75.0, account.getBalance());
    }

    @Test
    public void testMultipleOperations() {
        BankAccount account = new BankAccount("1004", "OpsTest", 200.0);
        account.deposit(100.0);   // 300
        account.withdraw(50.0);   // 250
        account.deposit(25.0);    // 275
        account.withdraw(75.0);   // 200
        assertEquals(200.0, account.getBalance());
    }

    @Test
    public void testDepositAfterWithdrawal() {
        BankAccount account = new BankAccount("1005", "DepositAfterWithdraw", 500.0);
        account.withdraw(200.0); // 300
        account.deposit(150.0);  // 450
        assertEquals(450.0, account.getBalance());
    }

    @Test
    public void testDepositWithLargeAmount() {
        BankAccount account = new BankAccount("1006", "LargeDeposit", 1000.0);
        account.deposit(1_000_000.0);
        assertEquals(1_001_000.0, account.getBalance());
    }

    @Test
    public void testWithdrawLargeAmount() {
        BankAccount account = new BankAccount("1007", "LargeWithdraw", 1_000_000.0);
        account.withdraw(500_000.0);
        assertEquals(500_000.0, account.getBalance());
    }

    @Test
    public void testBalanceConsistency() {
        BankAccount account = new BankAccount("1008", "ConsistencyTest", 250.0);
        account.deposit(50.0);
        account.withdraw(30.0);
        account.deposit(20.0);
        account.withdraw(40.0);
        account.deposit(10.0);
        assertEquals(260.0, account.getBalance());
    }

    @Test
    public void testZeroWithdrawal() {
        BankAccount account = new BankAccount("1009", "ZeroWithdraw", 400.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(0.0);
        });
        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    @Test
    public void testZeroDeposit() {
        BankAccount account = new BankAccount("1010", "ZeroDeposit", 400.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(0.0);
        });
        assertEquals("Deposit amount must be positive", exception.getMessage());
    }
}
