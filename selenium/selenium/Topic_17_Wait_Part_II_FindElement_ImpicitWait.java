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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_II_FindElement_ImpicitWait {

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
	public void TC_01_Find_Element() {
		driver.get("https://www.facebook.com/");
		//0 matching node: wait for timeout (implicitlyWait)  -> mark fail test case, throw exception
		//retry find every 0.5s
		driver.findElement(By.name("reg_email_confirmation__"));
		
		//1 matching node:return element (no need to wait end of timeout)
		driver.findElement(By.xpath("//a[text()='Tạo tài khoản mới']")).click();
		driver.findElement(By.name("reg_email_confirmation__"));
		
		// > 1 matching node: return elements, interact with 1st element in matching nodes
		sleepInSecond(2);
		scrollToBottomPage();
		sleepInSecond(2);
		driver.findElement(By.cssSelector("#pageFooter a")).click();
		
	}

	@Test
	public void TC_01_Find_Elements() {
		driver.get("https://www.facebook.com/");
		// 0 matching node: wait for timeout (implicitlyWait) -> proceed next step, not mark fail TC and 
		//throw exception
		// retry find every 0.5s
		driver.findElements(By.name("reg_email_confirmation__"));
		

		// 1 matching node:return element (no need to wait end of timeout)
		driver.findElements(By.xpath("//a[text()='Tạo tài khoản mới']"));
		System.out.println(driver.findElements(By.xpath("//a[text()='Tạo tài khoản mới']")).size());
		

		// > 1 matching node: return elements
		sleepInSecond(2);
		scrollToBottomPage();
		sleepInSecond(2);
		System.out.println(driver.findElements(By.cssSelector("#pageFooter a")).size());
	
	}
	
	@Test
	public void TC_03_Implicit_Wait_Less_Than() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get("https://automationfc.github.io/dynamic-loading/");
		clickElement(startButton);
		
		Assert.assertEquals(driver.findElement(helloWorldText).getText(), "Hello World!");
	}

	@Test
	public void TC_04_Implicit_Wait_Equal() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://automationfc.github.io/dynamic-loading/");
		clickElement(startButton);
		
		Assert.assertEquals(driver.findElement(helloWorldText).getText(), "Hello World!");
	}

	@Test
	public void TC_05_Implicit_Wait_Greater_Than() {
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.get("https://automationfc.github.io/dynamic-loading/");
		clickElement(startButton);
		
		Assert.assertEquals(driver.findElement(helloWorldText).getText(), "Hello World!");
	}
	
	

	@AfterClass
	public void afterClass() {
		driver.quit();
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