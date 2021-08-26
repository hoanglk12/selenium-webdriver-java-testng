package testng;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Topic_04_DataProvider {
	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	By emailTextbox = By.id("email");
	By passwordTextbox = By.id("pass");
	By loginButton = By.name("send");
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test(dataProvider = "email_pass")
	public void TC_01_Login_To_System(String emailAddress, String password) {
		driver.get("http://live.demoguru99.com/index.php/customer/account/login/");
	
		
		driver.findElement(emailTextbox).sendKeys(emailAddress);
		driver.findElement(passwordTextbox).sendKeys(password);
		driver.findElement(loginButton).click();
		
		// isDisplayed
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div[contains(.,'" + emailAddress + "')]/p")).isDisplayed());
		driver.findElement(By.cssSelector(".skip-link.skip-account")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();
		
	}
	@DataProvider(name = "email_pass")
	public Object[][] EmailAddressAndPasswordData(){
		return new Object[][] {
			{"selenium_11_01@gmail.com","111111"},
			{"selenium_11_02@gmail.com","111111"},
			{"selenium_11_03@gmail.com","111111"}
		};
		
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}
	
	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}
	
	public void sleepInSecond(long timeOutSecond) {
		try {
			Thread.sleep(timeOutSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
