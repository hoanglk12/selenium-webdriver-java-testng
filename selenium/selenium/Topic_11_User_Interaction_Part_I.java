package selenium;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_User_Interaction_Part_I {

	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Actions action;
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
		action = new Actions(driver);
	}

	@Test
	public void TC_01_Hover_To_Element_Tooltip() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		//Hover mouse to textbox
		action.moveToElement(driver.findElement(By.cssSelector("#age"))).perform();
		sleepInSecond(2);
		
		//Assert message in tooltip
		Assert.assertEquals(driver.findElement(By.cssSelector(".ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
	}

	@Test
	public void TC_02_Hover_To_Element() {
		driver.get("http://www.myntra.com/");
		//Hover mouse to KIDS link -> perform
		action.moveToElement(driver.findElement(By.xpath("//div[@id='desktop-headerMount']//a[text()='Kids']"))).perform();
		sleepInSecond(3);
		
		//Click on Home and Bath
		clickElement(By.xpath("//a[text()='Home & Bath']"));
		sleepInSecond(3);
		
		//Assert: currentUrl, Kids Home Bath (span and h1) are displayed
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='breadcrumbs-crumb' and text()='Kids Home Bath']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='title-container']/h1")).isDisplayed());
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.myntra.com/kids-home-bath");
	}

	@Test
	public void TC_03_Hover_To_Element() {
		driver.get("https://vnexpress.net/");
		//Hover mouse to Thế giới menu -> perform
		action.moveToElement(driver.findElement(By.cssSelector(".parent .thegioi"))).perform();
		sleepInSecond(5);
		
		//Assert: verify all sub-menu in Thế giới menu -> perform
		Assert.assertTrue(driver.findElement(By.xpath("//a[@title='Tư liệu' and text()='Tư liệu']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[@title='Phân tích' and text()='Phân tích']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[@title='Người Việt 5 châu' and text()='Người Việt 5 châu']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[@title='Cuộc sống đó đây' and text()='Cuộc sống đó đây']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[@title='Quân sự' and text()='Quân sự']")).isDisplayed());
	}

	@Test
	public void TC_04_Click_And_Hold_Element_Multiple() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> squareNumberItems = driver.findElements(By.cssSelector(".ui-selectable>li"));
		
		//Click and Hold on 1st element -> hover to target element -> release -> perform
		//1-4
		action.clickAndHold(squareNumberItems.get(0)).moveToElement(squareNumberItems.get(3)).release().perform();
		sleepInSecond(2);
		
		//Assert: 1-4 selected
		Assert.assertEquals(driver.findElements(By.cssSelector(".ui-selectable>.ui-selected")).size(), 4);
		
		//Click and Hold on 1st element -> hover to target element -> release -> perform
		//1-12
		action.clickAndHold(squareNumberItems.get(0)).moveToElement(squareNumberItems.get(11)).release().perform();
		sleepInSecond(2);
				
		//Assert: 1-12 selected
		Assert.assertEquals(driver.findElements(By.cssSelector(".ui-selectable>.ui-selected")).size(), 12);
	}
	
	@Test
	public void TC_05_Click_And_Hold_Element_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> squareNumberItems = driver.findElements(By.cssSelector(".ui-selectable>li"));
		
		//Click Ctrl on 1st element
		action.keyDown(Keys.CONTROL).perform();
		//Click target elements (click action)
		action.click(squareNumberItems.get(0));
		action.click(squareNumberItems.get(2));
		action.click(squareNumberItems.get(5));
		action.click(squareNumberItems.get(10));
		action.keyUp(Keys.CONTROL).perform();
		sleepInSecond(2);
		
		//Assert: 4 are selected and 1,3,6,8 displayed
		Assert.assertEquals(driver.findElements(By.cssSelector(".ui-selectable>.ui-selected")).size(), 4);
		Assert.assertTrue(driver.findElement(By.xpath("//ol[@class='ui-selectable']/li[text()='1' and contains(@class,'ui-selected')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//ol[@class='ui-selectable']/li[text()='3' and contains(@class,'ui-selected')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//ol[@class='ui-selectable']/li[text()='6' and contains(@class,'ui-selected')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//ol[@class='ui-selectable']/li[text()='11' and contains(@class,'ui-selected')]")).isDisplayed());
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
}