package test.utilities;
import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class DriverDefault {
	private static String workingDirectory = System.getProperty("user.dir");

	private static SeleniumApis seleniumApis = null;

   
	private static String seleniumServerHost; 
	private static int seleniumServerPort;
	private static String seleniumBrowserStartCommand;
	private static String seleniumBrowserURL;
    private static String seleniumBrowserPath;
 
    private static boolean isSetup = false;
    
    public static void setupProperties() {
    	

    }
    
    
	public static void setupSelenium() {  	
    	
    }



	public static void runSeleniumServer() throws Exception {
	
	}
    
    
	

	
	public static String getWorkingDirectory() {
		return workingDirectory;
	}
	
	
}
