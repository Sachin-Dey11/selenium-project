package com.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.pages.OMLandingPage;
import com.pages.OMMyOrderPage;

import test.utilities.LogHelper;
import test.utilities.Utils;
import test.utilities.common;

public class OrderManagement extends Utils{
	WebDriver driver ;
	private static String WORKING_DIR =  System.getProperty("user.dir");
	//static String TransitPortal_Test_Summary=WORKING_DIR + "\\Reports\\TransitPortal_Test_Summary.html";

	@Parameters({"browser"})
	@BeforeMethod
	public void setup(@Optional("browser") String value){
		driver= common.launchBrowser(value);
		Utils.testCaseName="OM Verification";
		LogHelper.logTestCaseHeader(testCaseName, "Order Management Flow");

	}

	@Test
	public void omStatusVerify(){
		OMLandingPage omLandingPage = new OMLandingPage();
		OMMyOrderPage omMyOrderPage = new OMMyOrderPage();
		omLandingPage.verifyOMLanding(driver);
		omMyOrderPage.verifyOMMyOrderPage(driver);
	}


	@AfterMethod
	public void teardown(){
		driver.quit();		
	}
}
