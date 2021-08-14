package selenium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Default_Dropdown {
	WebDriver driver;
	Select selectDay;
	Select selectMonth;
	Select selectYear;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String firstName, lastName, day, month, year, email, companyName, password, confirmPassword;
	List<String> expectedDays, expectedMonths, expectedYears;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		jsExecutor = (JavascriptExecutor) driver;
		driver.get("https://demo.nopcommerce.com/");

		// Data test for Register Page
		firstName = "John";
		lastName = "Cena";
		day = "23";
		month = "April";
		year = "1977";
		email = "johncena" + generateEmail();
		companyName = "Wayne Enterprise";
		password = "123456";
		confirmPassword = "123456";

		expectedDays = new ArrayList<String>(Arrays.asList("Day", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31"));
		expectedMonths = new ArrayList<String>(Arrays.asList("Month", "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November", "December"));
		expectedYears = new ArrayList<String>(Arrays.asList("Year", "1911", "1912", "1913", "1914", "1915", "1916",
				"1917", "1918", "1919", "1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929",
				"1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942",
				"1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955",
				"1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968",
				"1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981",
				"1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994",
				"1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007",
				"2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020",
				"2021"));
	}

	@Test
	public void TC_01_NopCommerce_Register() {
		// Click Register link
		clickElement(By.cssSelector(".ico-register"));
		sleepInSecond(2);

		// Input all mandatory fields in Register page
		sendKeysToElement(By.id("FirstName"), firstName);
		sendKeysToElement(By.id("LastName"), lastName);
		selectDay = new Select(driver.findElement(By.name("DateOfBirthDay")));
		selectDay.selectByVisibleText(day);
		selectMonth = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		selectMonth.selectByVisibleText(month);
		selectYear = new Select(driver.findElement(By.name("DateOfBirthYear")));
		selectYear.selectByVisibleText(year);
		sendKeysToElement(By.id("Email"), email);
		sendKeysToElement(By.id("Company"), companyName);
		sendKeysToElement(By.id("Password"), password);
		sendKeysToElement(By.id("ConfirmPassword"), confirmPassword);

		// Assert values in Day, Month and Year dropdown
		List<String> actualDays = selectDay.getOptions().stream().map(itemDay -> itemDay.getText())
				.collect(Collectors.toList());
		List<String> actualMonths = selectMonth.getOptions().stream().map(itemMonth -> itemMonth.getText())
				.collect(Collectors.toList());
		List<String> actualYears = selectYear.getOptions().stream().map(itemYear -> itemYear.getText())
				.collect(Collectors.toList());
		Assert.assertEquals(actualDays, expectedDays);
		Assert.assertEquals(actualMonths, expectedMonths);
		Assert.assertEquals(actualYears, expectedYears);

		// Click Register
		clickByJs(By.id("register-button"));
		sleepInSecond(2);

		// Assert
		Assert.assertEquals(driver.findElement(By.cssSelector(".page-body>.result")).getText(),
				"Your registration completed");
	}

	@Test
	public void TC_02_NopCommerce_Verify_MyAccount() {
		clickElement(By.cssSelector(".ico-account"));
		sleepInSecond(2);

		// Assert information in My Account match with Register Page
		Assert.assertEquals(driver.findElement(By.id("FirstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.id("LastName")).getAttribute("value"), lastName);
		selectDay = new Select(driver.findElement(By.name("DateOfBirthDay")));
		selectMonth = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		selectYear = new Select(driver.findElement(By.name("DateOfBirthYear")));
		Assert.assertEquals(selectDay.getFirstSelectedOption().getText(), day);
		Assert.assertEquals(selectMonth.getFirstSelectedOption().getText(), month);
		Assert.assertEquals(selectYear.getFirstSelectedOption().getText(), year);
		Assert.assertEquals(driver.findElement(By.id("Email")).getAttribute("value"), email);
		Assert.assertEquals(driver.findElement(By.id("Company")).getAttribute("value"), companyName);
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
