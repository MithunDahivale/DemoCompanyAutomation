package org.common;

import java.io.File;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utility {

	/**
	 * Generate a random 3 digit number
	 * 
	 * @return
	 */
	public static int getRandomInteger() {
		Random random = new Random();
		int value = random.nextInt(1000);
		return value;
	}

	/**
	 * Generate Account number in format 52xx-xxxx-xxxx-xxxx
	 * 
	 * @return
	 */
	public static String generate16DigitNumber() {
		Random random = new Random();
		String value = String.format((Locale) null, // don't want any thousand separators
				"%04d-%04d-%04d-%04d", random.nextInt(10000), random.nextInt(10000), random.nextInt(10000),
				random.nextInt(10000));
		return value;
	}

	/**
	 * ExplicitWait Until VisibilityOfElementLocated
	 * 
	 * @param driver
	 * @param element
	 * @return WebDriverWait
	 */
	public static WebDriverWait waitUntilVisibilityOfElementLocated(WebDriver driver, By element) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		return wait;
	}

	/**
	 * JavaScriptExecutor to perform actions on webelement
	 * 
	 * @param element
	 * @param driver
	 * @param action
	 * @return JavascriptExecutor
	 */
	public static JavascriptExecutor javaScriptExecutor(WebElement element, WebDriver driver, String action) {
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
		javaScriptExecutor.executeScript(action, element);
		return javaScriptExecutor;
	}

	/**
	 * ExplicitWait until TitleContains specified text
	 * 
	 * @param driver
	 * @param title
	 * @return WebDriverWait
	 */
	public static WebDriverWait waitUntilTitleContains(WebDriver driver, String title) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.titleContains(title));
		return wait;
	}

	/**
	 * replace all nextline,tab,spaces,return of the specified string
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceAll(String string) {
		return string.replaceAll("[\\t\\n\\r\\s]+", "");
	}

	/**
	 * capture screenshot
	 * 
	 * @param webdriver
	 * @param filePath
	 * @throws Exception
	 */
	public static void takeSnapShot(WebDriver webdriver, String filePath) throws Exception {

		try {

			TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(filePath);
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (Exception exception) {
			throw exception;
		}

	}

}
