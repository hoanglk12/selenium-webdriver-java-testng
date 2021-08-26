package testng;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Topic_05_Parameter {
	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Select selectDay;
	Select selectMonth;
	Select selectYear;
	
	By emailTextbox = By.id("email");
	By passwordTextbox = By.id("pass");
	By loginButton = By.name("send");
	String firstName, lastName, day, month, year, email, companyName, password, confirmPassword;
	@Parameters({"browser","url"})
	@BeforeClass
	public void beforeClass(String browserName, String urlPage) {
		System.out.println("Run with " + browserName);
		if(browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}else if(browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if(browserName.equals("edge")) {
			System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}else {
			throw new RuntimeException("Please input the correct browserName");
		}
		
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		// Data test for Register Page
		firstName = "John";
		lastName = "Cena";
		day = "23";
		month = "April";
		year = "1977";
		email = "johncena" + generateEmail();
		companyName = "Wayne Enterprise";
		password = "123456";
		confirmPassword = "123456";
		driver.get(urlPage);
	}
	
	@Test
	public void TC_01_Register_NopCommerce() {
		
		// Click Register link
		clickElement(By.cssSelector(".ico-register"));
		sleepInSecond(2);

		// Input all mandatory fields in Register page
		sendKeysToElement(By.id("FirstName"), firstName);
		sendKeysToElement(By.id("LastName"), lastName);
		selectDay = new Select(driver.findElement(By.name("DateOfBirthDay")));
		selectDay.selectByVisibleText(day);
		selectMonth = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		selectMonth.selectByVisibleText(month);
		selectYear = new Select(driver.findElement(By.name("DateOfBirthYear")));
		selectYear.selectByVisibleText(year);
		sendKeysToElement(By.id("Email"), email);
		sendKeysToElement(By.id("Company"), companyName);
		sendKeysToElement(By.id("Password"), password);
		sendKeysToElement(By.id("ConfirmPassword"), confirmPassword);

		// Click Register
		clickByJs(By.id("register-button"));
		sleepInSecond(2);

		// Assert
		Assert.assertEquals(driver.findElement(By.cssSelector(".page-body>.result")).getText(),
				"Your registration completed");
		
	}
	
	@Test
	public void TC_02_NopCommerce_Verify_MyAccount() {
		clickElement(By.cssSelector(".ico-account"));
		sleepInSecond(2);

		// Assert information in My Account match with Register Page
		Assert.assertEquals(driver.findElement(By.id("FirstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.id("LastName")).getAttribute("value"), lastName);
		selectDay = new Select(driver.findElement(By.name("DateOfBirthDay")));
		selectMonth = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		selectYear = new Select(driver.findElement(By.name("DateOfBirthYear")));
		Assert.assertEquals(selectDay.getFirstSelectedOption().getText(), day);
		Assert.assertEquals(selectMonth.getFirstSelectedOption().getText(), month);
		Assert.assertEquals(selectYear.getFirstSelectedOption().getText(), year);
		Assert.assertEquals(driver.findElement(By.id("Email")).getAttribute("value"), email);
		Assert.assertEquals(driver.findElement(By.id("Company")).getAttribute("value"), companyName);
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public void sendKeysToElement(By by, String key) {
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(key);
	}

	public void clickElement(By by) {
		driver.findElement(by).click();
	}

	public void clickByJs(By by) {
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(by));
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
	public String generateEmail() {
		Random rand = new Random();
		return rand.nextInt(9999) + "@mail.com";
	}
}
