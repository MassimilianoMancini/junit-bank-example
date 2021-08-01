package testing.example.bank;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BankAccountTestDeprecated {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

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
		// exercise
		bankAccount.deposit(10);
		// verify
		assertEquals(10, bankAccount.getBalance(), 0);
	}
	
	@Test
	public void testDepositWhenAmountIsNegativeShouldThrow() {
		// setup
		BankAccount bankAccount = new BankAccount();
		try {
			// exercise
			bankAccount.deposit(-1);
			fail("Expected an IllegalArgumentException to be thrown");
		} catch (IllegalArgumentException e) {
			// verify
			assertEquals("Negative amount: -1.0", e.getMessage());
			assertEquals(0, bankAccount.getBalance(), 0);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDepositWhenAmountIsNegativeShouldThrowWithExpected() {
		BankAccount bankAccount = new BankAccount();
		bankAccount.deposit(-1);
	}
	
	// False positive!
	@Test(expected = IllegalArgumentException.class)
	public void testDepositWhenAmountIsNegativeShouldThrowWithExpectedFalsePositive() {
		BankAccount bankAccount = new BankAccount();
		System.getProperty(""); // throws IllegalArgumentException
		bankAccount.deposit(10); // this does not throw
	}
	
	@Test
	public void testDepositWhenAmountIsNegativeShouldThrowWithExpectedException() {
		BankAccount bankAccount = new BankAccount();
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Negative amount: -1.0");
		bankAccount.deposit(-1);
		// but we can't perform further assertions...
	}
	
	// Not a false positive!
	@Test
	public void testDepositWhenAmountIsNegativeShouldThrowWithExpectedExceptinNotFalsePositive() {
		BankAccount bankAccount = new BankAccount();
		System.getProperty(""); // throws IllegalArgumentException
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Negative amount: -1.0");
		bankAccount.deposit(-1);
	}
	
	@Test
	public void testDepositWhenAmountIsNegativeShouldThrowWithAssertThrows() {
		BankAccount bankAccount = new BankAccount();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()->bankAccount.deposit(-1));
		// perform assertions on the thrown exception
		assertEquals("Negative amount: -1.0", e.getMessage());
		// and we can perform further assertions...
		assertEquals(0, bankAccount.getBalance(), 0);
	}
}
