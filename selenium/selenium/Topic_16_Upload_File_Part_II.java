package selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



public class Topic_16_Upload_File_Part_II {

	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Actions action;
	WebDriverWait explicitWait;
	String autoITPath = projectPath + File.separator + "autoIT" + File.separator + "authen_firefox.exe";
	String firefoxAutoITOnePerTimePath = projectPath + File.separator + "autoIT" + File.separator
			+ "firefoxUploadOnePerTime.exe";
	String firefoxAutoMultiPerTimePath = projectPath + File.separator + "autoIT" + File.separator
			+ "firefoxUploadMultiple.exe";
	String chromeAutoITOnePerTimePath = projectPath + File.separator + "autoIT" + File.separator
			+ "chromeUploadOneTime.exe";
	String chromeAutoMultiPerTimePath = projectPath + File.separator + "autoIT" + File.separator
			+ "chromeUploadMultiple.exe";
	Select select;
	String imageAlexFerguson = "alex-ferguson.jpg";
	String imageJohnWick = "John_Wick.jpg";
	String imageLucifer = "Lucifer.jpg";

	// Using File.separator (no need to care about \ or / )
	String uploadFilePath = projectPath + File.separator + "uploadFiles" + File.separator;
	String imageAlexFergusonFilePath = uploadFilePath + imageAlexFerguson;
	String imageJohnWickFilePath = uploadFilePath + imageJohnWick;
	String imageLuciferFilePath = uploadFilePath + imageLucifer;
	String[] imagePaths = {imageAlexFergusonFilePath, imageJohnWickFilePath, imageLuciferFilePath};
	@BeforeClass
	public void beforeClass() {
		//System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		//driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		 //System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
		 //driver = new EdgeDriver();
		// System.setProperty("webdriver.ie.driver", projectPath + "\\browserDrivers\\IEDriverServer.exe");
		// driver = new InternetExplorerDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		jsExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 25);
		action = new Actions(driver);
	}

	@Test
	public void TC_01_Upload_File_OnePerTime_AutoIT() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		sleepInSecond(3);
		
	
		if (driver.toString().contains("firefox")) {
			clickToElementByJS("//input[@type='file']");
			sleepInSecond(1);
			Runtime.getRuntime().exec(new String[] { firefoxAutoITOnePerTimePath, imageAlexFergusonFilePath});
		} else if (driver.toString().contains("chrome") || driver.toString().contains("edge")) {
			driver.findElement(By.cssSelector(".btn-success")).click();
			sleepInSecond(1);
			Runtime.getRuntime().exec(new String[] { chromeAutoITOnePerTimePath, imageAlexFergusonFilePath});
		} 
		sleepInSecond(2);
		
		
		if (driver.toString().contains("firefox")) {
			clickToElementByJS("//input[@type='file']");
			sleepInSecond(1);
			Runtime.getRuntime().exec(new String[] { firefoxAutoITOnePerTimePath, imageJohnWickFilePath });
		} else if (driver.toString().contains("chrome") || driver.toString().contains("edge")) {
			driver.findElement(By.cssSelector(".btn-success")).click();
			sleepInSecond(1);
			Runtime.getRuntime().exec(new String[] { chromeAutoITOnePerTimePath, imageJohnWickFilePath});
		} 
		sleepInSecond(2);
		
	
		if (driver.toString().contains("firefox")) {
			clickToElementByJS("//input[@type='file']");
			sleepInSecond(1);
			Runtime.getRuntime().exec(new String[] { firefoxAutoITOnePerTimePath, imageLuciferFilePath });
		} else if (driver.toString().contains("chrome") || driver.toString().contains("edge")) {
			driver.findElement(By.cssSelector(".btn-success")).click();
			sleepInSecond(1);
			Runtime.getRuntime().exec(new String[] { chromeAutoITOnePerTimePath, imageLuciferFilePath});
		} 
		sleepInSecond(2);
		

		// Verify files loaded successfully
		Assert.assertTrue(driver
				.findElement(
						By.xpath("//tbody[@class='files']//p[@class='name' and text()='" + imageAlexFerguson + "']"))
				.isDisplayed());
		Assert.assertTrue(driver
				.findElement(By.xpath("//tbody[@class='files']//p[@class='name' and text()='" + imageJohnWick + "']"))
				.isDisplayed());
		Assert.assertTrue(driver
				.findElement(By.xpath("//tbody[@class='files']//p[@class='name' and text()='" + imageLucifer + "']"))
				.isDisplayed());

		// Click to start upload
		List<WebElement> startButtons = driver.findElements(By.cssSelector("td>button[class*=start]"));
		for (WebElement startButton : startButtons) {
			startButton.click();
			sleepInSecond(1);
		}

		// Verify files uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='files']//a[text()='" + imageAlexFerguson + "']"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='files']//a[text()='" + imageJohnWick + "']"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='files']//a[text()='" + imageLucifer + "']"))
				.isDisplayed());
	}

	@Test
	public void TC_01_Upload_File_MultiplePerTime_AutoIT() throws IOException {
		// Won't work if > 256 characters for filePaths
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		sleepInSecond(3);
		
		if (driver.toString().contains("firefox")) {
			clickToElementByJS("//input[@type='file']");
			sleepInSecond(1);
			Runtime.getRuntime().exec(new String[] { firefoxAutoMultiPerTimePath, imageAlexFergusonFilePath,
					imageJohnWickFilePath, imageLuciferFilePath });
		} else if (driver.toString().contains("chrome") || driver.toString().contains("edge")) {
			driver.findElement(By.cssSelector(".btn-success")).click();
			sleepInSecond(1);
			Runtime.getRuntime().exec(new String[] { chromeAutoMultiPerTimePath, imageAlexFergusonFilePath,
					imageJohnWickFilePath, imageLuciferFilePath });
		} 
		
		// Verify files loaded successfully
		Assert.assertTrue(driver
				.findElement(
						By.xpath("//tbody[@class='files']//p[@class='name' and text()='" + imageAlexFerguson + "']"))
				.isDisplayed());
		Assert.assertTrue(driver
				.findElement(By.xpath("//tbody[@class='files']//p[@class='name' and text()='" + imageJohnWick + "']"))
				.isDisplayed());
		Assert.assertTrue(driver
				.findElement(By.xpath("//tbody[@class='files']//p[@class='name' and text()='" + imageLucifer + "']"))
				.isDisplayed());

		// Click to start upload
		List<WebElement> startButtons = driver.findElements(By.cssSelector("td>button[class*=start]"));
		for (WebElement startButton : startButtons) {
			startButton.click();
			sleepInSecond(1);
		}

		// Verify files uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='files']//a[text()='" + imageAlexFerguson + "']"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='files']//a[text()='" + imageJohnWick + "']"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='files']//a[text()='" + imageLucifer + "']"))
				.isDisplayed());
	}

	@Test
	public void TC_02_Upload_File_Robot() throws AWTException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		sleepInSecond(3);
	
		for (String imagePath : imagePaths) {
			if (driver.toString().contains("firefox")) {
				clickToElementByJS("//input[@type='file']");
				sleepInSecond(1);

			} else if (driver.toString().contains("chrome") || driver.toString().contains("edge")) {
				driver.findElement(By.cssSelector(".btn-success")).click();
				sleepInSecond(1);
			}
			// Specify the file location
			StringSelection select = new StringSelection(imagePath);

			// Copy to clipboard
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);

			Robot robot = new Robot();
			sleepInSecond(1);

			// Hit Enter
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			// Ctrl - V down
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			// Release Ctrl - V
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			sleepInSecond(1);

			// Hit Enter
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			sleepInSecond(2);
		}
		// Verify files loaded successfully
		Assert.assertTrue(driver
				.findElement(
						By.xpath("//tbody[@class='files']//p[@class='name' and text()='" + imageAlexFerguson + "']"))
				.isDisplayed());
		Assert.assertTrue(driver
				.findElement(By.xpath("//tbody[@class='files']//p[@class='name' and text()='" + imageJohnWick + "']"))
				.isDisplayed());
		Assert.assertTrue(driver
				.findElement(By.xpath("//tbody[@class='files']//p[@class='name' and text()='" + imageLucifer + "']"))
				.isDisplayed());
		
		// Click to start upload
		List<WebElement> startButtons = driver.findElements(By.cssSelector("td>button[class*=start]"));
		for (WebElement startButton : startButtons) {
			startButton.click();
			sleepInSecond(1);
		}
		
		// Verify files uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='files']//a[text()='" + imageAlexFerguson + "']"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='files']//a[text()='" + imageJohnWick + "']"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='files']//a[text()='" + imageLucifer + "']"))
				.isDisplayed());

	}

	@Test
	public void TC_03_Upload_File() {
		driver.get("https://gofile.io/uploadFiles");
		sleepInSecond(1);
		String parentId = driver.getWindowHandle();
		driver.findElement(By.cssSelector("#uploadFile-Input[type='file']")).sendKeys(imageAlexFergusonFilePath + "\n" + imageJohnWickFilePath + "\n" + imageLuciferFilePath);
		sleepInSecond(7);
		
		//Verify download link displayed
		Assert.assertTrue(driver.findElement(By.cssSelector(".card #rowUploadSuccess-downloadPage")).isDisplayed());
		clickElement(By.cssSelector(".card #rowUploadSuccess-downloadPage"));
		
		//Switch to window
		sleepInSecond(2);
		switchToWindowById(parentId);
		sleepInSecond(2);
		
		//Assert: verify image name is displayed
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
	
	public void switchToWindowByTitle(String expectedTitle) {
		Set<String> allWindowIds = driver.getWindowHandles();
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

	public void checkToCheckboxOrRadioButton(By by) {
		WebElement checkbox = driver.findElement(by);
		if (!checkbox.isSelected()) {
			checkbox.click();
		}
	}

	public void uncheckToCheckbox(By by) {
		WebElement checkbox = driver.findElement(by);
		if (checkbox.isSelected()) {
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