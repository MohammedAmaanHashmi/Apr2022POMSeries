package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic-100:Design the login page feature for open cart application")
@Story("US-101: design loginn page features with login, forgot pwd, title, url ")
public class LoginPageTest extends BaseTest {

	@Test
	@Description("Tc_01: To verify login page title")
	@Severity(SeverityLevel.NORMAL)
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Test
	@Description("Tc_02: To verify login page url test")
	@Severity(SeverityLevel.NORMAL)
	public void loginPageUrlTest() {
		String actUrl = loginPage.getLoginPageURL();
		Assert.assertEquals(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION), true);
	}

	@Test
	@Description("Tc_02: To verify forgot pwd link exist on the login page")
	@Severity(SeverityLevel.CRITICAL)
	public void forgotPwdLinkExistTest() {
		Assert.assertEquals(loginPage.isForgotPwdLinkExist(), true);
	}

	@Test
	@Description("Tc_02: To verify forgot pwd link exist on the login page")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}

}
