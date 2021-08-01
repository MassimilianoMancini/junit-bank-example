package testing.example.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BankTestDeprecated {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
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
		thrown.expect(NoSuchElementException.class);
		thrown.expectMessage("No account found with id: 1");
		bank.deposit(1, 10);
	}
	
	@Test
	public void testDepositWhenAccountIsFoundShouldIncrementBalance() {
		// setup
		BankAccount testAccount = createTestAccount(10);
		bankAccounts.add(testAccount);
		// exercise
		bank.deposit(testAccount.getId(), 5);
		// verify
		assertEquals(15, testAccount.getBalance(), 0);
	}
		
	@Test
	public void testWithdrawWhenAccountIsNotFoundShouldThrow() {
		thrown.expect(NoSuchElementException.class);
		thrown.expectMessage("No account found with id: 1");
		bank.withdraw(1, 10);
	}
	
	@Test
	public void testWithdrawWhenAccountIsFoundShouldDecrementBalance() {
		// setup
		BankAccount testAccount = createTestAccount(10);
		bankAccounts.add(testAccount);
		// exercise
		bank.withdraw(testAccount.getId(), 5);
		// verify
		assertEquals(5, testAccount.getBalance(), 0);
	}

	private BankAccount createTestAccount(double initialBalance) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(initialBalance);
		return bankAccount;
	}
	
	

}
