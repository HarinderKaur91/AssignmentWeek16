package com.HarinderKaur.AssignmentWeek16;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ValidateWebPages {

	WebDriver wd;
	WebDriverWait wdwait;
	SoftAssert sf;
	Actions action;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\kaurh\\OneDrive\\Documents\\Drivers\\chromedriver.exe");
		wd = new ChromeDriver();
		wdwait = new WebDriverWait(wd, Duration.ofSeconds(15));
		sf = new SoftAssert();
		wd.get("http://seleniumpractise.blogspot.com/2016/08/how-to-perform-mouse-hover-in-selenium.html");
		wd.manage().window().maximize();
		action = new Actions(wd);

	}

	@Test
	public void validateWebPages() {

		String text = wd.findElement(By.cssSelector("div.post-outer h3")).getText();
		sf.assertEquals(text, "How to perform mouse hover in Selenium Webdriver", "Text doesn't match");

		//Navigating to Selenium Page
		WebElement automationToolsBtn = wd.findElement(By.cssSelector("button.dropbtn"));// mouse hover button
		action.moveToElement(automationToolsBtn).perform();
		wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.dropdown-content a:first-of-type")));

		WebElement seleniumBtn = wd.findElement(By.cssSelector("div.dropdown-content a:first-of-type"));

		String parentWindowHanlde = wd.getWindowHandle();
		System.out.println("Parent handle is " + parentWindowHanlde);
		seleniumBtn.click();
		Set<String> allWindowHandlesList = wd.getWindowHandles();
		for (String handle : allWindowHandlesList) {
			if (!handle.equals(parentWindowHanlde)) {
				wd.switchTo().window(handle);
				System.out.println("Selenium page handle is " + handle);
			}
		}
		sf.assertEquals(wd.getTitle(), "Selenium Webdriver Tutorial - Selenium Tutorial for Beginners",
				"Title doesn't match");
		wd.switchTo().window(parentWindowHanlde);
		
		//Navigating to TestNg Page
		wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.dropdown-content a:nth-of-type(2)")));
		WebElement testNgBtn = wd.findElement(By.cssSelector("div.dropdown-content a:nth-of-type(2)"));
		testNgBtn.click();
		sf.assertEquals(wd.getTitle(), "TestNG Tutorials for Selenium Webdriver with Real Time Examples",
				"Title doesn't match");
		wd.navigate().back();

		//Navigating to Appium page
		wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.dropdown-content a:last-of-type")));
		WebElement appiumBtn = wd.findElement(By.cssSelector("div.dropdown-content a:last-of-type"));
		appiumBtn.click();
		sf.assertEquals(wd.getTitle(), "Complete Ultimate Appium tutorial for beginners using JAVA for Selenium",
				"Title doesn't match");
		sf.assertAll();

	}

	@AfterMethod
	public void tearDown() {
		wd.quit();
	}

}
