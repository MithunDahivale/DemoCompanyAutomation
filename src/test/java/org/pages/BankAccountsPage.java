package org.pages;

import org.common.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page object for Bank Account Page
 */
public class BankAccountsPage {

	WebDriver driver;

	public BankAccountsPage(WebDriver driver) {
		this.driver = driver;
	}

	By addAccountsButton = By.xpath("//*[contains(text(),'Add Bank Account')]");

	/**
	 * click on Add Accounts Button
	 */
	public void clickOnAddAccountsButton() {
		WebElement element = driver.findElement(addAccountsButton);
		Utility.javaScriptExecutor(element, driver, "arguments[0].scrollIntoView();");
		Utility.javaScriptExecutor(element, driver, "arguments[0].click();");
	}
}
