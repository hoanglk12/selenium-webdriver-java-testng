package selenium;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class Topic_17_Wait_Part_VI_Fluent_Wait {

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
		driver.manage().window().maximize();
		jsExecutor = (JavascriptExecutor) driver;
		action = new Actions(driver);
		explicitWait = new WebDriverWait(driver, 15);
	}

	@Test
	public void TC_01_Fluent_Wait() {
		driver.get("https://automationfc.github.io/fluent-wait/");
		Assert.assertTrue(waitForElementAndDisplayed(By.cssSelector("#javascript_countdown_time")));
	}

	@Test
	public void TC_02_Fluent_Wait() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		waitForElementAndClick(By.cssSelector("#start>button"));
		Assert.assertTrue(waitForElementAndDisplayed(By.xpath("//div[@id='finish']/h4[text()='Hello World!']")));
	}
	
	@Test
	public void TC_03_Ajax_Loading() {
		driver.get("https://opensource-demo.orangehrmlive.com");
		sendKeysToElement(By.id("txtUsername"), "Admin");
		sendKeysToElement(By.id("txtPassword"), "admin123");
		clickElement(By.id("btnLogin"));
		Assert.assertTrue(isJQueryLoadedSuccess(driver));
		
		//3 months isDisplayed
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='total']//span[text()='3 month(s)']")).isDisplayed());
		
		//Click PIM
		clickElement(By.cssSelector("#menu_pim_viewPimModule"));
		
		//Search Employee
		sendKeysToElement(By.id("empsearch_employee_name_empName"), "Peter Mac");
		clickElement(By.cssSelector("input#searchBtn"));
		
		//Employee Name is displayed
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='First (& Middle) Name']/ancestor::thead/following-sibling::tbody//a[text()='Peter Mac']")).isDisplayed());
	
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public boolean isJQueryLoadedSuccess(WebDriver driver) {
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
		};
		return explicitWait.until(jQueryLoad);
	}

	public boolean isJQueryAndAjaxIconLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, 15);
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};
		ExpectedCondition<Boolean> ajaxIconLoading = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return $('.raDiv').is('visible')").toString().equals("false");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(ajaxIconLoading);
	}

	public boolean isJQueryAndPageLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, 15);
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}
	
	public WebElement getWebElement(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(15))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
		return element;
	}
	
	public void waitForElementAndClick(By locator) {
		/*
		 * FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		 * .withTimeout(Duration.ofSeconds(15)) .pollingEvery(Duration.ofSeconds(1))
		 * .ignoring(NoSuchElementException.class); WebElement element = wait.until(new
		 * Function<WebDriver, WebElement>() { public WebElement apply(WebDriver driver)
		 * { return driver.findElement(locator); } });
		 */
		getWebElement(locator).click();
	}
	
	public boolean waitForElementAndDisplayed(By locator) {
		WebElement element = getWebElement(locator);
		FluentWait<WebElement> wait = new FluentWait<WebElement>(element)
				.withTimeout(Duration.ofSeconds(15))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
		boolean isDisplayed = wait.until(new Function<WebElement, Boolean>() {
			public Boolean apply(WebElement element) {
				boolean flag = element.isDisplayed();
				return flag;
			}
		});
		return isDisplayed;
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