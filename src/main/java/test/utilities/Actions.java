package test.utilities;

import java.util.EmptyStackException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Actions extends Utils {


	public static void clickElement(WebDriver driver, String xpath, String elementName)  {

		try {
			driver.findElement(By.xpath(xpath)).click();
			ReportLog("None", "", "PASS", "CLICK ELEMENT", "Clicking on [" + elementName + "]");
			test.utilities.LogHelper.logStepStatus("INFO", Utils.testCaseName, "-", true, "Clicking on [" + elementName + "]");
		} catch (Exception e) {
			ReportLog("None", "", "FAIL", "CLICK ELEMENT", "Clicking on " + elementName);
			test.utilities.LogHelper.logStepStatus("INFO", Utils.testCaseName, "-", false, "Clicking on [" + elementName + "]");
			throw new EmptyStackException();
		}
	}

	public static void enterText(WebDriver driver, String xpath, String elementName,String text) {

		try {
			driver.findElement(By.xpath(xpath)).sendKeys(text);
			ReportLog("None", "", "PASS", "ENTER TEXT", "Entering [" + text +  "] in [" + elementName + "]");
			test.utilities.LogHelper.logStepStatus("INFO", Utils.testCaseName, "-", true, "Entering [" + text + "] in [" + elementName + "]");
		} catch (Exception e) {
			ReportLog("None", "", "FAIL", "ENTER TEXT", "Entering [" + text + "] in " + elementName);
			test.utilities.LogHelper.logStepStatus("INFO", Utils.testCaseName, "-", false, "Entering [" + text + "] in [" + elementName + "]");
			throw new EmptyStackException();
		}
	}

	public static boolean searchTextValidation(WebDriver driver, String textToSearch)  {


		if(driver.findElements(By.xpath("//*[contains(text(),'" + textToSearch + "')]")).size()>0){
			try {
				ReportLog(Utils.takesScreenshot(driver), "", "PASS", "FINDING TEXT", "Finding [" + textToSearch +  "]" );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			test.utilities.LogHelper.logStepStatus("INFO", Utils.testCaseName, "-", true, "Finding [" + textToSearch + "]" );
			return true;
		}else
		{
			ReportLog("None", "", "FAIL", "FINDING TEXT", "Finding [" + textToSearch +  "]" );
			test.utilities.LogHelper.logStepStatus("INFO", Utils.testCaseName, "-", false, "Finding [" + textToSearch + "]" );
			return false;
		}

	}
	public static void searchElement(WebDriver driver, String xpath, String elementName) {

		try {
			driver.findElement(By.xpath(xpath));
			ReportLog("None", "", "PASS", "FINDING ELEMENT", "Finding [" + elementName +  "]");
			test.utilities.LogHelper.logStepStatus("INFO", Utils.testCaseName, "-", true, "Finding [" + elementName + "]");
		} catch (Exception e) {
			ReportLog("None", "", "FAIL", "FINDING ELEMENT", "Finding [" + elementName + "]");
			test.utilities.LogHelper.logStepStatus("INFO", Utils.testCaseName, "-", false, "Finding [" + elementName + "]");
			throw new EmptyStackException();
		}
	}
}
