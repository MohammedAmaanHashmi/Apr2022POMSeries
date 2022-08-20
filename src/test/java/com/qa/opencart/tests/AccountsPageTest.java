package com.qa.opencart.tests;

import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic("Epic-200:Design the Accounts  page feature for open cart application")
@Story("US-201: design Accounts page features with login, forgot pwd, title, url ")

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void isLogoutlinkExistTest() {
		Assert.assertEquals(accPage.isLoginLinkExist(), true);
	}

	@Test
	public void accPageTitleTest() {
		Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}

	@Test
	public void accPageHeadersTest() {
		List<String> actSectionHeaderList = accPage.getAccountSectionHeaderList();

		Collections.sort(actSectionHeaderList);

		Collections.sort(AppConstants.EXPECTED_ACCOUNTS_HEADERS_LIST);

		System.out.println("=======actual headers======" + actSectionHeaderList);
		Assert.assertEquals(actSectionHeaderList, AppConstants.EXPECTED_ACCOUNTS_HEADERS_LIST);

	}

}
