package com.qa.base;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		regPage = loginPage.goToRegisterPage();
	}

//	@DataProvider
//	public Object[][] getRegData() {
//		return new Object[][] { { "Amaan", "Hashmi", "thehe@gtmail.com", "9867821158", "Amaan@19987", "yes" },
//				{ "Abbas", "Dabir", "Abbas@gtmail.com", "9867821151", "Abbas@19987", "yes" },
//				{ "Neel", "Haria", "Neel@gtmail.com", "9867821136", "Neel@19987", "yes" }, };
//
//	}

	public String randomEmail() {
		Random random = new Random();
		String email = "automation" + random.nextInt(1000) + "@gmail.com";
		return email;
	}

	@DataProvider
	public Object[][] getExcelData() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}

	@Test(dataProvider = "getExcelData")
	public void userRegTest(String firstName, String lastName,  String telephone, String password,
			String subscribe) {
		boolean successFlag = regPage.userRegistration(firstName, lastName, randomEmail(), telephone, password, subscribe);
		regPage.goToRegisterPage();
		Assert.assertEquals(successFlag, true);
	}

}
