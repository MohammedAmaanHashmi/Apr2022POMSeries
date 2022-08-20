package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. by locators:

	private By logoutLink = By.linkText("Logout");
	private By searchFiled = By.name("search");
	private By accPageHeaders = By.cssSelector("div#content h2");
	private By searchIcon = By.cssSelector("div#search button");

	// 2. const..
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	// 3.Page Actions
	public String getAccountsPageTitle() {
		String title = eleUtil.waitForTitleToBe(AppConstants.ACCOUNTS_PAGE_TITLE, AppConstants.SMALL_DEFAULT_TIME_OUT);
		System.out.println("Acc page title: " + title);
		return title;
	}

	public String getAccountsPageURL() {
		String URL = eleUtil.waitForUrl(AppConstants.SMALL_DEFAULT_TIME_OUT, AppConstants.LOGIN_PAGE_URL_FRACTION);
		System.out.println("Acc page URL: " + URL);
		return URL;
	}

	public boolean isLoginLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, AppConstants.MEDIUM_DEFAULT_TIME_OUT).isDisplayed();
	}

	public boolean isSearchLinkExist() {
		return eleUtil.waitForElementVisible(searchFiled, AppConstants.MEDIUM_DEFAULT_TIME_OUT).isDisplayed();
	}

	public List<String> getAccountSectionHeaderList() {
		return eleUtil.getAllTextList(accPageHeaders, AppConstants.SMALL_DEFAULT_TIME_OUT);
	}

	// common page actions

	public SearchResultsPage doSearch(String productName) {
		System.out.println("searching for: " + productName);
		eleUtil.doSendKeysWithWait(searchFiled, 10, productName);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}

}
