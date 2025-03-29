import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountBasicPathTest {
    private BankAccount account;

    @BeforeEach
    public void setUp() {
        account = new BankAccount("1001", "TestUser", 1000.0);
    }

    @Test
    public void testPath1_InvalidAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(0.0);
        });
        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    @Test
    public void testPath2_InsufficientBalance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(6000.0);
        });
        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    public void testPath3_ExceedsMaxLimit() {
        account.deposit(15000.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(11000.0);
        });
        assertEquals("Exceeds max withdrawal limit", exception.getMessage());
    }

    @Test
    public void testPath4_BelowMinBalance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(960.0); // 1000 - 960 = 40 < 50
        });
        assertEquals("Below minimum balance", exception.getMessage());
    }

    @Test
    public void testPath5_SuccessfulWithdrawal() {
        account.withdraw(500.0);
        assertEquals(500.0, account.getBalance());
    }

    @Test
    public void testPath6_ExactBalanceBelowMin() {
        account = new BankAccount("1002", "TestUser", 100.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(100.0); // 100 - 100 = 0 < 50
        });
        assertEquals("Below minimum balance", exception.getMessage());
    }

    @Test
    public void testPath7_EdgeMinBalance() {
        account = new BankAccount("1003", "TestUser", 150.0);
        account.withdraw(100.0); // 150 - 100 = 50
        assertEquals(50.0, account.getBalance());
    }

    @Test
    public void testPath8_MaxLimitSuccess() {
        account = new BankAccount("1004", "TestUser", 11000.0);
        account.withdraw(10000.0); // 11000 - 10000 = 1000 >= 50
        assertEquals(1000.0, account.getBalance());
    }
}