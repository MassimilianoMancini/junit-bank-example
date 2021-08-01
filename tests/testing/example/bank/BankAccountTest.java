package testing.example.bank;

import static org.junit.Assert.*;

import org.junit.Test;

public class BankAccountTest {
	

	private static final String CANNOT_WITHDRAW_MESSAGE = "Cannot withdraw 7.0 from 0.0";
	private static final String NEGATIVE_AMOUNT_MESSAGE = "Negative amount: -1.0";
	private static final double NEGATIVE_AMOUNT = -1.0;
	private static final double INITIAL_BALANCE = 10.0;
	private static final double AMOUNT = 7.0;

	@Test
	public void testIdIsAutomaticallyAssignedAsPositiveNumber() {
		// setup
		BankAccount bankAccount = new BankAccount();
		// verify
		assertTrue("Id should be positive", bankAccount.getId() > 0);
	}
	
	@Test
	public void testIdsAreIncremental() {
		BankAccount firstAccount = new BankAccount();
		BankAccount secondAccout = new BankAccount();
		assertTrue("Ids should be incremental", firstAccount.getId() < secondAccout.getId());
	}

	@Test
	public void testDepositWhenAmountIsCorrectShouldIncreaseBalance() {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(AMOUNT);
		// exercise
		bankAccount.deposit(INITIAL_BALANCE);
		// verify
		assertEquals(INITIAL_BALANCE + AMOUNT, bankAccount.getBalance(), 0);
	}
	
	@Test
	public void testDepositWhenAmountIsNegativeShouldThrow() {
		// setup
		BankAccount bankAccount = new BankAccount();
		try {
			// exercise
			bankAccount.deposit(NEGATIVE_AMOUNT);
			fail("Expected an IllegalArgumentException to be thrown");
		} catch (IllegalArgumentException e) {
			// verify
			assertEquals(NEGATIVE_AMOUNT_MESSAGE, e.getMessage());
			assertEquals(0, bankAccount.getBalance(), 0);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDepositWhenAmountIsNegativeShouldThrowWithExpected() {
		BankAccount bankAccount = new BankAccount();
		bankAccount.deposit(NEGATIVE_AMOUNT);
	}
		
	@Test
	public void testDepositWhenAmountIsNegativeShouldThrowWithAssertThrows() {
		BankAccount bankAccount = new BankAccount();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()->bankAccount.deposit(NEGATIVE_AMOUNT));
		// perform assertions on the thrown exception
		assertEquals(NEGATIVE_AMOUNT_MESSAGE, e.getMessage());
		// and we can perform further assertions...
		assertEquals(0, bankAccount.getBalance(), 0);
	}
	
	@Test
	public void testWithdrawWhenAmountIsNegativeShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()->bankAccount.withdraw(NEGATIVE_AMOUNT));
		assertEquals(NEGATIVE_AMOUNT_MESSAGE, e.getMessage());
		assertEquals(0, bankAccount.getBalance(), 0);
	}
	
	@Test
	public void testWithdrawWhenBalanceIsUnsufficientShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()->bankAccount.withdraw(AMOUNT));
		assertEquals(CANNOT_WITHDRAW_MESSAGE, e.getMessage());
		assertEquals(0, bankAccount.getBalance(), 0);
	}
	
	@Test
	public void testWithdrawWhenBalanceIsSufficientShouldDecreaseBalance() {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		// exercise
		bankAccount.withdraw(AMOUNT);
		// verify
		assertEquals(INITIAL_BALANCE - AMOUNT, bankAccount.getBalance(), 0);
	}
}
