package com.HarinderKaur.AssignmentWeek16;

import java.time.Duration;

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

public class ValidateItemPurchase {

	//verifying jira integration
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
		wd.get("https://www.demoblaze.com/index.html");
		wd.manage().window().maximize();

	}

	@Test
	public void validateItemPurchase() {

		// Validate the title of home page
		sf.assertEquals(wd.getTitle(), "STORE", "Home page title doesn't match");

		// Select one product
		wdwait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("div.col-lg-4.col-md-6.mb-4:nth-of-type(5) h4.card-title a")));
		WebElement iphone6 = wd
				.findElement(By.cssSelector("div.col-lg-4.col-md-6.mb-4:nth-of-type(5) h4.card-title a"));
		iphone6.click();

		// Validating name and price of selected phone
		wdwait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.col-md-7.col-sm-12.col-xs-12 h2")));
		WebElement phoneTitle = wd.findElement(By.cssSelector("div.col-md-7.col-sm-12.col-xs-12 h2"));
		WebElement phonePrice = wd.findElement(By.cssSelector("div.col-md-7.col-sm-12.col-xs-12 h3"));

		sf.assertEquals(phoneTitle.getText(), "Iphone 6 32gb", "Title doesn't match");
		sf.assertEquals(phonePrice.getText(), "$790 *includes tax", "Price doesn't match");

		// Phone added to cart
		WebElement addToCartBtn = wd.findElement(By.cssSelector("div.col-sm-12.col-md-6.col-lg-6 a"));
		addToCartBtn.click();

		// Alert
		wdwait.until(ExpectedConditions.alertIsPresent());
		wd.switchTo().alert().accept();

		// Click on cart Button
		WebElement cartBtn = wd.findElement(By.cssSelector("ul.navbar-nav.ml-auto li:nth-of-type(4) a"));
		cartBtn.click();

		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tr.success td:nth-of-type(2)")));
		WebElement phoneTitleInCart = wd.findElement(By.cssSelector("tr.success td:nth-of-type(2)"));
		WebElement phonePriceInCart = wd.findElement(By.cssSelector("tr.success td:nth-of-type(3)"));
		WebElement totalPriceInCart = wd.findElement(By.cssSelector("h3#totalp"));
		sf.assertEquals(phoneTitleInCart.getText(), "Iphone 6 32gb", "Title doesn't match");
		sf.assertEquals(phonePriceInCart.getText(), "790", "price doesn't match");
		sf.assertEquals(totalPriceInCart.getText(), "790", "total doesn't match");

		// Place the order
		wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.col-lg-1 button")));
		WebElement placeOrderBtn = wd.findElement(By.cssSelector("div.col-lg-1 button"));
		placeOrderBtn.click();

		// Fill the purchase details form
		WebElement nameInput = wd.findElement(By.cssSelector("#orderModal div.form-group #name"));
		WebElement countryInput = wd.findElement(By.cssSelector("#orderModal div.form-group #country"));
		WebElement cityInput = wd.findElement(By.cssSelector("#orderModal div.form-group #city"));
		WebElement creditCardInput = wd.findElement(By.cssSelector("#orderModal div.form-group #card"));
		WebElement monthInput = wd.findElement(By.cssSelector("#orderModal div.form-group #month"));
		WebElement yearInput = wd.findElement(By.cssSelector("#orderModal div.form-group #year"));
		nameInput.sendKeys("Harinder");
		countryInput.sendKeys("Canada");
		cityInput.sendKeys("Edmonton");
		creditCardInput.sendKeys("23457810991");
		monthInput.sendKeys("December");
		yearInput.sendKeys("2022");

		//Click purchase button
		wdwait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("#orderModal div.modal-footer button:last-of-type")));
		WebElement purchaseBtn = wd.findElement(By.cssSelector("#orderModal div.modal-footer button:last-of-type"));
		purchaseBtn.click();

		//Purchase confirmation alert
		wdwait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("div.sweet-alert.showSweetAlert.visible h2")));
		WebElement thankYouMsgAlert = wd.findElement(By.cssSelector("div.sweet-alert.showSweetAlert.visible h2"));
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.sweet-alert p")));
		String purchaseDetailsOnAlert = wd.findElement(By.cssSelector("div.sweet-alert p")).getText();
		String purchaseDetailsExpected = "Amount: 790 USD\nCard Number: 23457810991\nName: Harinder";
		sf.assertEquals(thankYouMsgAlert.getText(), "Thank you for your purchase!", "thank you message doesn't match");
		sf.assertTrue(purchaseDetailsOnAlert.contains(purchaseDetailsExpected), "Purchase deatils doesn't match");
		wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.confirm.btn.btn-lg.btn-primary")));
		WebElement okBtnAlert = wd.findElement(By.cssSelector("button.confirm.btn.btn-lg.btn-primary"));
		okBtnAlert.click();
		
		//Validating home page
		sf.assertEquals(wd.getTitle(), "STORE", "Page doesn't navigate back to the home page");
		sf.assertAll();

	}

	@AfterMethod
	public void tearDown() {
		// wd.quit();
	}
}
