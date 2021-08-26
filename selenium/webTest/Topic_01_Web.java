package webTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_01_Web {
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		System.out.println("Open Browser");
	}
	@Test(groups = {"web","regression"})
	public void TC_01_Add_New_Language() {
		System.out.println("Run TC01");
	}
	@Test(groups = {"web","regression"})
	public void TC_02_Change_Language() {
		System.out.println("Run TC02");
	}
	@Test(groups = {"web","regression"})
	public void TC_03_Move_Language() {
		System.out.println("Run TC03");
	}
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		System.out.println("Close Browser");
	}

}
