package testng;

import org.testng.Assert;

import org.testng.annotations.Test;


public class Topic_07_DependOnTest {
	@Test
	public void Account_01_Create_New_Account() {
		
	}
	
	@Test(dependsOnMethods = "Account_01_Create_New_Account")
	public void Account_02_View_Account() {
		
	}
	@Test(dependsOnMethods = "Account_02_View_Account")
	public void Account_03_Edit_Account() {
		Assert.assertTrue(false);
		
	}
	@Test(dependsOnMethods = "Account_03_Edit_Account")
	public void Account_04_Move_Account() {
		
	}
	@Test(dependsOnMethods = "Account_04_Move_Account")
	public void Account_05_Delete_Account() {
		
	}
	
}
