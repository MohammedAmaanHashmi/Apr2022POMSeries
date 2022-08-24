package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class CartPage {
	
	By cart = By.id("cart");

	
	public boolean addToCart()
	{
		System.out.println("add to cart");
		System.out.println("click on cart");
		return true;
	}
}
