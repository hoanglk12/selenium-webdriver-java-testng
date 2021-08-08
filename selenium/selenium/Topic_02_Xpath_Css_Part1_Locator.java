package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Xpath_Css_Part1_Locator {
	//Variable driver for Selenium WebDriver
	WebDriver driver;
	@BeforeClass
	public void beforeClass() {
		//Open Firefox Browser
		System.setProperty("webdriver.gecko.driver", ".\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		//Waiting time for element to be presented in a range time (30s)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//Open AUT/SUT
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
	}

	@Test
	public void TC_01_ID() {
		//Input 'Automation' in Firstname textbox
		driver.findElement(By.id("FirstName")).sendKeys("Automation");;
		sleepInSecond(3);
		
		//Click on the male radio button
		driver.findElement(By.id("gender-male")).click();
		sleepInSecond(3);
	}

	@Test
	public void TC_02_Class() {
		// Refresh page
		driver.navigate().refresh();
		
		//Input 'Macbook' in search box text
		driver.findElement(By.className("search-box-text")).sendKeys("Macbook");
		sleepInSecond(3);
		
		//Click on the search box button
		driver.findElement(By.className("search-box-button")).click();
		sleepInSecond(3);
	}

	@Test
	public void TC_03_Name() {
		//Open AUT/SUT again
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		
		//Input 'dam@gmail.com' in field 'Email'
		driver.findElement(By.name("Email")).sendKeys("");
		sleepInSecond(3);
		
		// Click on Newsletter
		driver.findElement(By.name("Newsletter")).click();
		sleepInSecond(3);
	}
	
	@Test
	public void TC_04_Tagname() {
		// Find total links
		System.out.println("Total links: " + driver.findElements(By.tagName("a")).size());
		
		// Find total inputs
		System.out.println("Total links: " + driver.findElements(By.tagName("input")).size());
	}
	
	@Test
	public void TC_05_LinkText() {
		// Click on Login link
		driver.findElement(By.linkText("Log in")).click();
		sleepInSecond(3);
	}
	
	@Test
	public void TC_06_Partial_LinkText() {
		// Click 'Recently viewed products' 
		driver.findElement(By.partialLinkText("Recently viewed products")).click();
		sleepInSecond(3);
		
		
		driver.findElement(By.partialLinkText("viewed products")).click();
		sleepInSecond(3);
		
		
		driver.findElement(By.partialLinkText("Recently viewed")).click();
		sleepInSecond(3);
	}
	
	@Test
	public void TC_07_Css() {
		//Open AUT/SUT again
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		
		//Input 'Automation FC' in Firstname textbox
		driver.findElement(By.cssSelector("input[id='FirstName']")).sendKeys("Automation FC");
		sleepInSecond(3);
		
		//Input 'Macbook' in search box text
		driver.findElement(By.cssSelector("input[class='search-box-text ui-autocomplete-input']")).sendKeys("Macbook");
		sleepInSecond(3);
				
		//Input 'dam@gmail.com' in field 'Email'
		driver.findElement(By.cssSelector("input[name='Email']")).sendKeys("dam@gmail.com");
		sleepInSecond(3);
		
		// Click on Login link
		driver.findElement(By.cssSelector("a[href*='login?']")).click();
		sleepInSecond(3);
	}
	
	@Test
	public void TC_08_Xpath() {
		//Open AUT/SUT again
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
				
		//Input 'Automation FC' in Firstname textbox
		driver.findElement(By.xpath("//input[@id='FirstName']")).sendKeys("Automation FC");
	    sleepInSecond(3);
				
		//Input 'Macbook' in search box text
		driver.findElement(By.xpath("//input[@class='search-box-text ui-autocomplete-input']")).sendKeys("Macbook");
		sleepInSecond(3);
						
		//Input 'dam@gmail.com' in field 'Email'
		driver.findElement(By.xpath("//input[@name='Email']")).sendKeys("dam@gmail.com");
		sleepInSecond(3);
			
		// Click on Login link
		driver.findElement(By.xpath("//a[text()='Log in']")).click();
		sleepInSecond(3);
		
		// Click 'Recently viewed products' 
		driver.findElement(By.xpath("//a[contains(text(),'Recently viewed')]")).click();
		sleepInSecond(3);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public void sleepInSecond(long timeOutSecond) {
		try {
			Thread.sleep(timeOutSecond*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
