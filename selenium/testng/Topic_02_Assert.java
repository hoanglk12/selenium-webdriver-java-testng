package testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Topic_02_Assert {
	
	Object studentPrice = null; //bien global co gia tri mac dinh
	@Test
	public void TC_01() {
		String studentName = "John Cena";//bien local bat buoc khoi tao moi dung duoc
		
		Assert.assertTrue(studentName.contains("John"));
		Assert.assertFalse(studentName.contains("Rick"));
		Assert.assertEquals(studentName,"John Cena");
		Assert.assertNotEquals(studentName,"John Wick");
		Assert.assertNull(studentPrice);
		
		studentPrice = 900;
		Assert.assertNotNull(studentPrice);
	}
	

}
