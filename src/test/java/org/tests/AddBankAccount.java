package org.tests;

import org.common.Messages;
import org.common.Utility;
import org.openqa.selenium.support.PageFactory;
import org.pages.AddBankAccountsPage;
import org.pages.BankAccountsPage;
import org.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

/**
 * Test Class to Add Bank Account
 */
public class AddBankAccount extends BaseTest {

	/**
	 * Test case to Add Bank Account to the Organisation
	 * 
	 * @param bankName
	 * @param accountName
	 * @param accountType
	 * @throws Exception
	 */
	@Test(testName = "Add Bank Account to the Organisation", dataProvider = "readData")
	public void addAccount(String bankName, String accountName, String accountType) throws Exception {
		try {
			//
			logIn();
			// Click on Bank Accounts submenu under AddBankAccount Menu on Dashboard page
			DashboardPage dashboardPage = PageFactory.initElements(webDriver, DashboardPage.class);
			dashboardPage.clickBankAccountFromDropDown();

			// check the page title
			Utility.waitUntilTitleContains(webDriver, "Bank accounts");
			String title = Utility.replaceAll(webDriver.getTitle());
			log.info("Navigated successfully to " + title);
			Assert.assertEquals(title, Messages.BANKACCOUNTS_WINDOW_TITLE);
			Utility.takeSnapShot(webDriver, file + "//BankAccounts.png");

			// Click on the Add Accounts Button on Bank Accounts Page
			BankAccountsPage bankAccountsPage = PageFactory.initElements(webDriver, BankAccountsPage.class);
			bankAccountsPage.clickOnAddAccountsButton();

			// check the page title
			Utility.waitUntilTitleContains(webDriver, "Find your bank");
			title = Utility.replaceAll(webDriver.getTitle());
			log.info("Navigated successfully to " + title);
			Assert.assertEquals(title, Messages.FINDYOURBANK_WINDOW_TITLE);

			// Search and Select the bank from dropdown on Add Bank Accounts Page
			AddBankAccountsPage addBankAccountsPage = PageFactory.initElements(webDriver, AddBankAccountsPage.class);
			addBankAccountsPage.searchBank(bankName);
			Assert.assertTrue(addBankAccountsPage.selectBankFromDropDown(bankName));
			log.info(bankName + " Bank  has been selected");
			logger.log(Status.PASS, bankName + " Bank  has been selected");

			// check the page title
			Utility.waitUntilTitleContains(webDriver, "account details");
			title = Utility.replaceAll(webDriver.getTitle());
			log.info("Navigated successfully to " + title);
			Assert.assertEquals(title, Messages.ENTERYOURANZACCOUNTDETAILS_WINDOW_TITLE);
			Utility.takeSnapShot(webDriver, file + "//SelectBank.png");

			// Enter Account Details
			accountName = accountName + Utility.getRandomInteger();
			log.info("Account Name " + accountName);

			addBankAccountsPage.enterAccountName(accountName);
			addBankAccountsPage.enterAccountType(accountType);

			String accountNumber = Utility.generate16DigitNumber();
			addBankAccountsPage.enterAccountNumber(accountNumber);

			// Click on Continue and verify the confirmation message
			String message = addBankAccountsPage.clickOnContinue().trim();
			Assert.assertEquals(message, accountName + " has been added.");
			log.info(accountName + " Account  has been added");
			logger.log(Status.PASS, accountName + " Account  has been added");
			Utility.takeSnapShot(webDriver, file + "//BankAccountAdded.png");

			// SignOut
			signOut();

			// check the page title
			Utility.waitUntilTitleContains(webDriver, "Login");
			title = Utility.replaceAll(webDriver.getTitle());
			log.info(title);
			Assert.assertEquals(title, Messages.LOGIN_WINDOW_TITLE);
			log.info("Add Bank Account Test Passed");
			logger.log(Status.PASS, "Add Bank Account Test Passed");

		} catch (Exception exception) {
			log.error(exception);
			log.info("Add Bank Account Test Failed");
			logger.log(Status.FAIL, "Add Bank Account Test Failed");
			throw exception;
		}
	}
}
