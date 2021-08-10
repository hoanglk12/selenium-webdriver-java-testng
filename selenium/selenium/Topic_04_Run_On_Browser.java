package selenium;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class Topic_04_Run_On_Browser {
	// Variable driver for Selenium WebDriver
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	@Test
	public void TC_01_Run_On_Firefox() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		// Open AUT/SUT
		driver.get("http://live.demoguru99.com/index.php");

		// Close Browser
		driver.quit();
	}

	@Test
	public void TC_02_Run_On_Chrome() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		
		// Open AUT/SUT
		driver.get("http://live.demoguru99.com/index.php");

		// Close Browser
		driver.quit();
	}

	@Test
	public void TC_03_Run_On_Edge_Chromium() {
		// Open AUT/SUT
		System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
		driver = new EdgeDriver();
		
		// Open AUT/SUT
		driver.get("http://live.demoguru99.com/index.php");

		// Close Browser
		driver.quit();
	}

	public void sleepInSecond(long timeOutSecond) {
		try {
			Thread.sleep(timeOutSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
