package selenium;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Windows_Tab {

	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Actions action;
	WebDriverWait explicitWait;
	String autoITPath = projectPath + "\\autoIT\\authen_firefox.exe";
	Select select;
	Alert alert;
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
	public void TC_01_Windows_Tab() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		String parentWindowId = driver.getWindowHandle();
		clickElement(By.xpath("//div[@class='container']/a[text()='GOOGLE']"));
		sleepInSecond(1);
		
		//Switch to Google window and verify title
		switchToWindowByTitle("Google");
		Assert.assertEquals(driver.getTitle(), "Google");
		sleepInSecond(1);
		
		//Switch to parent window
		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		sleepInSecond(1);
		
		//Switch to Facebook window and verify title
		clickElement(By.xpath("//div[@class='container']/a[text()='FACEBOOK']"));
		sleepInSecond(2);
		switchToWindowByTitle("Facebook - Đăng nhập hoặc đăng ký");
		Assert.assertEquals(driver.getTitle(), "Facebook - Đăng nhập hoặc đăng ký");
		sleepInSecond(2);
		
		//Switch to parent window
		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		sleepInSecond(1);
		
		//Switch to Tiki window and verify title
		clickElement(By.xpath("//div[@class='container']/a[text()='TIKI']"));
		sleepInSecond(3);
		switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		Assert.assertEquals(driver.getTitle(), "Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		
		//Close all windows and return to parent window
		closeAllWindowsWithoutParent(parentWindowId);
		sleepInSecond(2);
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");
		
	}

	@Test
	public void TC_02_Windows_Tab() {
		driver.get("https://kyna.vn/");
		List<WebElement> popupKyna = driver.findElements(By.cssSelector("div.fancybox-inner img"));
		if(popupKyna.size() > 0 && popupKyna.get(0).isDisplayed()) {
			driver.findElement(By.cssSelector(".fancybox-item.fancybox-close")).click();
		}
		sleepInSecond(2);
		scrollToBottomByJs();
		sleepInSecond(1);
		String kynaParentId = driver.getWindowHandle();
		
		//click icon fb link
		clickElement(By.cssSelector("div.hotline a>img[alt='facebook']"));
		sleepInSecond(1);
		switchToWindowByTitle("Kyna.vn - Trang chủ | Facebook");
		sleepInSecond(3);
		Assert.assertEquals(driver.getTitle(), "Kyna.vn - Trang chủ | Facebook");
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/kyna.vn");
		sleepInSecond(3);
		
		//switch to parent id
		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		Assert.assertEquals(driver.getTitle(), "Kyna.vn - Học online cùng chuyên gia");
		sleepInSecond(1);
		
		//click icon youtube link
		clickElement(By.cssSelector("div.hotline a>img[alt='youtube']"));
		sleepInSecond(3);
		switchToWindowByTitle("Kyna.vn - YouTube");
		sleepInSecond(1);
		Assert.assertEquals(driver.getTitle(), "Kyna.vn - YouTube");
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/user/kynavn");
		sleepInSecond(2);
		
		//Close all windows and return to parent window
		closeAllWindowsWithoutParent(kynaParentId);
		sleepInSecond(1);
		Assert.assertEquals(driver.getTitle(), "Kyna.vn - Học online cùng chuyên gia");
		Assert.assertEquals(driver.getCurrentUrl(), "https://kyna.vn/");
	}

	@Test
	public void TC_03_Windows_Tab() {
		driver.get("http://live.demoguru99.com/index.php/");
		String homePageWindowId = driver.getWindowHandle();
		String productApple = "IPhone";
		String productSony = "Sony Xperia";
		clickElement(By.xpath("//a[text()='Mobile']"));
		sleepInSecond(1);
		
		//Add to Compare IPhone and Sony XPeria
		clickElement(By.xpath("//a[text()='" + productApple + "']/parent::h2/parent::div[@class='product-info']//a[text()='Add to Compare']"));
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "The product " + productApple +" has been added to comparison list.");
		clickElement(By.xpath("//a[text()='"+ productSony + "']/parent::h2/parent::div[@class='product-info']//a[text()='Add to Compare']"));
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "The product " + productSony + " has been added to comparison list.");
		sleepInSecond(2);
		
		//Click Compare -> New Window
		clickElement(By.xpath("//span[text()='Compare']/parent::span/parent::button"));
		sleepInSecond(2);
		
		//Switch to Compare window
		switchToWindowByTitle("Products Comparison List - Magento Commerce");
		sleepInSecond(1);
		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
		
		//Close window and return to homepage
		closeAllWindowsWithoutParent(homePageWindowId);
		sleepInSecond(1);
		
		//Clear all verify success message
		clickElement(By.xpath("//a[text()='Clear All']"));
		sleepInSecond(3);
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "The comparison list was cleared.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public void switchToWindowByTitle(String expectedTitle) {
		Set<String> allWindowIds =  driver.getWindowHandles();
		for (String windowId : allWindowIds) {
			driver.switchTo().window(windowId);
			String currentWindowTitle = driver.getTitle();
			if (currentWindowTitle.equals(expectedTitle)) {
				break;
			}
		}
	}
	
	public void closeAllWindowsWithoutParent(String parentWindowId) {
		Set<String> allWindowIds = driver.getWindowHandles();
		for (String windowId : allWindowIds) {
			if (!windowId.equals(parentWindowId)) {
				driver.switchTo().window(windowId);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindowId);
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
	
	public void scrollToBottomByJs() {
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
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