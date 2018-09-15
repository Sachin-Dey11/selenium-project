package test.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class common {

	//WebDriver driver;
	public static String getValueFromPropertyFile(String propertyName) throws IOException{
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "//src//test//resources//config.properties");
		System.out.println(fis.toString());
		prop.load(fis);
		return prop.getProperty(propertyName);
	}
	
	public static String repository(String elementName) {
		
		Properties prop = new Properties();
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(System.getProperty("user.dir")+ "//src//test//resources//repository.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return prop.getProperty(elementName);
	}
	
	public static WebDriver launchBrowser(String browser) {
		//String browser = null;
		//browser = common.getValueFromPropertyFile("browser");
		//browser = System.getProperty("browser");
		if(browser==null){
			try {
				browser = common.getValueFromPropertyFile("browser");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(browser.equals("browser")){
			try {
				browser = common.getValueFromPropertyFile("browser");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
		}
		
		
		WebDriver driver = null;
		
		if(browser.equals("firefox")){
			//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//src//test//resources//geckodriver.exe");
			driver = new FirefoxDriver();
			
			
		}
		if(browser.equals("ie")){
			DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
            capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//src//test//resources//IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			
		}
		if(browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
			driver = new ChromeDriver();
			
			
		}
		
		
		try {
			driver.get(common.getValueFromPropertyFile("url"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		return driver;
	}
	
}
