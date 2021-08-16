package selenium;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
	String[] expectedItemSelected = {"January","February","December"};
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
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
	
	@Test
	public void TC_04_Angular() {
		driver.get("https://valor-software.com/ng2-select/");
		selectElementInCustomDropdown("//tab[@heading='Single']//div[contains(@class,'ui-select-container')]","//ul[@role='menu']//li", "Barcelona");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//h3[text()='Select a single city']/following-sibling::ng-select//div[@class='ui-select-match']/span/span")).getText(),"Barcelona");
	
	}
	
	@Test
	public void TC_05_Edittable_Dropdown_Angular() {
		driver.get("https://valor-software.com/ng2-select/");
		enterAndSelectElementInCustomDropdown("//tab[@heading='Single']//div[contains(@class,'ui-select-container')]","//tab[@heading='Single']//input","//ul[@role='menu']//li/div/a/div","Sheffield");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//h3[text()='Select a single city']/following-sibling::ng-select//div[@class='ui-select-match']/span/span")).getText(),"Sheffield");
	}
	
	@Test
	public void TC_05_Edittable_Dropdown_ReactJs() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		enterAndTabOnElementInCustomDropdown("//div[@role='combobox']/input", "Austria");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@role='combobox']/div[@role='alert' and contains(text(),'Austria')]")).isDisplayed());
		
		enterAndTabOnElementInCustomDropdown("//div[@role='combobox']/input", "Belgium");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@role='combobox']/div[@role='alert' and contains(text(),'Belgium')]")).isDisplayed());
	}
	@Test
	public void TC_06_Multiple_Select() {
		driver.get("https://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
		selectMultiElementInCustomDropdown("(//label[contains(text(),'Multiple Select')])[last()-1]/following-sibling::div","(//div[@class='ms-drop bottom'])[last()-1]//span", expectedItemSelected);
		areItemsSelected(expectedItemSelected);
	}
	
	@AfterClass
	public void afterClass() {
		//driver.quit();
	}

	public void selectElementInCustomDropdown(String parentLocator, String childLocator, String expectedItem) {
		// 1.Click on 1 element for the list items displayed -> parent
		clickElement(By.xpath(parentLocator));
		sleepInSecond(3);
		// 2. Wait for all item loaded successfully -> child
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childLocator)));
		// 3. Find target item
		// 3.1 item display > click
		// 3.2 item not display (hidden) > scroll > click
		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(2);
				item.click();
				break;
			}

		}

	}
	
	public void enterAndSelectElementInCustomDropdown(String parentLocator, String textboxLocator, String childLocator, String expectedItem) {
		
		clickElement(By.xpath(parentLocator));
		sleepInSecond(2);
		sendKeysToElement(By.xpath(textboxLocator), expectedItem);
		sleepInSecond(1);
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childLocator)));
	
		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(2);
				item.click();
				break;
			}

		}

	}
	
	public void enterAndTabOnElementInCustomDropdown(String Locator, String expectedItem) {
		sendKeysToElement(By.xpath(Locator), expectedItem);
		sleepInSecond(1);
		driver.findElement(By.xpath(Locator)).sendKeys(Keys.TAB);
	}
	
	public void selectMultiElementInCustomDropdown(String parentLocator, String childLocator, String[] expectedValueItem) {

		clickElement(By.xpath(parentLocator));
		sleepInSecond(1);

		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childLocator)));

		for (WebElement item : allItems) {
			for (String valueItem : expectedValueItem) {
				if (item.getText().trim().equals(valueItem)) {
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
					sleepInSecond(1);
					item.click();
					List <WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
					if (itemSelected.size() == expectedValueItem.length) {
						break;
					}
				}
			}
		}
	}
	
	public boolean areItemsSelected(String[] months) {
		List<WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
		String allItemsTextSelected = driver.findElement(By.xpath("(//button[@class='ms-choice']/span)[last()-1]"))
				.getText();
		int numberItemSelected = itemSelected.size();
		System.out.println("items selected: " + numberItemSelected);
		if (numberItemSelected > 0 && numberItemSelected <= 3) {
			for (WebElement item : itemSelected) {
				boolean found = true;
				if (!allItemsTextSelected.contains(item.getText())) {
					found = false;
					return found;
				}
				return found;
			}
		} else if (numberItemSelected > 3 && numberItemSelected < 12) {
			return driver
					.findElement(By.xpath(
							"//button[@class='ms-choice']/span[text()='" + numberItemSelected + "' of 12 selected']"))
					.isDisplayed();
		} else if (numberItemSelected >= 12) {
			return driver.findElement(By.xpath("//button[@class='ms-choice']/span[text()='All selected']"))
					.isDisplayed();
		}
		return false;

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
