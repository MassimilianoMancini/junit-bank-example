package testing.example.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class BankTest {

	private static final double AMOUNT = 7.0;

	private static final double INITIAL_BALANCE = 10.0;

	private static final String INEXISTENT_ID_MESSAGE = "No account found with id: 1";

	private static final int INEXISTENT_ID = 1;

	private Bank bank;

	// the collaborator of Bank that we manually instrument and inspect
	private List<BankAccount> bankAccounts;

	@Before
	public void setup() {
		bankAccounts = new ArrayList<BankAccount>();
		bank = new Bank(bankAccounts);
	}

	@Test
	public void testOpenNewAccountShouldReturnAPositiveIdAndStoreTheAccount() {
		int newAccountId = bank.openNewBankAccount(0);
		assertTrue("Unexpected non positive id: " + newAccountId, newAccountId > 0);
		assertEquals(newAccountId, bankAccounts.get(0).getId());
	}

	@Test
	public void testDepositWhenAccountIsNotFoundShouldThrow() {
		NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> bank.deposit(INEXISTENT_ID, INITIAL_BALANCE));
		assertEquals(INEXISTENT_ID_MESSAGE, e.getMessage());
	}

	@Test
	public void testDepositWhenAccountIsFoundShouldIncrementBalance() {
		// setup
		BankAccount testAccount = createTestAccount(INITIAL_BALANCE);
		bankAccounts.add(testAccount);
		// exercise
		bank.deposit(testAccount.getId(), AMOUNT);
		// verify
		assertEquals(INITIAL_BALANCE + AMOUNT, testAccount.getBalance(), 0);
	}

	@Test
	public void testWithdrawWhenAccountIsNotFoundShouldThrow() {
		NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> bank.withdraw(INEXISTENT_ID, INITIAL_BALANCE));
		assertEquals(INEXISTENT_ID_MESSAGE, e.getMessage());
	}

	@Test
	public void testWithdrawWhenAccountIsFoundShouldDecrementBalance() {
		// setup
		BankAccount testAccount = createTestAccount(INITIAL_BALANCE);
		bankAccounts.add(testAccount);
		// exercise
		bank.withdraw(testAccount.getId(), AMOUNT);
		// verify
		assertEquals(INITIAL_BALANCE - AMOUNT, testAccount.getBalance(), 0);
	}

	private BankAccount createTestAccount(double initialBalance) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(initialBalance);
		return bankAccount;
	}

}
