package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for Login Page
 */
public class LoginPage {
	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	By userNameElement = By.id("email");

	By passwordElement = By.id("password");

	By submitButton = By.id("submitButton");

	/**
	 * Enter userName
	 * 
	 * @param userName
	 */
	public void enterUserName(String userName) {
		driver.findElement(userNameElement).sendKeys(userName);
	}

	/**
	 * Enter Password
	 * 
	 * @param password
	 */
	public void enterPassword(String password) {
		driver.findElement(passwordElement).sendKeys(password);
	}

	/**
	 * Click on Login Button
	 */
	public void clickOnLogin() {
		driver.findElement(submitButton).click();
	}

}
