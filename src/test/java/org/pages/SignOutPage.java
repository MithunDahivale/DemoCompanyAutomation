package org.pages;

import org.common.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for SignOut on all pages
 */
public class SignOutPage {
	WebDriver driver;

	public SignOutPage(WebDriver driver) {
		this.driver = driver;
	}

	By userAddOnElement = By.xpath("//*[@data-automationid='xrh-addon-user']");

	By logOutElement = By.xpath("//*[@data-automationid='xrh-addon-user-body']//a[@data-name='user-menu/log-out']");

	/**
	 * click on User AddOn on all Pages
	 */
	public void clickOnUserAddOn() {
		driver.findElement(userAddOnElement).click();

	}

	/**
	 * click on LogOut submenu under User AddOn on all Pages
	 */
	public void clickOnLogOut() {
		Utility.waitUntilVisibilityOfElementLocated(driver, logOutElement);
		driver.findElement(logOutElement).click();

	}
}
