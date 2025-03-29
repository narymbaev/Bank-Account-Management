import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BankServiceMockitoTest {

    @Test
    public void testSuccessfulTransfer() {
        Bank mockBank = mock(Bank.class);
        BankAccount mockFromAccount = mock(BankAccount.class);
        BankAccount mockToAccount = mock(BankAccount.class);

        when(mockBank.getAccount("from")).thenReturn(mockFromAccount);
        when(mockBank.getAccount("to")).thenReturn(mockToAccount);
        when(mockBank.getAccounts()).thenReturn(java.util.Arrays.asList(mockFromAccount, mockToAccount));

        when(mockFromAccount.getAccountNumber()).thenReturn("from");
        when(mockFromAccount.getBalance()).thenReturn(1000.0);
        doNothing().when(mockFromAccount).withdraw(200.0);

        when(mockToAccount.getAccountNumber()).thenReturn("to");
        when(mockToAccount.getBalance()).thenReturn(500.0);
        doNothing().when(mockToAccount).deposit(200.0);

        BankService bankService = new BankService(mockBank);
        boolean result = bankService.transfer("from", "to", 200.0);
        assertTrue(result);

        verify(mockFromAccount, times(1)).withdraw(200.0);
        verify(mockToAccount, times(1)).deposit(200.0);
    }

    @Test
    public void testTransferFromAccountNotFound() {
        Bank mockBank = mock(Bank.class);
        when(mockBank.getAccount("from")).thenReturn(null);
        BankAccount mockToAccount = mock(BankAccount.class);
        when(mockBank.getAccount("to")).thenReturn(mockToAccount);
        when(mockBank.getAccounts()).thenReturn(java.util.Arrays.asList(mockToAccount));

        BankService bankService = new BankService(mockBank);
        boolean result = bankService.transfer("from", "to", 100.0);
        assertFalse(result);
    }

    @Test
    public void testTransferToAccountNotFound() {
        Bank mockBank = mock(Bank.class);
        BankAccount mockFromAccount = mock(BankAccount.class);
        when(mockBank.getAccount("from")).thenReturn(mockFromAccount);
        when(mockBank.getAccount("to")).thenReturn(null);
        when(mockBank.getAccounts()).thenReturn(java.util.Arrays.asList(mockFromAccount));

        BankService bankService = new BankService(mockBank);
        boolean result = bankService.transfer("from", "to", 100.0);
        assertFalse(result);
    }

    @Test
    public void testTransferWithdrawalFailure() {
        Bank mockBank = mock(Bank.class);
        BankAccount mockFromAccount = mock(BankAccount.class);
        BankAccount mockToAccount = mock(BankAccount.class);

        when(mockBank.getAccount("from")).thenReturn(mockFromAccount);
        when(mockBank.getAccount("to")).thenReturn(mockToAccount);
        when(mockBank.getAccounts()).thenReturn(java.util.Arrays.asList(mockFromAccount, mockToAccount));

        doThrow(new IllegalArgumentException("Insufficient funds")).when(mockFromAccount).withdraw(300.0);
        when(mockFromAccount.getAccountNumber()).thenReturn("from");
        when(mockFromAccount.getBalance()).thenReturn(200.0);

        when(mockToAccount.getAccountNumber()).thenReturn("to");
        when(mockToAccount.getBalance()).thenReturn(400.0);
        doNothing().when(mockToAccount).deposit(300.0);

        BankService bankService = new BankService(mockBank);
        boolean result = bankService.transfer("from", "to", 300.0);
        assertFalse(result);

        verify(mockFromAccount, times(1)).withdraw(300.0);
        verify(mockToAccount, never()).deposit(anyDouble());
    }

    @Test
    public void testTransferDepositFailure() {
        Bank mockBank = mock(Bank.class);
        BankAccount mockFromAccount = mock(BankAccount.class);
        BankAccount mockToAccount = mock(BankAccount.class);

        when(mockBank.getAccount("from")).thenReturn(mockFromAccount);
        when(mockBank.getAccount("to")).thenReturn(mockToAccount);
        when(mockBank.getAccounts()).thenReturn(java.util.Arrays.asList(mockFromAccount, mockToAccount));

        doNothing().when(mockFromAccount).withdraw(100.0);
        when(mockFromAccount.getAccountNumber()).thenReturn("from");
        when(mockFromAccount.getBalance()).thenReturn(500.0);

        doThrow(new IllegalArgumentException("Deposit error")).when(mockToAccount).deposit(100.0);
        when(mockToAccount.getAccountNumber()).thenReturn("to");
        when(mockToAccount.getBalance()).thenReturn(300.0);

        BankService bankService = new BankService(mockBank);
        boolean result = bankService.transfer("from", "to", 100.0);
        assertFalse(result);

        verify(mockFromAccount, times(1)).withdraw(100.0);
        verify(mockToAccount, times(1)).deposit(100.0);
    }

    @Test
    public void testTransferWithMultipleBehaviors() {
        Bank mockBank = mock(Bank.class);
        BankAccount mockFromAccount = mock(BankAccount.class);
        BankAccount mockToAccount = mock(BankAccount.class);

        when(mockBank.getAccount("from")).thenReturn(mockFromAccount);
        when(mockBank.getAccount("to")).thenReturn(mockToAccount);
        when(mockBank.getAccounts()).thenReturn(java.util.Arrays.asList(mockFromAccount, mockToAccount));

        // Multiple behaviors for fromAccount
        when(mockFromAccount.getBalance()).thenReturn(1000.0);
        doNothing().when(mockFromAccount).withdraw(250.0);

        when(mockToAccount.getAccountNumber()).thenReturn("to");
        when(mockToAccount.getBalance()).thenReturn(500.0);
        doNothing().when(mockToAccount).deposit(250.0);

        BankService bankService = new BankService(mockBank);
        boolean result = bankService.transfer("from", "to", 250.0);
        assertTrue(result);

        verify(mockFromAccount, times(1)).withdraw(250.0);
        verify(mockToAccount, times(1)).deposit(250.0);
    }
}
