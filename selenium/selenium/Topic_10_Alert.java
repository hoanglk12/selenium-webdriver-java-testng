package selenium;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Alert {

	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Alert alert;
	WebDriverWait explicitWait;
	String autoITPath = projectPath + "\\autoIT\\authen_firefox.exe";
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		jsExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 15);
	}

	@Test
	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		clickElement(By.xpath("//button[text()='Click for JS Alert']"));
//		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		waitForAlertPresence(driver);
		sleepInSecond(5);
		
		//Assert: 
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		
		//Accept alert
		acceptAlert(driver);
		
		//Assert:verify message at Result
		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You clicked an alert successfully");
	}

//	@Test
	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		clickElement(By.xpath("//button[text()='Click for JS Confirm']"));
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		sleepInSecond(2);
		
		//Assert: 
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		
		//Cancel alert
		alert.dismiss();
		
		//Assert:verify message at Result
		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You clicked: Cancel");
		
	}

//	@Test
	public void TC_03_Prompt_Alert() {
		String dataInputAlert = "Automation Test Engineer";
		driver.get("https://automationfc.github.io/basic-form/index.html");
		clickElement(By.xpath("//button[text()='Click for JS Prompt']"));
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		sleepInSecond(2);
		
		//Assert: 
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		
		//Accept alert
		alert.sendKeys(dataInputAlert);
		alert.accept();
		
		//Assert:verify message at Result
		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You entered: "+ dataInputAlert);
		
	}

//	@Test
	public void TC_04_Authentication_Alert() {
		driver.get("http://the-internet.herokuapp.com");
		String hrefUrl = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		//System.out.println("hrefUrl = " + hrefUrl);
		accessSplitandJoinUrl(hrefUrl, "admin", "admin");
		sleepInSecond(1);
		//Assert: verify text message
		Assert.assertEquals(driver.findElement(By.cssSelector(".example>p")).getText(), "Congratulations! You must have the proper credentials.");
	}
	
//	@Test
	public void TC_05_Authentication_Alert_AutoIT() throws IOException {
		Runtime.getRuntime().exec(new String[] {autoITPath,"admin","admin"});
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		sleepInSecond(10);
		
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".example>p")));
		//Assert: verify text message
		Assert.assertEquals(driver.findElement(By.cssSelector(".example>p")).getText(), "Congratulations! You must have the proper credentials.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public boolean isElementDisplayed(By by) {
		if (driver.findElement(by).isDisplayed()) {
			System.out.println(by + " is displayed");
			return true;
		} else {
			System.out.println(by + " is not displayed");
			return false;
		}
	}

	public boolean isElementEnabled(By by) {
		if (driver.findElement(by).isEnabled()) {
			System.out.println(by + " is enabled");
			return true;
		} else {
			System.out.println(by + " is disabled");
			return false;
		}
	}

	public boolean isElementSelected(By by) {
		if (driver.findElement(by).isSelected()) {
			System.out.println(by + " is selected");
			return true;
		} else {
			System.out.println(by + " is de-selected");
			return false;
		}
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
	
	public void removeAttributeByJs(By by) {
		jsExecutor.executeScript("arguments[0].removeAttribute('disabled')", driver.findElement(by));
	}
	
	public void checkToCheckboxOrRadioButton(By by) {
		WebElement checkbox = driver.findElement(by);
		if(!checkbox.isSelected()) {
			checkbox.click();
		}
	}
	
	public void uncheckToCheckbox(By by) {
		WebElement checkbox = driver.findElement(by);
		if(checkbox.isSelected()) {
			checkbox.click();
		}
	}
	
	public void accessSplitandJoinUrl(String url, String username, String password) {
		String[] hrefUrl = url.split("//");
		url = hrefUrl[0] + "//" + username + ":" + password + "@" + hrefUrl[1];
		driver.get(url);
	}
	
	public String generateEmail() {
		Random rand = new Random();
		return rand.nextInt(9999) + "@mail.com";
	}

	public void sleepInSecond(long timeOutSecond) {
		try {
			Thread.sleep(timeOutSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Alert waitForAlertPresence(WebDriver driver) {
	
		return alert = explicitWait.until(ExpectedConditions.alertIsPresent());
	}
	public void acceptAlert(WebDriver driver) {
		waitForAlertPresence(driver);
		alert.accept();
	}
	public void cancelAlert(WebDriver driver) {
		waitForAlertPresence(driver);
		alert.dismiss();
	}

	public void sendkeyToAlert(WebDriver driver, String value) {
		waitForAlertPresence(driver);
		alert.sendKeys(value);
	}
}