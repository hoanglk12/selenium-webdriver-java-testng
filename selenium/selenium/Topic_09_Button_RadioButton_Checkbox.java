package selenium;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Button_RadioButton_Checkbox {

	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		jsExecutor = (JavascriptExecutor) driver;

	}

	@Test
	public void TC_01_Button() {
		By loginButton = By.cssSelector(".fhs-btn-login");
		
		driver.get("https://www.fahasa.com/customer/account/create");
		sleepInSecond(1);
		clickElement(By.xpath("//ul[@id='popup-login-tab_list']//a[text()='Đăng nhập']"));
		
		
		//Assert (verify login button is disabled)
		Assert.assertFalse(driver.findElement(loginButton).isEnabled());
		
		sendKeysToElement(By.id("login_username"), "0936777444");
		sendKeysToElement(By.id("login_password"), "123456");
		sleepInSecond(2);
		
		//Assert (verify login button is enabled and red background)
		Assert.assertTrue(driver.findElement(loginButton).isEnabled());
		Assert.assertEquals(driver.findElement(loginButton).getCssValue("background-color"), "rgb(201, 33, 39)");
		
		//Refresh
		driver.navigate().refresh();
		sleepInSecond(1);
		
		//Remove attribute disabled and clickByJs
		removeAttributeByJs(loginButton);
		clickByJs(loginButton);
		sleepInSecond(1);
		
		//Assert: verify error msg
		clickElement(By.xpath("//ul[@id='popup-login-tab_list']//a[text()='Đăng nhập']"));
		sleepInSecond(1);
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='login_username']/parent::div/following-sibling::div")).getText(), "Thông tin này không thể để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='login_password']/parent::div/following-sibling::div")).getText(), "Thông tin này không thể để trống");
		
	}

	@Test
	public void TC_02_DefaultCheckbox_RadioButton() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");
		sleepInSecond(1);
		
		//Scroll Down
		jsExecutor.executeScript("window.scrollBy(0,350)");
		
		//Check on the checkbox
		checkToCheckboxOrRadioButton(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));
		//Assert checkbox is selected
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).isSelected());
		//Uncheck on the checkbox
		uncheckToCheckbox(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));
		//Assert checkbox is de-selected
		Assert.assertFalse(driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).isSelected());
		
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
		checkToCheckboxOrRadioButton(By.xpath("//label[text()='1.4 Petrol, 92kW']/preceding-sibling::input"));
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='1.4 Petrol, 92kW']/preceding-sibling::input")).isSelected());
		checkToCheckboxOrRadioButton(By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input"));
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input")).isSelected());
	}

	@Test
	public void TC_03_CustomCheckbox_RadioButton() {
		By summerRadioButton = By.xpath("//input[@value='Summer']");
		By checkedCheckboxFalse = By.xpath("//span[contains(text(),'Checked')]/preceding-sibling::span/input[@aria-checked='false']");
		By checkedCheckboxTrue = By.xpath("//span[contains(text(),'Checked')]/preceding-sibling::span/input[@aria-checked='true']");
		By indeterminateCheckboxFalse = By.xpath("//span[contains(text(),'Indeterminate')]/preceding-sibling::span/input[@aria-checked='false']");
		By indeterminateCheckboxTrue = By.xpath("//span[contains(text(),'Indeterminate')]/preceding-sibling::span/input[@aria-checked='true']");
		
		driver.get("https://material.angular.io/components/radio/examples");
		clickByJs(summerRadioButton);
		Assert.assertTrue(driver.findElement(summerRadioButton).isSelected());
		
		//Check on checkbox Checked and Intermediate
		driver.get("https://material.angular.io/components/checkbox/examples");
		clickByJs(checkedCheckboxFalse);
		Assert.assertTrue(driver.findElement(checkedCheckboxTrue).isDisplayed());
		clickByJs(indeterminateCheckboxFalse);
		Assert.assertTrue(driver.findElement(indeterminateCheckboxTrue).isDisplayed());
		
		//Uncheck on checkbox Checked and Intermediate
		clickByJs(checkedCheckboxTrue);
		Assert.assertTrue(driver.findElement(checkedCheckboxFalse).isDisplayed());
		clickByJs(indeterminateCheckboxTrue);
		Assert.assertTrue(driver.findElement(indeterminateCheckboxFalse).isDisplayed());
		
	}

	@Test
	public void TC_04_CustomCheckbox_RadioButton() {
		By canThoRadioFalse = By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='false']//div[contains(@class,'RadioButtonContainer')]");
		By canThoRadioTrue = By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='true']//div[contains(@class,'RadioButtonContainer')]");
		
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		sleepInSecond(3);
		
		//Assert: CanTho radio button not selected
		Assert.assertTrue(driver.findElement(canThoRadioFalse).isDisplayed());
		
		clickByJs(canThoRadioFalse);
		Assert.assertTrue(driver.findElement(canThoRadioTrue).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
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
