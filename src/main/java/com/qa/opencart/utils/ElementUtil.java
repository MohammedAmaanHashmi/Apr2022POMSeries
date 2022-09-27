package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.OptionsManager;

public class ElementUtil {

	private WebDriver driver;
	private Actions act;
	public static Logger log = Logger.getLogger(ElementUtil.class);

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
	}

	public By getBy(String locatorType, String selector) {

		By locator = null;

		switch (locatorType.toLowerCase()) {
		case "id":
			locator = By.id(selector);
			break;
		case "name":
			locator = By.name(selector);
			break;

		case "className":
			locator = By.className(selector);
			break;

		case "xpath":
			locator = By.xpath(selector);
			break;
		case "cssselector":
			locator = By.cssSelector(selector);
			break;
		case "linktext":
			locator = By.linkText(selector);
			break;
		case "partiallinktext":
			locator = By.partialLinkText(selector);
			break;

		default:
			break;
		}

		return locator;

	}

	public void doSendKeys(By locator, String value) {
		getElement(locator).sendKeys(value);
	}

	public WebElement getElement(By locator) {
		log.info("By locator is "+locator);
		return driver.findElement(locator);
	}

	public void doActionSendKeys(By locator, String value) {

		act.sendKeys(getElement(locator), value);
	}

	public void doActionsClick(By locator) {

		act.click(getElement(locator)).build().perform();
	}

	public void doSendKeys(String locatorType, String selector, String value) {
		By locator = getBy(locatorType, selector);
		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(String locatorType, String selector) {
		By locator = getBy(locatorType, selector);
		getElement(locator).click();
	}

	public String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public int getElementTextCount(By locator) {
		return getElementTextList(locator).size();
	}

	public int getEmptyTextList(By locator) {
		int totalLink = getPageElemntsCount(locator);
		int totalNonEmptyLinks = getElementTextCount(locator);
		return totalLink - totalNonEmptyLinks;
	}

	public List<String> getElementTextList(By locator) {
		List<WebElement> elelist = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();

		for (WebElement e : elelist) {
			String text = e.getText();

			if (!text.isEmpty()) {
				eleTextList.add(text);
			}
		}
		return eleTextList;

	}

	public int getPageElemntsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public List<String> getAttributeList(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrVarList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attrName);
			eleAttrVarList.add(attrValue);

		}
		return eleAttrVarList;
	}

	public void performSearch(By search, String searchKey, By suggListLocator, String suggName)
			throws InterruptedException {
		doSendKeys(search, searchKey);
		Thread.sleep(2000);

		List<WebElement> suggList = getElements(suggListLocator);

		System.out.println("total suggs: " + suggList.size());

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggName)) {
				e.click();
				break;
			}

		}
	}

	public void selectElement(String productName) {
		By locator = By.xpath("//li[text()='" + productName + "']");
		doClick(locator);
	}

	// *********************element displayed utils*************************//
	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public int getElementCount(By locator) {
		int eleCount = getElements(locator).size();
		return eleCount;
	}

	public boolean checkSingleElementExist(By locator) {
		if (getElementCount(locator) == 1) {
			return true;
		}
		return false;
	}

	public boolean checkElementMultipleExist(By locator) {
		if (getElementCount(locator) > 1) {
			return true;
		}
		return false;
	}

	// ***********************Select Tag based drop down
	// utils************************

	public void doSelectByIndex(By locator, int Index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(Index);
	}

	public void doSelectByVisbileText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public int getDropDownCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();

	}

	public List<String> getDropDownListValues(By locator) {
		Select select = new Select(getElement(locator));
		List<String> optionValList = new ArrayList<String>();
		List<WebElement> optionEleList = select.getOptions();

		for (WebElement e : optionEleList) {
			String text = e.getText();
			optionValList.add(text);

		}
		return optionValList;
	}

	public void doSelectValueUsingOptions(By locator, String Value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionList = select.getOptions();

		for (WebElement e : optionList) {
			String text = e.getText();
			System.out.println(text);

			if (text.equals(Value)) {
				e.click();
				break;
			}

		}
	}

	public void doSelectValuefromDropdown(By locator, String Value) {

		List<WebElement> optionList = getElements(locator);

		for (WebElement e : optionList) {
			String text = e.getText();
			System.out.println(text);

			if (text.equals(Value)) {
				e.click();
				break;
			}

		}
	}

//	*****************************wait util*********************//

	public Alert waitForAlet(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {
		return waitForAlet(timeOut).getText();
	}

	public void acceptAlert(int timeOut) {
		waitForAlet(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForAlet(timeOut).dismiss();
	}

	public void alertSendKeys(int timeOut, String value) {
		waitForAlet(timeOut).sendKeys(value);
	}

	public void doSendKeysWithWait(By locator, int timeOut, String value) {
		WebElement ele = waitForElementVisible(locator, timeOut);
		ele.clear();
		ele.sendKeys(value);
	}

	public String doElementGetText(By locator) {
		String eleText = getElement(locator).getText();
		return eleText;
	}

	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void doClickWithWait(By locator, int timeOut) {
		waitForElementVisible(locator, timeOut).click();
	}

	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public String waitForTitleContains(String titlefractionValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(titlefractionValue))) {

				return driver.getTitle();
			}
		} catch (Exception e) {
			System.out.println("Title is not found");
		}

		return null;
	}

	public String waitForTitleToBe(String titleValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(titleValue))) {

				return driver.getTitle();

			}
		} catch (Exception e) {
			System.out.println("Title is not found");

		}
		return null;
	}

	public String waitForUrl(int timeOut, String urlFractionValue) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {

			if (wait.until(ExpectedConditions.urlContains(urlFractionValue))) {

				return driver.getCurrentUrl();

			}

		} catch (Exception e) {

			System.out.println("url is not found....");

		}

		return null;

	}

	public String waitForUrlToBe(int timeOut, String urlValue) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {

			if (wait.until(ExpectedConditions.urlToBe(urlValue))) {

				return driver.getCurrentUrl();

			}

		} catch (Exception e) {

			System.out.println("url is not found....");

		}

		return null;

	}

	public void waitForFrameAndSwitchToIt(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFrameUsingIndexAndSwitchToIt(int timeOut, By frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameUsingNameOrIdAndSwitchToIt(int timeOut, By frameNameOrId) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
	}

	public void waitForFrameUsingElementOrIdAndSwitchToIt(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(getElement(frameLocator)));
	}

	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}

	public List<WebElement> waitForElementToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public List<WebElement> waitForElementToBeVisible(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut, pollingTime));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public void printAllElementsText(By locator, int timeOut) {
		List<WebElement> eleList = waitForElementToBeVisible(locator, timeOut);

		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
		}
	}

	public List<String> getAllTextList(By locator, int timeOut) {
		List<WebElement> eleList = waitForElementToBeVisible(locator, timeOut);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String text = e.getText();
			eleTextList.add(text);
		}

		return eleTextList;

	}

	public WebElement waitForElementVisibileWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
				.withMessage("element is not present on the page.....sorry..........");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	// ************************CustomWait**************************

	public void waitForPageLoad(int timeOut) {
		long endTime = System.currentTimeMillis() + timeOut;

		while (System.currentTimeMillis() < endTime) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String pageState = js.executeScript("return document.readyState").toString();

			System.out.println("page loading state: " + pageState);
			if (pageState.equals("complete")) {
				System.out.println("page is fullyloaded with all scripts, images, css...");
				break;
			}
		}
	}

	public WebElement retryingElement(By locator, int timeOut) {
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = getElement(locator);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found.....in attempt:" + (attempts + 1));

				try {
					Thread.sleep(500);// default time
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			attempts++;
		}

		if (element == null) {
			try {
				throw new Exception("ELEMENTISNOTFOUNDEXCEPTION");

			} catch (Exception e) {
				System.out.println(
						"element is not found...tried for" + timeOut + "secs" + "with the interval of: " + 500 + "ms");
			}
		}
		return element;
	}

	public WebElement retryingElement(By locator, int timeOut, int pollingTime) {
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = getElement(locator);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found.....in attempt:" + (attempts + 1));

				try {
					Thread.sleep(pollingTime);// interval time
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			attempts++;
		}

		if (element == null) {
			try {
				throw new Exception("ELEMENTISNOTFOUNDEXCEPTION");

			} catch (Exception e) {
				System.out.println(
						"element is not found...tried for" + timeOut + "secs" + "with the interval of: " + 500 + "ms");
			}
		}
		return element;
	}

	public static void shortWait() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void mediumWait() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void longWait() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void waitFor(int timeOut) {
		try {
			Thread.sleep(timeOut * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
