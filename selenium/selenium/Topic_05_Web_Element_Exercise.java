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

public class Topic_05_Web_Element_Exercise {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String firstName = "Osama";
	String lastName = "Binladen";
	String fullName = firstName + " " + lastName;
	String emailAddress = "osamabin" + generateEmail();
	String password = "123456";
	String confirmPassword = "123456";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver",projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_Create_New_Account() {
		driver.get("http://live.demoguru99.com/");

		// Click My Account at footer
		driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();

		// Click Create An Account button
		driver.findElement(By.cssSelector(".col2-set a.button[title='Create an Account']")).click();

		// Input mandatory fields
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(confirmPassword);
		driver.findElement(By.xpath("//button[@title='Register']")).click();

		// Asserts message
		Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(),"Thank you for registering with Main Website Store.");

		// Assert fullName and email with 2 ways
		// isDisplayed
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div[contains(.,'" + fullName+ "')]/p")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div[contains(.,'"+ emailAddress + "')]/p")).isDisplayed());
		
		// getText and Assert with method contains
		String contactInformation = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div[contains(.,'" + fullName + "')]/p")).getText();
		Assert.assertTrue(contactInformation.contains(fullName));
		Assert.assertTrue(contactInformation.contains(emailAddress));

		// Logout
		driver.findElement(By.cssSelector(".skip-link.skip-account")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();
	}

	@Test
	public void TC_02_Login() {
		// Click My Account at footer
		driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
		
		driver.findElement(By.id("email")).sendKeys(emailAddress);
		driver.findElement(By.id("pass")).sendKeys(password);
		driver.findElement(By.name("send")).click();
		
		// isDisplayed
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div[contains(.,'" + fullName + "')]/p")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div[contains(.,'" + emailAddress + "')]/p")).isDisplayed());
		

		// getText and Assert with method contains
		String contactInformation = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div[contains(.,'" + fullName + "')]/p")).getText();
		Assert.assertTrue(contactInformation.contains(fullName));
		Assert.assertTrue(contactInformation.contains(emailAddress));

	}

	@Test
	public void TC_03_Displayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		By emailTextBox = By.id("mail");
		By educationTextArea = By.id("edu");
		By under18RadioButton = By.id("under_18");
		
		if (isElementDisplayed(emailTextBox)) {
			sendKeysToElement(emailTextBox,"Automation FC");
		}
		if (isElementDisplayed(educationTextArea)) {
			sendKeysToElement(educationTextArea,"Automation FC");
		}
		if (isElementDisplayed(under18RadioButton)) {
			clickElement(under18RadioButton);
		}
		
	}

	@Test
	public void TC_04_Enabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		By emailTextBox = By.id("mail");
		By educationTextArea = By.id("edu");
		By under18RadioButton = By.id("under_18");
		By jobRole1 = By.id("job1");
		By jobRole2 = By.id("job2");
		By disabledJobRole3 = By.id("job3");
		By developmentCheckbox = By.id("development");
		By slider01 = By.id("slider-1");
		By disabledAgeRadioButton = By.id("radio-disabled");
		By disabledTnterestCheckbox = By.id("check-disbaled");
		By disabledPasswordTextbox = By.id("password");
		By disabledBiographyTextarea = By.id("bio");
		By disabledSlider02 = By.id("slider-2");
		
		//Verify these element enabled
		Assert.assertTrue(isElementEnabled(emailTextBox));
		Assert.assertTrue(isElementEnabled(under18RadioButton));
		Assert.assertTrue(isElementEnabled(educationTextArea));
		Assert.assertTrue(isElementEnabled(jobRole1));
		Assert.assertTrue(isElementEnabled(jobRole2));
		Assert.assertTrue(isElementEnabled(developmentCheckbox));
		Assert.assertTrue(isElementEnabled(slider01));
		
		//Verify these element disabled
		Assert.assertFalse(isElementEnabled(disabledAgeRadioButton));
		Assert.assertFalse(isElementEnabled(disabledTnterestCheckbox));
		Assert.assertFalse(isElementEnabled(disabledPasswordTextbox));
		Assert.assertFalse(isElementEnabled(disabledBiographyTextarea));
		Assert.assertFalse(isElementEnabled(disabledSlider02));
		Assert.assertFalse(isElementEnabled(disabledJobRole3));
		
	}
	
	@Test
	public void TC_05_Selected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		By under18RadioButton = By.id("under_18");
		By javaLanguageCheckbox = By.id("java");
		
		clickElement(under18RadioButton);
		clickElement(javaLanguageCheckbox);
		sleepInSecond(2);
		
		//Verify ageUnder18 and javaLanguage are selected
		Assert.assertTrue(isElementSelected(under18RadioButton));
		Assert.assertTrue(isElementSelected(javaLanguageCheckbox));
		
		clickElement(javaLanguageCheckbox);
		sleepInSecond(2);
		
		//Verify javaLanguague is de-selected
		Assert.assertFalse(isElementSelected(javaLanguageCheckbox));
		
	}
	
	@Test
	public void TC_06_Register_Validate() {
		driver.get("https://login.mailchimp.com/signup/");
		By uppercaseCompleted = By.cssSelector(".uppercase-char.completed");
		By lowercaseCompleted = By.cssSelector(".lowercase-char.completed");
		By numberCompleted = By.cssSelector(".number-char.completed");
		By specialcharCompleted = By.cssSelector(".special-char.completed");
		By greaterthan8CharCompleted = By.cssSelector("li[class='8-char completed']");
		By signupButton = By.id("create-account");
		By passwordTextbox = By.id("new_password");
		By marketingNewsletterCheckbox = By.id("marketing_newsletter");
		
		
		driver.findElement(By.id("email")).sendKeys("automation@hotmail.com");
		driver.findElement(By.id("new_username")).sendKeys("automated");
		
		//Uppercase
		sendKeysToElement(passwordTextbox, "AUTOMATION");
		Assert.assertTrue(isElementDisplayed(uppercaseCompleted));
		Assert.assertFalse(isElementEnabled(signupButton));
		
		//Lowercase
		sendKeysToElement(passwordTextbox, "automation");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(lowercaseCompleted));
		Assert.assertFalse(isElementEnabled(signupButton));
		
		//Number
		sendKeysToElement(passwordTextbox, "123456");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(numberCompleted));
		Assert.assertFalse(isElementEnabled(signupButton));
		
		//Special char
		sendKeysToElement(passwordTextbox, "!@$%^");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(specialcharCompleted));
		Assert.assertFalse(isElementEnabled(signupButton));
		
		// >=8
		sendKeysToElement(passwordTextbox, "automation");
		sleepInSecond(2);
		Assert.assertTrue(isElementDisplayed(greaterthan8CharCompleted));
		Assert.assertTrue(isElementDisplayed(lowercaseCompleted));
		Assert.assertFalse(isElementEnabled(signupButton));
		
		//Combine all conditions
		sendKeysToElement(passwordTextbox, "Autom@tionFC1");
		sleepInSecond(2);
		
		Assert.assertFalse(isElementDisplayed(uppercaseCompleted));
		Assert.assertFalse(isElementDisplayed(lowercaseCompleted));
		Assert.assertFalse(isElementDisplayed(numberCompleted));
		Assert.assertFalse(isElementDisplayed(specialcharCompleted));
		Assert.assertFalse(isElementDisplayed(greaterthan8CharCompleted));
		Assert.assertTrue(isElementEnabled(signupButton));
		
		WebElement element = driver.findElement(marketingNewsletterCheckbox);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		sleepInSecond(1); 
		clickElement(marketingNewsletterCheckbox);
		Assert.assertTrue(isElementSelected(marketingNewsletterCheckbox));
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
