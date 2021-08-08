package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Xpath_Css_Part_II_Technical {
	// Variable driver for Selenium WebDriver
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		// Open Firefox Browser
		System.setProperty("webdriver.gecko.driver", ".\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		// Waiting time for element to be presented in a range time (10s)
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Login_Empty_Email_Password() {
		// Open AUT/SUT
		driver.get("http://live.demoguru99.com/index.php");

		// Click My Account link at footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// Leave blank for email, password and click Login
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("pass")).sendKeys("");
		driver.findElement(By.id("send2")).click();

		// Assert
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),
				"This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),
				"This is a required field.");
	}

	@Test
	public void TC_02_Login_Invalid_Email() {
		// Open AUT/SUT
		driver.get("http://live.demoguru99.com/index.php");

		// Click My Account link at footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// Input invalid email and correct password
		driver.findElement(By.id("email")).sendKeys("123434234@12312.123123");
		driver.findElement(By.id("pass")).sendKeys("123456");
		driver.findElement(By.id("send2")).click();

		// Assert
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(),
				"Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_03_Login_Invalid_Password() {
		// Open AUT/SUT
		driver.get("http://live.demoguru99.com/index.php");

		// Click My Account link at footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// Input correct email and invalid password
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123");
		driver.findElement(By.id("send2")).click();

		// Assert
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(),
				"Please enter 6 or more characters without leading or trailing spaces.");
	}

	@Test
	public void TC_04_Login_Incorrect_Password() {
		// Open AUT/SUT
		driver.get("http://live.demoguru99.com/index.php");

		// Click My Account link at footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// Input correct email and incorrect password
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123123123");
		driver.findElement(By.id("send2")).click();

		// Assert
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(),
				"Invalid login or password.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
