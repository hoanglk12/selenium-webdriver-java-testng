package selenium;

import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_V_Mixing {

	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Actions action;
	WebDriverWait explicitWait;
	String autoITPath = projectPath + "\\autoIT\\authen_firefox.exe";
	By confirmEmailTextbox = By.xpath("//input[@name='reg_email_confirmation__']");
	By startButton = By.cssSelector("#start>button");
	By loadingIcon = By.cssSelector(".example #loading");
	By helloWorldText = By.cssSelector("#finish>h4");
	By dateTimePicker = By.cssSelector("div.calendarContainer");
	By ajaxLoadingIcon = By.xpath("//div[not(@style='display:none;')]//div[@class='raDiv']");
	By dateSelect = By.xpath("//td//a[text()='3']");
	By dateSelected = By.xpath("//td[@class='rcSelected']//a[text()='3']");
	By addFilesButton = By.cssSelector("#uploadFile-Input[type='file']");
	By successUploadMessage = By.cssSelector(".callout.callout-success>h5");
	
	String imageAlexFerguson = "alex-ferguson.jpg";
	
	// Using File.separator (no need to care about \ or / )
	String uploadFilePath = projectPath + File.separator + "uploadFiles" + File.separator;
	String imageAlexFergusonFilePath = uploadFilePath + imageAlexFerguson;
	
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		jsExecutor = (JavascriptExecutor) driver;
		action = new Actions(driver);
		
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//explicitWait = new WebDriverWait(driver, 15);
	}

	//@Test
	public void TC_01_Implicit_Only_Not_Found() {
	
		driver.get("https://www.facebook.com/");
		
		//->Output: Throw exception: NoSuchElement, timeout of implicit ~> 8
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		System.out.println("Start time TC01: " + getDateTimeSecondNow());
	
		try {
			driver.findElement(By.cssSelector("input#tiki")).isDisplayed();
		} finally {
			System.out.println("End time TC01: " + getDateTimeSecondNow());
		}
		
	}

	//@Test
	public void TC_02_Explicit_Only_Not_Found_WebElement() {
		
		driver.get("https://www.facebook.com/");
		
		//->Output: Throw exception: NoSuchElement, timeout of implicit ~> 0
		
		explicitWait = new WebDriverWait(driver, 5);
		System.out.println("Start time TC01: " + getDateTimeSecondNow());
	
		try {
			driver.findElement(By.cssSelector("input#tiki")).isDisplayed();
		} finally {
			System.out.println("End time TC01: " + getDateTimeSecondNow());
		}
	}
	
	//@Test
	public void TC_02_Explicit_Only_Not_Found_By() {
		
		driver.get("https://www.facebook.com/");
		
		//->Output: Throw exception: TimeoutException and NoSuchElementException, timeout ~> 5s
		
		explicitWait = new WebDriverWait(driver, 5);
		System.out.println("Start time TC01: " + getDateTimeSecondNow());
	
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#tiki")));
		} finally {
			System.out.println("End time TC01: " + getDateTimeSecondNow());
		}
	}

	//@Test
	public void TC_03_Mixing_Not_Found() {
		//When Implicit > Explicit
		driver.get("https://www.facebook.com/");
		
		
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 5);
		
		//->Output: Throw exception: NoSuchElement, timeout of implicit ~> 8
		System.out.println("Start time TC03 findElement: " + getDateTimeSecondNow());
		try {
			driver.findElement(By.cssSelector("input#tiki"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("End time TC03 findElement: " + getDateTimeSecondNow());
		
		//->Output: Timeout for findElement is 8s, Timeout for Exception is 5s
		System.out.println("Start time TC03 By: " + getDateTimeSecondNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#tiki")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End time TC03 By: " + getDateTimeSecondNow());
		
	}
	
	@Test
	public void TC_04_Mixing_Not_Found() {
		//When Implicit < Explicit
		driver.get("https://www.facebook.com/");
		
		
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 10);
		
		//->Output: Throw exception: NoSuchElement, timeout of implicit ~> 8
		System.out.println("Start time TC04 findElement: " + getDateTimeSecondNow());
		try {
			driver.findElement(By.cssSelector("input#tiki"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("End time TC04 findElement: " + getDateTimeSecondNow());
		
		
		//->Output: Throw exception: TimeoutException and NoSuchElementException (for findElement) with weird time
		//Each run has different time
		System.out.println("Start time TC04 By: " + getDateTimeSecondNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#tiki")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End time TC04 By: " + getDateTimeSecondNow());
		
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public String getDateTimeSecondNow() {
		Date date = new Date();
		return date.toString();
	}
	
	public void switchToWindowById(String parentId) {
		Set<String> allWindowIds = driver.getWindowHandles();
		for (String windowId : allWindowIds) {
			if (!windowId.equals(parentId)) {
				driver.switchTo().window(windowId);
				break;
			}
		}
	}
	
	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean isExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElement(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				getElement(locator));
		return status;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
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