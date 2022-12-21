package com.HarinderKaur.AssignmentWeek16;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MultipleTabs {

	WebDriver wd;
	WebDriverWait wdwait;
	SoftAssert sf;

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\kaurh\\OneDrive\\Documents\\Drivers\\chromedriver.exe");
		wd = new ChromeDriver();
		wdwait = new WebDriverWait(wd, Duration.ofSeconds(10));
		sf = new SoftAssert();
		wd.get("http://seleniumpractise.blogspot.com/2017/07/multiple-window-examples.html");
		wd.manage().window().maximize();

	}

	@Test
	public void validateMultipleTabs() {

		String text = wd.findElement(By.cssSelector("div.post-outer h3")).getText();
		sf.assertEquals(text, "Multiple window examples", "Text doesn't match");

		WebElement firstLink = wd.findElement(By.cssSelector("div.post-body.entry-content a:first-of-type"));
		WebElement secondLink = wd.findElement(By.cssSelector("div.post-body.entry-content a:nth-of-type(2)"));
		WebElement thirdLink = wd.findElement(By.cssSelector("div.post-body.entry-content a:nth-of-type(3)"));
		WebElement fourthLink = wd.findElement(By.cssSelector("div.post-body.entry-content a:last-of-type"));

		String parentWindowHandle = wd.getWindowHandle();
		String child1Handle = null;
		String child2Handle = null;
		String child3Handle = null;
		
		//Navigating to first tab
		firstLink.click();
		Set<String> allWindowHandlesList = wd.getWindowHandles();
		System.out.println("Parent Window handle " + parentWindowHandle);
		for (String handle : allWindowHandlesList) {
			if (!handle.equals(parentWindowHandle)) {
				wd.switchTo().window(handle);
				child1Handle = handle;
			}
		}
		System.out.println("Child1 handle is " + child1Handle);
		sf.assertEquals(wd.getTitle(), "Google", "Title doesn't match");
		wd.switchTo().window(parentWindowHandle);
		
		//Navigating to second tab
		wdwait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("div.post-body.entry-content a:nth-of-type(2)")));
		secondLink.click();
		allWindowHandlesList = wd.getWindowHandles();
		for (String handle : allWindowHandlesList) {
			if (!(handle.equals(parentWindowHandle) || handle.equals(child1Handle))) {
				wd.switchTo().window(handle);
				child2Handle = handle;
			}
		}
		System.out.println("Child2 handle is " + child2Handle);
		sf.assertEquals(wd.getTitle(), "Facebook - log in or sign up", "Title doesn't match");
		wd.switchTo().window(parentWindowHandle);
	
		//Navigating to third tab
		wdwait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("div.post-body.entry-content a:nth-of-type(3)")));
		thirdLink.click();
		allWindowHandlesList = wd.getWindowHandles();
		for (String handle : allWindowHandlesList) {
			if (!(handle.equals(parentWindowHandle) || handle.equals(child1Handle) || handle.equals(child2Handle))) {
				wd.switchTo().window(handle);
				child3Handle = handle;
			}
		}
		System.out.println("Child3 handle is " + child3Handle);
		sf.assertEquals(wd.getTitle(),
				"Yahoo | Mail, Weather, Search, News, Finance, Sports, Shopping, Entertainment, Video",
				"Title doesn't match");
		wd.switchTo().window(parentWindowHandle);
		
	    //Navigating to fourth link on same window	
		wdwait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector("div.post-body.entry-content a:last-of-type")));

		fourthLink.click();
		sf.assertEquals(wd.getTitle(), "Facebook - log in or sign up", "Title doesn't match");
		sf.assertAll();

	}

	@AfterMethod
	public void tearDown() {
		// closing the browser
		wd.quit();
	}
}
