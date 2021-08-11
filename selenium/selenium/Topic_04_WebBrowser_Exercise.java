package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class Topic_04_WebBrowser_Exercise {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@Test
	public void TC_01_Verify_Url() {
		// Setup FirefoxDriver
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.get("http://live.demoguru99.com/");

		// Click My Account at footer
		driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/login/");

		// Click Create An Account button
		driver.findElement(By.cssSelector(".col2-set a.button[title='Create an Account']")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/create/");
	}

	@Test
	public void TC_02_Verify_Title() {
		// Click on logo to back to Homepage
		driver.findElement(By.cssSelector(".logo .large")).click();

		// Click My Account at footer
		driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.getTitle(), "Customer Login");

		// Click Create An Account button
		driver.findElement(By.cssSelector(".col2-set a.button[title='Create an Account']")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}

	@Test
	public void TC_03_Verify_Navigation() {
		// Click on logo to back to Homepage
		driver.findElement(By.cssSelector(".logo .large")).click();

		// Click My Account at footer
		driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
		sleepInSecond(3);

		// Click Create An Account button
		driver.findElement(By.cssSelector(".col2-set a.button[title='Create an Account']")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/create/");

		// Back to Login page
		driver.navigate().back();
		sleepInSecond(3);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/login/");

		// Forward to Register page
		driver.navigate().forward();
		sleepInSecond(3);
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}

	@Test
	public void TC_04_Verify_Page_Source() {
		// Click on logo to back to Homepage
		driver.findElement(By.cssSelector(".logo .large")).click();

		// Click My Account at footer
		driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
		sleepInSecond(3);
		String currentPageSource = driver.getPageSource();
		Assert.assertTrue(currentPageSource.contains("Login or Create an Account"));

		// Click Create An Account button
		driver.findElement(By.cssSelector(".col2-set a.button[title='Create an Account']")).click();
		sleepInSecond(3);
		currentPageSource = driver.getPageSource();
		Assert.assertTrue(currentPageSource.contains("Create an Account"));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInSecond(long timeOutSecond) {
		try {
			Thread.sleep(timeOutSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
