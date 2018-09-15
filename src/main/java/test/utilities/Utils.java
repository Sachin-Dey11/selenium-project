package test.utilities;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Timer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class Utils {
	
	
static //	public static String strReportFilepath = GetReportLogFileName();
	String strReportFilepath=null;
	public static String strCurrentTestName = "";
	public static String strReportSummaryFilepath = GetReportSummaryLogFileName();
	public static String testCaseName;
	public static String prvTestCaseName = "";
	public static String prvTestCaseName1 = "";
	public static String testStatus = "";
	public static int stepCount = 0;
	public static int failStepCount = 0;
	public static int passStepCount = 0;
	public static int testCounter = 0;
	public Timer startTime = new Timer();
	public static long testStartTime = System.currentTimeMillis();
	public static long totalTime = System.currentTimeMillis();
	public Timer endTime = new Timer();
	public HashMap<String,String> testData;
	
	/**
	 * These are the enums for the different ways to find an element by calling FindElement
	 * */
	public enum SearchType {
		XPATH,
		NAME,
		ID,
		CLASS,
		LINKTEXT,
	}
	
	public static final int	IMPLICIT_TIMEOUT	= 5;

	public static String takesScreenshot(WebDriver driver) throws  InterruptedException{
		
		Date date = new Date();
	    SimpleDateFormat ft =  new SimpleDateFormat ("Eyyyy.MM.ddhhmmssazzz");
//	    String fileName = DriverDefault.getWorkingDirectory() + "\\Screenshots\\" + TestCaseDriver.imageFolder + "\\" + testCaseName + "_" + "Step_" + stepId + "_"+ ft.format(date)+ ".jpg";
	    String fileName = System.getProperty("user.dir") + "\\Screenshots\\" +  ft.format(date)+ ".jpg";
		
	    
	    File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return fileName;
	}


	
	public static void ReportLog(String screenshot, String inputValue, String strStatus, String strActionDescription, String strDescription){
		try{
	      						
			String computername=InetAddress.getLocalHost().getHostName() + "\\";
			String statuscolor;
			boolean result = false;
			if(strStatus.equals("PASS")){
				statuscolor = "green";
				result = true;
				passStepCount++;
			}else if(strStatus.equals("FAIL")){
				statuscolor = "red";
				result = false;
				failStepCount++;
			}else{
				statuscolor = "black";
			}
			if(screenshot==null || screenshot == "" || screenshot.equals("None")){
				screenshot = "N/A";
			}else{
				screenshot = screenshot.replace("C:\\", computername);
				screenshot = screenshot.replace("D:\\", computername);
				screenshot ="\\\\"+ screenshot ;
				screenshot = "<a href=\""+ screenshot + "\"><p><strong>ScreenShot<p></strong></a>";
			}
			
			if(strCurrentTestName!=testCaseName){
				strCurrentTestName = testCaseName;
				strReportFilepath = GetReportLogFileName(testCaseName);
				
			//	String computername=InetAddress.getLocalHost().getHostName() + "\\";
			//	strReportSummaryFilepath = strReportSummaryFilepath.replace("C:\\", computername);
			//	strReportSummaryFilepath = strReportSummaryFilepath.replace("D:\\", computername);
			//	strReportSummaryFilepath ="\\\\"+ strReportSummaryFilepath ;
				
				
				
				stepCount=1;
	        	// Create file 
				FileWriter fstream = new FileWriter(strReportFilepath,true);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write("<html><head><title>SELENIUM SUMMARY RESULTS</title></head>\n");
				
				
				
				
				
				
				//out.write("<a href=\"" + strReportSummaryFilepathLink +"\"><tr bgcolor=#CBD9F4><p><strong>Back To Test Summary>></strong></a>");
				
				
				
				out.write("<div align=right><a href=\""+ strReportSummaryFilepath + "\"><p><strong>Back To Test Summary>><p></strong></a></div>\n");
				
				out.write ("<div style =\""+ "overflow: scroll; height: 34em;\""+"><table width=100% bgcolor=#FFFFFF border=1 cellpadding=3 cellspacing=0></Table>\n");
				out.write ("<tr bgcolor=#CBD9F4><div align=left><p><strong>Test Name: </strong>"+ "<a name=" + testCaseName + ">" +testCaseName + "</a>" +"<strong><br />Test Description: </strong>"+ "No Description" +"</p></div></tr>\n");
				out.write ("<Table><tr><td bgcolor=#CBD9F4 width = 3%>#</td><td bgcolor=#CBD9F4 width = 7%>FRD Sec</td><td bgcolor=#CBD9F4 width = 32%>ACTION</td><td bgcolor=#CBD9F4 width = 10%>INPUT</td><td bgcolor=#CBD9F4 width = 42%>EXPECTED RESULTS</td><td bgcolor=#CBD9F4 width = 6%>STATUS</td>\n");
				out.write ("<TR bgcolor=#ffffcc><TD WIDTH = 3%>" + stepCount + "</TD><TD WIDTH = 7%>" + screenshot + "</TD><TD WIDTH = 32%>" + strActionDescription + "</TD><TD WIDTH = 10%>" + inputValue + "</TD><TD WIDTH=42%>" + strDescription + "</TD><TD WIDTH = 6%><font color="+ statuscolor +">" + strStatus +"</font></TD></TR>\n");
				
				out.close();
			}
				
			        	
	         FileWriter fstream = new FileWriter(strReportFilepath,true);
             BufferedWriter out = new BufferedWriter(fstream);
              if(strActionDescription == "END OF TEST"){
            	  ReportSummaryLog(strStatus);
            	  int validationCount = passStepCount + failStepCount;
            //	  LogHelper.logTestCaseFooter(validationCount, failStepCount, "", "");
            	  out.close();
            	  return;
              }
	          if(prvTestCaseName1 == testCaseName){    
	            	out.write ("<TR bgcolor=#ffffcc><TD WIDTH = 3%>" + stepCount + "</TD><TD WIDTH = 7%>" + screenshot + "</TD><TD WIDTH = 32%>" + strActionDescription + "</TD><TD WIDTH = 10%>" + inputValue + "</TD><TD WIDTH=42%>" + strDescription + "</TD><TD WIDTH = 6%><font color="+ statuscolor +">" + strStatus +"</font></TD></TR>\n");
	        	//  LogHelper.logStepStatus("Check", testCaseName, stepCount, result, strDescription);
	          }else if(prvTestCaseName1 != testCaseName && stepCount == 1 ){
	        	  prvTestCaseName1 = testCaseName;	
	        	  //stepCount = 1;
	        	//  passStepCount = 0;
	        	//  failStepCount=0;
	          }else if (prvTestCaseName1 != testCaseName && stepCount != 1 ){
					stepCount = 1;
				//	passStepCount=0;
				//	failStepCount=0;
					//testCounter++;
					prvTestCaseName1 = testCaseName;
					testStartTime = System.currentTimeMillis();
					out.write ("<TR bgcolor=#ffffcc><TD WIDTH = 3%></TD><TD WIDTH = 7%></TD><TD WIDTH = 32%><TD WIDTH = 10%></TD><TD WIDTH=42%></TD><TD WIDTH = 6%><font color=green</font></TD></TR></Table>\n");
			//		out.write ("<tr ><div bgcolor=#CBD9F4 align=left><p><strong>Test Name: </strong>"+ testCaseName +"<strong><br/>Test Description: </strong>"+ testCaseName +"</p></div></tr>\n");
					out.write ("<tr bgcolor=#CBD9F4><div align=left><p><strong>Test Name: </strong>"+ "<a name=" + testCaseName + ">" +testCaseName + "</a>" + "<strong><br />Test Description: </strong>"+ "No Decription" + "</p></div></tr>\n");
					out.write ("<Table><tr><td bgcolor=#CBD9F4 width = 3%>#</td><td bgcolor=#CBD9F4 width = 7%>FRD Sec</td><td bgcolor=#CBD9F4 width = 32%>ACTION</td><td bgcolor=#CBD9F4 width = 10%>INPUT</td><td bgcolor=#CBD9F4 width = 42%>EXPECTED RESULTS</td><td bgcolor=#CBD9F4 width = 6%>STATUS</td>\n");
					out.write ("<TR bgcolor=#ffffcc><TD WIDTH = 3%>" + stepCount + "</TD><TD WIDTH = 7%>" + screenshot + "</TD><TD WIDTH = 32%>" + strActionDescription + "</TD><TD WIDTH = 10%>" + inputValue + "</TD><TD WIDTH=42%>" + strDescription + "</TD><TD WIDTH = 6%><font color="+ statuscolor +">" + strStatus +"</font></TD></TR>\n");
				//	LogHelper.logStepStatus("Check", testCaseName, stepCount, result, strDescription);
	          }
	          out.close();
              stepCount++;
	          	        
	        
		  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());

		  }
	}	
	
	
	
	
	public static void ReportSummaryLog(String strStatus){
		try{
		  						
			String statuscolor;
	//		String computername=InetAddress.getLocalHost().getHostName() + "\\";
			
			
		//	strReportFilepath = strReportFilepath.replace("C:\\", computername);
		//	strReportFilepath = strReportFilepath.replace("D:\\", computername);
		//	strReportFilepath ="\\\\"+ strReportFilepath ;
			
			//check if report file exists
		    if (!(new File(strReportSummaryFilepath).exists())) {
		    	// Create file 
		    	
		    	
		    	FileWriter fstream = new FileWriter(strReportSummaryFilepath,true);
		    	BufferedWriter out = new BufferedWriter(fstream);
		    	out.write("<html><head><title>SELENIUM SUMMARY RESULTS</title></head>\n");
		    	out.write ("<body bgcolor=#ffffcc><table width=100% bgcolor=#FFFFFF border=1 cellpadding=3 cellspacing=0>\n");
		    	out.write ("<tr bgcolor=#CBD9F4><div align=center><p><strong>SUMMARY OF RESULTS </strong></p></div></tr>\n");
		    	out.write ("<tr bgcolor=#CBD9F4><div align=center><p><strong> </strong></p></div></tr>\n");
		    	out.write("<tr><td bgcolor=#CBD9F4 width = 4%>#</td><td bgcolor=#CBD9F4 width = 24%>TEST NAME</td><td bgcolor=#CBD9F4 width = 24%>STATUS</td><td bgcolor=#CBD9F4 width = 24%># STEPS</td><td bgcolor=#CBD9F4 width = 24%>STEPS FAILED</td></Table>\n");
		    	out.write("<div style =\" overflow: scroll; height: 34em;\"><table width=100% bgcolor=#FFFFFF border=1 cellpadding=3 cellspacing=0>\n");
		    	  	
		    	//Close the output stream
		    	out.close();
		    }
		    if(failStepCount >0){
		    	testStatus = "FAIL";
		    	statuscolor = "red";
		    }else{
		    	testStatus = "PASS";
				statuscolor = "green";
		    }
		    testCounter++;
		    totalTime = System.currentTimeMillis();
		    Timer startTime = new Timer();
			
			totalTime = System.currentTimeMillis();
			Timer endTime = new Timer();
		    //String totalTime = endTime - startTime;
	        FileWriter fstream = new FileWriter(strReportSummaryFilepath,true);
            BufferedWriter out = new BufferedWriter(fstream);
         //   out.write("<tr><td width = 4%>"+testCounter+"</td><td width = 25%><a href=\""+ strReportFilepath + "\">"+ testCaseName +"</a></td><td width = 24.75%><font color="+ statuscolor +">"+ testStatus +"</td><td width = 24.25%>"+ (stepCount - 1) +"</td><td width = 22.8%><font color=red>"+ failStepCount +"</td></TR>\n");
          out.write("<tr><td width = 4%>"+testCounter+"</td><td width = 25%><a href=\""+ testCaseName + ".html\">"+ testCaseName +"</a></td><td width = 24.75%><font color="+ statuscolor +">"+ testStatus +"</td><td width = 24.25%>"+ (stepCount - 1) +"</td><td width = 22.8%><font color=red>"+ failStepCount +"</td></TR>\n");
            
            out.close();
            stepCount++;

		  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());

		  }
	}	
	
	public static String GetUniqueFilename() {
		
		String workingDirectory = DriverDefault.getWorkingDirectory();
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd-hhmmss");  
	    df.setTimeZone(TimeZone.getTimeZone("PST"));  
	    String sUniquedate = df.format(new Date());
	    
	    if(new File(workingDirectory + "/Reports").exists()) {
		    return workingDirectory+"/Reports/SeleniumReport_"+ sUniquedate + ".html";
	    } 
	    
	    if (new File(workingDirectory + "/Reports").mkdir()){
	    	return workingDirectory+"/Reports/SeleniumReport_"+ sUniquedate + ".html";
	    }
	    
	    return null;
	    
	}
	
	public static String GetReportLogFileNameold() {
		
		String workingDirectory = DriverDefault.getWorkingDirectory();
	   
	    if(new File(workingDirectory + "/Reports").exists()) {
		    return workingDirectory+"/Reports/TransitPortal_Test_Summary_Detail.html";
	    } 
	    
	    if (new File(workingDirectory + "/Reports").mkdir()){
	    	return workingDirectory+"/Reports/TransitPortal_Test_Summary_Detail.html";
	    }
	    
	    return null;
	    
	}
	
	public static String GetReportLogFileName(String testCaseName) {
		
		String workingDirectory = DriverDefault.getWorkingDirectory();
	   
	    if(new File(workingDirectory + "/Reports").exists()) {
		    
	    	
	    	String testfilename=workingDirectory + "\\Reports\\" + testCaseName + ".html";
	    	if ((new File(testfilename).exists())){
	    		new File(testfilename).delete();
	      	}
	    
	    
	    	
	    	
	    	return workingDirectory+"/Reports/" + testCaseName + ".html";
	    } 
	    
	    if (new File(workingDirectory + "/Reports").mkdir()){
	    	
	    	System.out.println("file not deleted");
	    	return workingDirectory+"/Reports/" + testCaseName + ".html";
	    }
	    
	    return null;
	    
	}
	
	
	public static String GetReportSummaryLogFileName() {
		
		String workingDirectory = DriverDefault.getWorkingDirectory();
	   
	    if(new File(workingDirectory + "/Reports").exists()) {
		    return workingDirectory+"/Reports/TransitPortal_Test_Summary.html";
	    } 
	    
	    if (new File(workingDirectory + "/Reports").mkdir()){
	    	return workingDirectory+"/Reports/TransitPortal_Test_Summary.html";
	    }
	    
	    return null;
	    
	}
	
	/**
	 * use Thread.sleep(n) directly
	 * @param n
	 */
	public static void javaWait (int n){
        try {
        	Thread.sleep(n * 100);
        } catch (InterruptedException x) {
        	RuntimeException rx = new RuntimeException("Was awakened? " + x.getMessage());
        	rx.initCause(x);
        	throw rx;
        }
	}
	
	
	


	/**
	 * excelGetUniqueColVal: this method is used to find the unique column names within a
	 * excel file.
	 * 
	 * @param xlFilePath
	 *            Excel file path
	 * @param column
	 *            Column name for which values need to be returned
	 * 
	 * @return hash map which contains the umique values
	 */

	/**
	 * generateXMLFile: this method is used to generate object repository xml file.
	 * 
	 * @param excelFilePath
	 *            Excel file path for object definitions
	 * @param xmlFilePath
	 *            path where objectRepository.xml needs to be generated
	 * 
	 * 
	 */


	
	public static void ACT_takesScreenshot(WebDriver driver,String testCaseName,String stepId) throws IOException, InterruptedException{
	
		Date date = new Date();
	    SimpleDateFormat ft =  new SimpleDateFormat ("Eyyyy.MM.ddhhmmssazzz");
	    String fileName = DriverDefault.getWorkingDirectory() + "\\Screenshots\\" + testCaseName + "_" + "Step_" + stepId + "_"+ ft.format(date)+ ".jpeg";
	    File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(fileName));
	
	}
	
    public static String breakdate(String date,String datePart){
		
    	String[] datearray=date.split("/");
    	
    	if(datePart.equals("month"))
    	{
    		return datearray[0];
    	}else if(datePart.equals("day"))
    	{
    		return datearray[1];
    	}
    	else if(datePart.equals("year")){
    		
    		return datearray[2];
    	}
    	else
    	    	
    	return null;
    	
    }
	
    
 public static String[] XmlReturner(String xmlFilePath,String pageName,String sectionName,String objectName) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		
    	String[] returnedValues= new String[4];
    	DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance(); 
        builderfactory.setNamespaceAware(true); 
        DocumentBuilder builder = builderfactory.newDocumentBuilder(); 
        Document xmlDocument = builder.parse( new File(xmlFilePath)); 
        XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance(); 
        XPath xPath = factory.newXPath(); 
              
        String xpath = "//" + pageName + "/"+  sectionName + "/element[name='"  + objectName + "']/./value";
        
        //query object definition 
        XPathExpression xPathExpression = xPath.compile(xpath);
        String objectDef = xPathExpression.evaluate(xmlDocument,XPathConstants.STRING).toString(); 
        returnedValues[0]=objectDef;
 
        xpath = "//" + pageName + "/"+  sectionName + "/element[name='"  + objectName + "']/./searchType";

        xPathExpression = xPath.compile(xpath); 

        String searchBy = xPathExpression.evaluate(xmlDocument,XPathConstants.STRING).toString(); 
        returnedValues[1]=searchBy;
        returnedValues[2]=pageName;
        returnedValues[3]=objectName;
    
    	
    	
    	return returnedValues;
    	
    	
    	
    }
 
 
 public static String[] XmlReturnerhsqldb(String pageName,String sectionName,String objectName) throws Exception{
		
 	String[] returnedValues= new String[4];
 	String WORKING_DIR =  System.getProperty("user.dir");
	
  
 //	System.out.println(pageName);
 //	System.out.println(sectionName);
 //	System.out.println(objectName);
 	
 	String mySqlQueryobjdef = "select *  from objdef where PAGENAME='" + pageName + "' AND " + "SECTION='" + sectionName + "' AND " + "OBJECTNAME='" + objectName + "'";
//	System.out.println(mySqlQueryobjdef);
	
 	
 	
   
  //   System.out.println(returnedValues[0]);
   //  System.out.println(returnedValues[1]);
     returnedValues[2]=pageName;
     returnedValues[3]=objectName;
 
 	
 	
 	return returnedValues;
 	
 	
 	
 }

 
 
 	public static HashMap<String, String> XmlReturnerTestExecutionList(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		
 		 String[] returnedValues= new String[4];
	 	 DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance(); 
	     builderfactory.setNamespaceAware(true); 
	     DocumentBuilder builder = builderfactory.newDocumentBuilder(); 
	     Document xmlDocument = builder.parse( new File(xmlFilePath)); 
	     Element documentElement=xmlDocument.getDocumentElement();
	     NodeList sList=documentElement.getElementsByTagName("Test");
	     String[] colnames= new String[9];
	     colnames[0]="Id";
	     colnames[1]="Env";
	     colnames[2]="TestCaseName";
	     colnames[3]="TestDescription";
	     colnames[4]="Run";
	     
	 
	    
	     int numberOfColumns = 5;
	     
	     HashMap<String, String> data = new HashMap<String, String>();
	     
	     int rowNum=1;
	     
	     String Id="";
	     String Env="";
	     String TestCaseName="";
	     String TestDescription="";
	     String Run="";
	     
	     for(int i=0; i<sList.getLength(); i++) {
	             try {
	            	 Id= sList.item(i).getAttributes().getNamedItem("Id").getTextContent();
		             }
		             catch (Exception e) {
		            	 Id="";
		             } 
	             
	             
	             try {
	            	 Env= sList.item(i).getAttributes().getNamedItem("Env").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 Env="";
		     		 } 
			             
		         try {
		        	 TestCaseName= sList.item(i).getAttributes().getNamedItem("TestCaseName").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 TestCaseName="";
		     		 } 
	             
		             try {
		            	 TestDescription= sList.item(i).getAttributes().getNamedItem("TestDescription").getTextContent();
			             }
			             catch (Exception e) {
			         		
			            	 TestDescription="";
			     		 } 
	             try {
	            	 Run= sList.item(i).getAttributes().getNamedItem("Run").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 Run="";
		     		
		     		 } 
	             
	             String[] colvalues= new String[5];
	            
	             
	             colvalues[0]=Id;
	             colvalues[1]=Env;
	             colvalues[2]=TestCaseName;
	             colvalues[3]=TestDescription;
	             colvalues[4]=Run;
	             
	             
	             
	             for (int j = 0; j < numberOfColumns; j++) {
						data.put(colnames[j] + "_" + rowNum,colvalues[j]);
					}
	             rowNum++;
	         }
		return data;

	    	
	    	 
}
 
 	public static int XmlReturnerTestExecutionListCount(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		
		 String[] returnedValues= new String[4];
	 	 DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance(); 
	     builderfactory.setNamespaceAware(true); 
	     DocumentBuilder builder = builderfactory.newDocumentBuilder(); 
	     Document xmlDocument = builder.parse( new File(xmlFilePath)); 
	     Element documentElement=xmlDocument.getDocumentElement();
	     NodeList sList=documentElement.getElementsByTagName("Test");
	     return sList.getLength();
	     	    	
	    	 
}

 /**
	 * Create HashMap from XML based test case 
	 * 
	 * @param xmlfilepath
	 *            The reference to XML file
	 */    
 
 public static HashMap<String, String> XmlReturnerTestCase(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		
	 	 String[] returnedValues= new String[4];
	 	 DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance(); 
	     builderfactory.setNamespaceAware(true); 
	     DocumentBuilder builder = builderfactory.newDocumentBuilder(); 
	     Document xmlDocument = builder.parse( new File(xmlFilePath)); 
	     Element documentElement=xmlDocument.getDocumentElement();
	     NodeList sList=documentElement.getElementsByTagName("step");
	     String[] colnames= new String[8];
	     colnames[0]="StepId";
	     colnames[1]="PageName";
	     colnames[2]="SectionName";
	     colnames[3]="ObjectName";
	     colnames[4]="Action";
	     colnames[5]="Value";
	     colnames[6]="ExpectedValue";
	     colnames[7]="Description";
	 
	    
	     int numberOfColumns = 8;
	     
	     HashMap<String, String> data = new HashMap<String, String>();
	     
	     int rowNum=1;
	     
	     String StepId="";
	     String PageId="";
	     String Section="";
	     String object="";
	     String Action="";
	     String Value="";
	     String ExpectedValue="";
	     String Description="";
	     for(int i=0; i<sList.getLength(); i++) {
	             try {
	            	 StepId= sList.item(i).getAttributes().getNamedItem("stepId").getTextContent();
		             }
		             catch (Exception e) {
		            	 StepId="";
		             } 
	             
	             
	             try {
	            	 PageId= sList.item(i).getAttributes().getNamedItem("pageId").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 PageId="";
		     		 } 
	             
	            
	             try {
	            	 Section= sList.item(i).getAttributes().getNamedItem("section").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 Section="";
		     		
		     		 } 

		          try {
	            	 object= sList.item(i).getAttributes().getNamedItem("object").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 object="";
		     		
		     		}  
	             
	            
	            
	             try {
	            	 Action= sList.item(i).getAttributes().getNamedItem("action").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 Action="";
		     		
		     		}  
	             
	             
	             
	             try {
	            	 Value= sList.item(i).getAttributes().getNamedItem("value").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 Value="";
		     		
		     		}  
	             
	             
	             try {
	              ExpectedValue= sList.item(i).getAttributes().getNamedItem("expectedValue").getTextContent();
	             }
	             catch (Exception e) {
	         		
	            	 ExpectedValue="";
	     		
	     		}  
	             
	             try {
	            	 Description= sList.item(i).getAttributes().getNamedItem("description").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 Description="";
		     		
		     		}  
	             
	             
	             
	             String[] colvalues= new String[8];
	            
	             
	             colvalues[0]=StepId;
	             colvalues[1]=PageId;
	             colvalues[2]=Section;
	             colvalues[3]=object;
	             colvalues[4]=Action;
	             colvalues[5]=Value;
	             colvalues[6]=ExpectedValue;
	             colvalues[7]=Description;
	             
	             
	             for (int j = 0; j < numberOfColumns; j++) {
						data.put(colnames[j] + "_" + rowNum,colvalues[j]);
					}
	             rowNum++;
	         }
		return data;

	    	
	    	 
  }
		
	    
 /**
	 * Create HashMap from XML based test case (includes FRD_ID
	 * 
	 * @param xmlfilepath
	 *            The reference to XML file
	 */    

public static HashMap<String, String> XmlReturnerTestCaseFRD(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		
	 	 String[] returnedValues= new String[4];
	 	 DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance(); 
	     builderfactory.setNamespaceAware(true); 
	     DocumentBuilder builder = builderfactory.newDocumentBuilder(); 
	     Document xmlDocument = builder.parse( new File(xmlFilePath)); 
	     Element documentElement=xmlDocument.getDocumentElement();
	     NodeList sList=documentElement.getElementsByTagName("step");
	     String[] colnames= new String[9];
	     colnames[0]="StepId";
	     colnames[1]="FRD_Sec_Id";
	     colnames[2]="PageName";
	     colnames[3]="SectionName";
	     colnames[4]="ObjectName";
	     colnames[5]="Action";
	     colnames[6]="Value";
	     colnames[7]="ExpectedValue";
	     colnames[8]="Description";
	 
	    
	     int numberOfColumns = 9;
	     
	     HashMap<String, String> data = new HashMap<String, String>();
	     
	     int rowNum=1;
	     
	     String StepId="";
	     String FRDSectionId="";
	     String PageId="";
	     String Section="";
	     String object="";
	     String Action="";
	     String Value="";
	     String ExpectedValue="";
	     String Description="";
	     for(int i=0; i<sList.getLength(); i++) {
	             try {
	            	 StepId= sList.item(i).getAttributes().getNamedItem("stepId").getTextContent();
		             }
		             catch (Exception e) {
		            	 StepId="";
		             } 
	             
	             
	             try {
	            	 FRDSectionId= sList.item(i).getAttributes().getNamedItem("frdSectionId").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 FRDSectionId="";
		     		 } 
			             
		         try {
	            	 PageId= sList.item(i).getAttributes().getNamedItem("pageId").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 PageId="";
		     		 } 
	             
	            
	             try {
	            	 Section= sList.item(i).getAttributes().getNamedItem("section").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 Section="";
		     		
		     		 } 

		          try {
	            	 object= sList.item(i).getAttributes().getNamedItem("object").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 object="";
		     		
		     		}  
	             
	            
	            
	             try {
	            	 Action= sList.item(i).getAttributes().getNamedItem("action").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 Action="";
		     		
		     		}  
	             
	             
	             
	             try {
	            	 Value= sList.item(i).getAttributes().getNamedItem("value").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 Value="";
		     		
		     		}  
	             
	             
	             try {
	              ExpectedValue= sList.item(i).getAttributes().getNamedItem("expectedValue").getTextContent();
	             }
	             catch (Exception e) {
	         		
	            	 ExpectedValue="";
	     		
	     		}  
	             
	             try {
	            	 Description= sList.item(i).getAttributes().getNamedItem("description").getTextContent();
		             }
		             catch (Exception e) {
		         		
		            	 Description="";
		     		
		     		}  
	             
	             
	             
	             String[] colvalues= new String[9];
	            
	             
	             colvalues[0]=StepId;
	             colvalues[1]=FRDSectionId;
	             colvalues[2]=PageId;
	             colvalues[3]=Section;
	             colvalues[4]=object;
	             colvalues[5]=Action;
	             colvalues[6]=Value;
	             colvalues[7]=ExpectedValue;
	             colvalues[8]=Description;
	             
	             
	             for (int j = 0; j < numberOfColumns; j++) {
						data.put(colnames[j] + "_" + rowNum,colvalues[j]);
					}
	             rowNum++;
	         }
		return data;

	    	
	    	 
}
		
	     
	 
 
 
     
    /**
	 * To check if the string is null or empty. This method is commonly used in 
	 * passing the strings to the actions where the input is not mandatory.
	 * 
	 * @param String
	 *            String to be checked
	 * @return String
	 * 				Returns the same string if not null or empty
	 * 
	 */
    public static String isNullOrBlank(String s)
    {
      if(s==null || s.trim().equals("")){
    	  s = "";
      }
      
      return s;
      
    }

    /**
	 * Validates if an object is enabled
	 * 
	 * @param driver
	 *            The WebDriver that you want to make this call on
	 * @param objId
	 *            The identifier that you want to find
	 * @param searchType
	 *            The way you want to find the WebElement. See the SearchType enum values.
	 * @param enabled
	 *            true if the object is displayed, false otherwise
	 * @param description
	 *            A description that will be used in the log message to indicate what is being tested
	 * @return true if the object is displayed, false otherwise.
	 */
	

	public static String getMachineName() throws UnknownHostException {
		String str="D:\\CCViews\\hsinn_CI.02.00.00.00_int\\Architecture\\ContIteg\\CommonCI";
		
		
		String computername=InetAddress.getLocalHost().getHostName() + "\\";
		str = str.replace("C:\\", computername) ;
		str = str.replace("D:\\", computername);
		str = "\\\\"+ str ;
		
	//	System.out.println(str);
		return str;
	}

}
