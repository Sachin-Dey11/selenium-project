package test.utilities;

import java.io.File;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


/**
 * note Selenium has different apis some
 * work with GWT recording playback
 * some don't so we are mixing and matching
 * so that we can do the best job possible
 * 
 * Use the best one for your test cases.
 * 
 * @author smorga
 *
 */
public class SeleniumApis {
	
	
	/**
	 * client code which calls this to create FireFoxWindow
	 * is responsible for closing it to the full test run doesn't hang
	 * 
	 * @return
	 */
	public WebDriver createBrowserDriver(String browserType) {
		if(browserType.equals("FF")){
			return new FirefoxDriver();
		}
		else{
			
			String WORKING_DIR =  System.getProperty("user.dir");
			String IEDriver = WORKING_DIR + "\\src\\test\\resources\\IEDriverServer.exe";
			File file = new File(IEDriver);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			return new InternetExplorerDriver();
		}
	}

	/*public WebDriver createFireFoxWebDriver() {
		return new FirefoxDriver();
	}*/


	/**
	 * 
	 * @return a shared selenium instance
	 *    which opens two firefox windows 
	 *    one of which 
	 * @throws Exception
	 */
	

	
	
}
