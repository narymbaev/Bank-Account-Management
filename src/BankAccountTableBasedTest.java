import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BankAccountTableBasedTest {
    private BankAccount account;

    // Setup method to initialize account with specific balance before each test
    private void setupAccount(double initialBalance) {
        account = new BankAccount("1001", "TestUser", initialBalance);
    }

    @Test
    public void testTC1_ZeroWithdrawal() {
        setupAccount(1000.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(0.0);
        });
        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    @Test
    public void testTC2_NegativeWithdrawal() {
        setupAccount(1000.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-50.0);
        });
        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    @Test
    public void testTC3_InsufficientBalance() {
        setupAccount(500.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(600.0);
        });
        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    public void testTC4_WithdrawExactBalance() {
        setupAccount(500.0);
        account.withdraw(300.0);
        assertEquals(200.0, account.getBalance(), 0.0001); // Small delta for double comparison
    }

    @Test
    public void testTC5_NormalWithdrawal() {
        setupAccount(500.0);
        account.withdraw(300.0);
        assertEquals(200.0, account.getBalance(), 0.0001);
    }

    @Test
    public void testTC6_ZeroBalanceInsufficient() {
        setupAccount(0.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(1.0);
        });
        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    public void testTC7_FullWithdrawal() {
        setupAccount(1000.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(1000.0);
        });
        assertEquals("Below minimum balance", exception.getMessage());
    }

    @Test
    public void testTC8_PartialWithdrawal() {
        setupAccount(200.0);
        account.withdraw(50.0);
        assertEquals(150.0, account.getBalance(), 0.0001);
    }
}