package org.pages;

import org.common.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for Dashboard Page
 */
public class DashboardPage {

	 WebDriver driver;
	   
	   public DashboardPage(WebDriver driver)
	   {
		   this.driver = driver;
	   }
	   
	   By accountingButtonElement = By.name("navigation-menu/accounting");
	   
	   By  bankAccountElement = By.xpath("//*[contains(@data-event-action, 'bank-accounts')]");
	   /**
	    * Click on Bank Account submenu from dropdown
	    */
	   public void clickBankAccountFromDropDown()
	   {
		    Utility.waitUntilVisibilityOfElementLocated(driver, accountingButtonElement);
		   
			WebElement element = driver.findElement(accountingButtonElement);
			Utility.javaScriptExecutor(element, driver, "arguments[0].click();");	
		  
			driver.findElement(bankAccountElement).click();	
	   }
}
