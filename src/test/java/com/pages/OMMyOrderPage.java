package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import test.utilities.Actions;
import test.utilities.common;

public class OMMyOrderPage {
	public void verifyOMMyOrderPage(WebDriver driver) {
		Actions.searchElement(driver, common.repository("OM-OrderNumber-Textfield"), "Check Order Number Textfield");
		Actions.enterText(driver, common.repository("OM-OrderNumber-Textfield"), "Order Number Textfield", "or-trtm-103225782");
		System.out.println("Sachin" + driver.findElement(By.xpath(common.repository("OM-OrderNumber-Textfield"))).getText());
		
		Actions.searchElement(driver, common.repository("OM-OrderNumber-Textfield"), "Check Order Status Button");
		Actions.clickElement(driver, "OM-CheckOrderStatus-Button", "Check Order Status Button");
	}

}
