package com.pages;

import org.openqa.selenium.WebDriver;

import test.utilities.Actions;
import test.utilities.common;

public class OMLandingPage {
public void verifyOMLanding(WebDriver driver) {
		Actions.searchElement(driver, common.repository("OM-CheckOrderStatus"), "Check Order Status");
		Actions.searchElement(driver, common.repository("OM-CancelYourOrder"), "Check Cancel Order");
		Actions.searchElement(driver, common.repository("OM-OrderAReturnKit"), "Check Order a Return Kit");
		Actions.searchElement(driver, common.repository("OM-CheckTransferEligibility"), "Check Order Transfer Eligibility");
		Actions.searchElement(driver, common.repository("OM-CheckTransferStatus"), "Check Order Transfer Status");
		Actions.clickElement(driver, common.repository("OM-CheckOrderStatus"), "Check Order Status");
	}

}
