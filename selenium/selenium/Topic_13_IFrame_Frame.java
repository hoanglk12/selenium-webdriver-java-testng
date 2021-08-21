package selenium;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_IFrame_Frame {

	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Actions action;
	WebDriverWait explicitWait;
	String autoITPath = projectPath + "\\autoIT\\authen_firefox.exe";
	Select select;
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
	public void TC_01_Iframe() {
		driver.get("https://kyna.vn/");
		List<WebElement> popupKyna = driver.findElements(By.cssSelector("div.fancybox-inner img"));
		if(popupKyna.size() > 0 && popupKyna.get(0).isDisplayed()) {
			driver.findElement(By.cssSelector(".fancybox-item.fancybox-close")).click();
		}
		sleepInSecond(2);
		scrollToBottomByJs();
		sleepInSecond(3);
		
		//switch to iframe fanpage fb
		driver.switchTo().frame(driver.findElement(By.cssSelector(".face-content iframe")));
		sleepInSecond(1);
		//Assert: 168K likes
		Assert.assertEquals(driver.findElement(By.xpath("//span[@aria-label='Trang đã xác minh']/parent::div/following-sibling::div")).getText(), "168K lượt thích");
		
		//switch to parent frame
		driver.switchTo().defaultContent();
		sleepInSecond(1);
		
		//switch to iframe chat
		driver.switchTo().frame(driver.findElement(By.id("cs_chat_iframe")));
		sleepInSecond(1);
		clickElement(By.cssSelector("div.button_text+div"));
		sleepInSecond(2);
		
		//Input name, phone no, support service and message
		String name = "John Wick";
		String phone = "0909444555";
		String message = "Java Bootcamp";
		sendKeysToElement(By.cssSelector("input.input_name"), name);
		sendKeysToElement(By.cssSelector("input.input_phone"), phone);
		select = new Select(driver.findElement(By.id("serviceSelect")));
		select.selectByVisibleText("TƯ VẤN TUYỂN SINH");
		sendKeysToElement(By.cssSelector("textarea.meshim_widget_widgets_TextArea"), message);
		clickElement(By.cssSelector("div.container input.meshim_widget_widgets_ConnAwareSubmit"));
		sleepInSecond(2);
		
		//Assert name, phone, message
		Assert.assertEquals(driver.findElement(By.cssSelector("div.floater_inner_seriously>label.logged_in_name")).getText(), name);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.floater_inner_seriously>label.logged_in_phone")).getText(), phone);
		Assert.assertEquals(driver.findElement(By.cssSelector("textarea.meshim_widget_widgets_TextArea")).getText(), message);
		
		//switch to parent frame
		driver.switchTo().defaultContent();
		sleepInSecond(1);
		
		//Input Excel in search textbox
		String searchText = "Excel";
		sendKeysToElement(By.id("live-search-bar"), searchText);
		clickElement(By.cssSelector(".search-button"));
		List<String> courseNameTexts = driver.findElements(By.cssSelector(".box-style+h4")).stream().map(course->course.getText()).collect(Collectors.toList());
		//Assert: verify total course = 10
		Assert.assertEquals(courseNameTexts.size(), 10);
		
		//Assert: verify coursename contain Excel
		for (String courseText : courseNameTexts) {
			Assert.assertTrue(courseText.contains(searchText));
		}
		
	}

	@Test
	public void TC_02_Frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		sleepInSecond(1);
		
		//Switch to frame login
		driver.switchTo().frame("login_page");
		sendKeysToElement(By.cssSelector("input.form-control"), "automation");
		clickElement(By.xpath("//a[text()='CONTINUE']"));
		sleepInSecond(1);
		
		//Assert: Verify password textbox is displayed
		Assert.assertTrue(driver.findElement(By.id("fldPasswordDispId")).isDisplayed());
		sleepInSecond(1);
		
		//Click Terms and Conditions
		clickElement(By.xpath("//div[@class='footer-btm']/a[text()='Terms and Conditions']"));
		
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