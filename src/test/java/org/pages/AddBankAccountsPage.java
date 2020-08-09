package org.pages;

import java.util.List;

import org.common.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * Page object for Add Bank Account Page
 */
public class AddBankAccountsPage {
	 WebDriver driver;
	   
	   public AddBankAccountsPage(WebDriver driver)
	   {
		   this.driver = driver;
	   }
	   
       By searchElement = By.id("xui-searchfield-1018-inputEl");
       
       By searchDropDownElement = By.xpath("//*[@id='ba-banklist-1023']//li");
       
       By accountNameElement = By.id("accountname-1037-inputEl");
       
       By accountTypeElement = By.id("accounttype-1039-inputEl");
       
       By accountTypeListElement = By.xpath("//*[@id='boundlist-1076-listEl']//li");
       
       By accountNumberElement = By.id("accountnumber-1068-inputEl");
       
       By continueButton = By.id("common-button-submit-1015-btnInnerEl");     
      
       By confirmationMessage = By.id("notify01");
       
       /**
        * Enter the bankname in search
        * @param bankName
        */
	   public void searchBank(String bankName)
	   {		 
			Utility.waitUntilVisibilityOfElementLocated(driver, searchElement);
			Utility.javaScriptExecutor(driver.findElement(searchElement), driver, "arguments[0].click();");
			driver.findElement(searchElement).sendKeys(bankName);
	   }
	   
	   /**
	    * Select the bank from the search result dropdown
	    * @param bankName
	    * @return boolean
	    */
	   public boolean selectBankFromDropDown(String bankName)
	   {
		  List<WebElement> dropdownElements =driver.findElements(searchDropDownElement);
		  
		  if(!dropdownElements.isEmpty())
		  {
			  for(WebElement element:dropdownElements)
			  {
				 if( element.getText().equals(bankName))
				 {
				  element.click();
				  return true;
				 }
			  }
		  }
		  return false;		
	   }
	   
	   /**
	    * Enter the Account Number
	    * @param accountName
	    */
	   public void enterAccountName(String accountName)
	   {		
	   	 Utility.waitUntilVisibilityOfElementLocated(driver, accountNameElement);		 
		   driver.findElement(accountNameElement).sendKeys(accountName);		
	   }
	   
	   /**
	    * Enter Account Type
	    * @param accountType
	    * @return
	    */
	   public boolean enterAccountType(String accountType)
	   {
		   driver.findElement(accountTypeElement).click();
		   
		   List<WebElement> dropdownElements =driver.findElements(accountTypeListElement);
			  
			  if(!dropdownElements.isEmpty())
			  {
				  for(WebElement element:dropdownElements)
				  {
					 if( element.getText().equals(accountType))
					 {
					  element.click();
					  return true;
					 }
				  }
			  }
			  return false;	
	   }
	   
	   /**
	    * Enter Account Number
	    * @param accountNumber
	    */
	   public void enterAccountNumber(String accountNumber)
	   {
		   Utility.waitUntilVisibilityOfElementLocated(driver, accountNumberElement);
		   driver.findElement(accountNumberElement).sendKeys(accountNumber);		 
	   }
	   
	   /**
	    * click on the continue button
	    * @return
	    */
	   public String clickOnContinue()
	   {
		   driver.findElement(continueButton).click();	
		   String message =  driver.findElement(confirmationMessage).getText();
		   return message;	   
	   }
	  
}
