package testng;

import org.testng.annotations.Test;

public class Topic_03_Priority_Enabled {
	//Sort: alphabet
	
	@Test(priority = 1)
	public void Account_01_Create_New_Account() {
		
	}
	
	@Test(priority = 2, description = "Customer can view the newly created account")
	public void Account_02_View_Account() {
		
	}
	@Test(priority = 3)
	public void Account_03_Edit_Account() {
		
	}
	@Test(priority = 4)
	public void Account_04_Move_Account() {
		
	}
	@Test(enabled = false)
	public void Account_05_Delete_Account() {
		
	}
	

}
