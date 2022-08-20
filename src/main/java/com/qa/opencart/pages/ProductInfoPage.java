package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1.By locator -OR
	private By productCount = By.cssSelector("div.product-thumb");
	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private Map<String, String> productMap;

	// 2. page const....
	public ProductInfoPage(WebDriver driver) {

		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeaderValue() {
		// By mainProduct = By.xpath("//h2[text()='" + mainProductName + "']");
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("produ Header " + productHeaderVal);
		return productHeaderVal;

	}

	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementToBeVisible(productImages, AppConstants.SMALL_DEFAULT_TIME_OUT).size();
		System.out.println("images count: " + imagesCount);
		return imagesCount;
	}

	public Map<String, String> getProductInfo() {
		 productMap = new HashMap<String, String>();
		//productMap = new LinkedHashMap<String, String>();
		//productMap = new TreeMap<String, String>();

		productMap.put("productName", getProductHeaderValue());
		getProductMetaData();
		getProductPriceData();

		System.out.println("======actual product info=====");

		productMap.forEach((k, v) -> System.out.println(k + ":" + v));
		return productMap;

	}

	private Map<String, String> getProductMetaData() {

		// product meta data:
//		Brand: Apple
//		Product Code: Product 17
//		Reward Points: 700
//		Availability: In Stock
		List<WebElement> metaDataList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaDataList) {
			String text = e.getText();
			String meta[] = text.split(":");

			String key = meta[0].trim();

			String value = meta[1].trim();

			productMap.put(key, value);

		}
		return productMap;

	}

	private Map<String, String> getProductPriceData() {
		// price data:
//		$1,202.00
//		Ex Tax: $1,000.00

		List<WebElement> metaPriceList = eleUtil.getElements(productPriceData);
		String productPrice = metaPriceList.get(0).getText().trim();
		String productExTaxPrice = metaPriceList.get(1).getText().trim();
		productMap.put("productPrice", productPrice);
		productMap.put("extraPrice", productExTaxPrice);

		return productMap;

	}

}
