package selenium;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

public class Topic_08_Custom_Dropdown {

	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		jsExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 15);
	}

	@Test
	public void TC_01_JQuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		selectElementInCustomDropdown("//span[@id='number-button']",
				"//ul[@id='number-menu']//li[@class='ui-menu-item']//div", "5");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//span[@id='number-button']//span[contains(text(),'5')]")));

		selectElementInCustomDropdown("//span[@id='number-button']",
				"//ul[@id='number-menu']//li[@class='ui-menu-item']//div", "19");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//span[@id='number-button']//span[contains(text(),'19')]")));

		selectElementInCustomDropdown("//span[@id='number-button']",
				"//ul[@id='number-menu']//li[@class='ui-menu-item']//div", "3");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//span[@id='number-button']//span[contains(text(),'3')]")));
	}

	@Test
	public void TC_02_ReactJs() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");

		selectElementInCustomDropdown("//div[@role='listbox']",
				"//div[@class='visible menu transition']//div[@role='option']", "Jenny Hess");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//div[@class='divider text' and text()='Jenny Hess']")));

		selectElementInCustomDropdown("//div[@role='listbox']",
				"//div[@class='visible menu transition']//div[@role='option']", "Elliot Fu");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//div[@class='divider text' and text()='Elliot Fu']")));

		selectElementInCustomDropdown("//div[@role='listbox']",
				"//div[@class='visible menu transition']//div[@role='option']", "Stevie Feliciano");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//div[@class='divider text' and text()='Stevie Feliciano']")));

		selectElementInCustomDropdown("//div[@role='listbox']",
				"//div[@class='visible menu transition']//div[@role='option']", "Christian");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//div[@class='divider text' and text()='Christian']")));

		selectElementInCustomDropdown("//div[@role='listbox']",
				"//div[@class='visible menu transition']//div[@role='option']", "Matt");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//div[@class='divider text' and text()='Matt']")));

		selectElementInCustomDropdown("//div[@role='listbox']",
				"//div[@class='visible menu transition']//div[@role='option']", "Justen Kitsune");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(By.xpath("//div[@class='divider text' and text()='Justen Kitsune']")));
	}

	@Test
	public void TC_03_VueJs() {
		// //ul[@class='dropdown-menu hide']/li/a
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectElementInCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']/li/a",
				"First Option");
		sleepInSecond(2);
		Assert.assertTrue(
				isElementDisplayed(By.xpath("//li[@class='dropdown-toggle' and contains(text(),'First Option')]")));

		selectElementInCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']/li/a",
				"Second Option");
		sleepInSecond(2);
		Assert.assertTrue(
				isElementDisplayed(By.xpath("//li[@class='dropdown-toggle' and contains(text(),'Second Option')]")));

		selectElementInCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']/li/a",
				"Third Option");
		sleepInSecond(2);
		Assert.assertTrue(
				isElementDisplayed(By.xpath("//li[@class='dropdown-toggle' and contains(text(),'Third Option')]")));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void selectElementInCustomDropdown(String parentLocator, String childLocator, String expectedItem) {
		// 1.Click on 1 element for the list items displayed -> parent
		clickElement(By.xpath(parentLocator));
		// 2. Wait for all item loaded successfully -> child
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childLocator)));
		// 3. Find target item
		// 3.1 item display > click
		// 3.2 item not display (hidden) > scroll > click
		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				if (!item.isDisplayed()) {
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", expectedItem);
					sleepInSecond(1);
				}
				item.click();
				break;
			}

		}

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
