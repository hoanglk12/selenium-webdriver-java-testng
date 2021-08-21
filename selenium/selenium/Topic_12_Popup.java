package selenium;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Popup {

	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Actions action;
	WebDriverWait explicitWait;
	String autoITPath = projectPath + "\\autoIT\\authen_firefox.exe";
	
	@BeforeClass
	public void beforeClass() {
		//System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		//driver = new ChromeDriver();
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		jsExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 25);
		action = new Actions(driver);
	}

	@Test
	public void TC_01_Fixed_Popup() {
		driver.get("https://ngoaingu24h.vn/");
		
		//Click Login
		clickElement(By.cssSelector("button.login_"));
		sleepInSecond(3);
		//Assert
		Assert.assertTrue(driver.findElement(By.cssSelector("div#modal-login-v1>div")).isDisplayed());
		
		//Input username, password
		sendKeysToElement(By.id("account-input"), "automationfc");
		sendKeysToElement(By.id("password-input"), "automationfc");
		clickElement(By.cssSelector(".btn-v1.btn-login-v1"));
	
		//Assert: error msg
		Assert.assertEquals(driver.findElement(By.xpath("//button[text()='Đăng nhập' and contains(@class,'btn-v1')]/preceding-sibling::div[contains(@class,'error-login-panel')]")).getText(), "Tài khoản không tồn tại!");
		
		clickElement(By.cssSelector("div#modal-login-v1 .close"));
		
		//Assert
		Assert.assertFalse(driver.findElement(By.cssSelector("div#modal-login-v1>div")).isDisplayed());
	}

	@Test
	public void TC_02_Random_Popup_In_Dom() {
		driver.get("https://blog.testproject.io/");
		//Check if jQuery loaded successfully
		Assert.assertTrue(isJQueryLoadedSuccess());
		By popup = By.cssSelector("div.mailch-wrap");
		
		if (driver.findElement(popup).isDisplayed()) {
			clickElement(By.cssSelector("#close-mailch"));
		}
		
		//Input 'Selenium' in Search
		driver.findElement(By.cssSelector("#search-2 input.search-field")).sendKeys("Selenium");
		
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#search-2 span.glass")));
		driver.findElement(By.cssSelector("#search-2 span.glass")).click();
		
		//Assert all title contains 'Selenium'
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("h3.post-title>a")));
		List <String> allTitleTexts = driver.findElements(By.cssSelector(".post-title>a")).stream().map(itemTitle->itemTitle.getText()).collect(Collectors.toList());
		for (String titleText : allTitleTexts) {
			Assert.assertTrue(titleText.contains("Selenium"));
		}
	}

	@Test
	public void TC_04_Random_Popup_Not_In_Dom() {
		driver.get("https://shopee.vn/");
		List<WebElement> popupShopee = driver.findElements(By.cssSelector("#modal img"));
		if(popupShopee.size() > 0 && popupShopee.get(0).isDisplayed()) {
			System.out.println("Popup is displayed");
			driver.findElement(By.cssSelector(".shopee-popup__close-btn")).click();
		}else {
			System.out.println("Popup is NOT displayed");
		}
		sendKeysToElement(By.cssSelector(".shopee-searchbar-input>input"), "Macbook Pro");
		clickElement(By.cssSelector(".shopee-searchbar__main+button"));
		sleepInSecond(2);
		
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='yQmmFK _1POlWt _36CEnF']")));
		List <String> allProductTexts = driver.findElements(By.xpath("//div[@class='yQmmFK _1POlWt _36CEnF']")).stream().map(itemTitle->itemTitle.getText()).collect(Collectors.toList());
		for (String productText : allProductTexts) {
			Assert.assertTrue(productText.toLowerCase().contains("macbook"));
			Assert.assertTrue(productText.toLowerCase().contains("pro"));
		}
	}
	

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public boolean isJQueryLoadedSuccess() {
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
		};
		return explicitWait.until(jQueryLoad);
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
	
	public void scrollToElementByJs (By by) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
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