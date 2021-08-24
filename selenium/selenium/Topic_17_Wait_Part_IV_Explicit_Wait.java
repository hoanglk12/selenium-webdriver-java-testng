package selenium;

import java.io.File;
import java.util.Random;
import java.util.Set;

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

public class Topic_17_Wait_Part_IV_Explicit_Wait {

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
		
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//explicitWait = new WebDriverWait(driver, 15);
		
		
		//Load extension
		//System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		//ChromeOptions options = new ChromeOptions();
		//options.addExtensions(new File(projectPath + File.separator + "browserExtensions" + File.separator + "UltraSurf-v1.6.0.crx"));
		//driver = new ChromeDriver(options);
		
		driver.manage().window().maximize();
		jsExecutor = (JavascriptExecutor) driver;
		action = new Actions(driver);
		driver.navigate().refresh();
	}

	@Test
	public void TC_01_Explicit_Wait_Less_Than_Invisible() {
	
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		//Wait for startButton to be clickable
		explicitWait = new WebDriverWait(driver, 15);
		explicitWait.until(ExpectedConditions.elementToBeClickable(startButton));
		clickElement(startButton);
		
		//Wait for loadingIcon disappear
		explicitWait = new WebDriverWait(driver, 3);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		
		Assert.assertEquals(driver.findElement(helloWorldText).getText(), "Hello World!");
		
	}

	@Test
	public void TC_02_Explicit_Wait_Equal_Invisible() {
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		//Wait for startButton to be clickable
		explicitWait = new WebDriverWait(driver, 15);
		explicitWait.until(ExpectedConditions.elementToBeClickable(startButton));
		clickElement(startButton);
		
		//Wait for loadingIcon disappear
		explicitWait = new WebDriverWait(driver, 5);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		
		Assert.assertEquals(driver.findElement(helloWorldText).getText(), "Hello World!");
	}

	@Test
	public void TC_03_Explicit_Wait_Less_Than_Visible() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		//Wait for startButton to be clickable
		explicitWait = new WebDriverWait(driver, 15);
		explicitWait.until(ExpectedConditions.elementToBeClickable(startButton));
		clickElement(startButton);
		
		//Wait for loadingIcon disappear
		explicitWait = new WebDriverWait(driver, 3);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(helloWorldText));
		
		Assert.assertEquals(driver.findElement(helloWorldText).getText(), "Hello World!");
	}
	
	@Test
	public void TC_04_Explicit_Wait_Equal_Visible() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
	
		//Wait for startButton to be clickable
		explicitWait = new WebDriverWait(driver, 15);
		explicitWait.until(ExpectedConditions.elementToBeClickable(startButton));
		clickElement(startButton);
				
		//Wait for loadingIcon disappear
		explicitWait = new WebDriverWait(driver, 5);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(helloWorldText));
				
		Assert.assertEquals(driver.findElement(helloWorldText).getText(), "Hello World!");
	}
	
	@Test
	public void TC_05_Explicit_Wait_Ajax_Loading() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		explicitWait = new WebDriverWait(driver, 5);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(dateTimePicker));
		
		//Verify default message is 'No Selected Dates to display.'
		Assert.assertEquals(driver.findElement(By.xpath("//legend[text()='Selected Dates:']/following-sibling::div/span")).getText(), "No Selected Dates to display.");
		
		explicitWait = new WebDriverWait(driver, 3);
		explicitWait.until(ExpectedConditions.elementToBeClickable(dateSelect));
		clickElement(dateSelect);
		
		//Verify date selected
		explicitWait = new WebDriverWait(driver, 5);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(ajaxLoadingIcon));
		Assert.assertEquals(driver.findElement(By.xpath("//legend[text()='Selected Dates:']/following-sibling::div/span")).getText(), "Tuesday, August 3, 2021");
		
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(dateSelected));
		Assert.assertTrue(driver.findElement(dateSelected).isDisplayed());
	}
	
	@Test
	public void TC_06_Explicit_Wait() {
		driver.get("https://gofile.io/?t=uploadFiles");
		String parentId = driver.getWindowHandle();
		explicitWait = new WebDriverWait(driver, 15);
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#uploadFile-Input[type='file']")));
		driver.findElement(By.cssSelector("#uploadFile-Input[type='file']")).sendKeys(imageAlexFergusonFilePath);
		
		//Verify download link and message displayed
		explicitWait = new WebDriverWait(driver, 15);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(successUploadMessage));
		Assert.assertEquals(driver.findElement(successUploadMessage).getText(),"Your files have been successfully uploaded");
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".card #rowUploadSuccess-downloadPage")));
		clickElement(By.cssSelector(".card #rowUploadSuccess-downloadPage"));
		
		//Switch to window
		switchToWindowById(parentId);
		
		//Assert: verify image name is displayed
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='contentName' and text()='" + imageAlexFerguson + "']")));
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='" + imageAlexFerguson + "']")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
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