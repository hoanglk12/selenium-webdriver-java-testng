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

public class Topic_17_Wait_Part_I_Element_Status {

	String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Actions action;
	WebDriverWait explicitWait;
	String autoITPath = projectPath + "\\autoIT\\authen_firefox.exe";
	By confirmEmailTextbox = By.xpath("//input[@name='reg_email_confirmation__']");
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		jsExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 15);
		action = new Actions(driver);
		driver.get("https://www.facebook.com/");
	}

	//@Test
	public void TC_01_Visible() {
		//Visible: must display in UI and exist in DOM/HTML
		
		driver.findElement(By.xpath("//a[text()='Tạo tài khoản mới']")).click();
		driver.findElement(By.name("reg_email__")).sendKeys("hp95@gmail.com");
		
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(confirmEmailTextbox));
		driver.findElement(confirmEmailTextbox).sendKeys("hp95@gmail.com");
	}

	@Test
	public void TC_02_Invisible_01() {
		driver.findElement(By.xpath("//a[text()='Tạo tài khoản mới']")).click();
		
		//Case1: NOT display in UI, exist in DOM/HTML
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(confirmEmailTextbox));
	}
	
	@Test
	public void TC_02_Invisible_02() {
		//driver.navigate().refresh();
		driver.findElement(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")).click();
		
		//Case2: NOT display in UI, NOT exist in DOM/HTML
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(confirmEmailTextbox));
	}

	//@Test
	public void TC_03_Presense() {
		//Must exist in DOM/HTML, no care display in UI or NOT
		
		driver.findElement(By.xpath("//a[text()='Tạo tài khoản mới']")).click();
		
		//Wait presence (exist in DOM/HTML - NOT display in UI)
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(confirmEmailTextbox));
		
		driver.findElement(By.name("reg_email__")).sendKeys("hp95@gmail.com");
		
		//Wait presence (exist in DOM/HTML - display in UI)
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(confirmEmailTextbox));
	}

	//@Test
	public void TC_04_Staleness() {
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		driver.findElement(By.id("SubmitCreate")).click();
		
		//1
		WebElement accountError = driver.findElement(By.xpath("//div[@id='create_account_error']"));
		
		//2. Element is to updated (no longer attached to DOM)
		driver.navigate().refresh();
		
		//3. Wait for emailTextbox is staleness (wait for element not in old state)
		explicitWait.until(ExpectedConditions.stalenessOf(accountError));
		
		//StaleElementException: element đã bị thay đổi trạng thái nhưng vẫn lấy ra để thao tác
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