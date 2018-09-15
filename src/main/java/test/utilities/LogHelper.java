package test.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;


public class LogHelper {

	private static final Logger		log						= Logger.getLogger(LogHelper.class);
	private static boolean			startedRun				= false;
	/**
	 * if a test case id is in here it has already been logged as complete (PASSED or FAILED)
	 */
	private static Set<String>		loggedTestCasesFinished	= new HashSet<String>();
	private static SimpleDateFormat	sdf						= new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

	public static void logTestStart(String name, Logger log) {
		log.info("---------------------------------------------------------------------------------");
		log.info("Starting Test " + name);
	}

	public static synchronized void startingTestRun() {
		if (!startedRun) {
			log.info("---------------------------------------------------------------------------------");
			log.info("---------------------------------------------------------------------------------");
			log.info("STARTING TEST RUN");
			log.info("---------------------------------------------------------------------------------");
			log.info("---------------------------------------------------------------------------------");

			startedRun = true;
		}
	}

	public static void logTestCaseSepStar() {
		log.info("*********************************************************************************");
	}

	public static void logTestCaseDash() {
		log.info("---------------------------------------------------------------------------------");
	}

	public static void logStepStatus(String messageType, String tcName, String tcStepId, boolean status, String description) {
		String statusText = "FAIL";
		//tcStepId=org.apache.commons.lang.StringUtils.leftPad(tcStepId, 4, " ");
					
		
		if (status) {
			statusText = "PASS";
		}
		if (!status) {
			statusText = "FAIL";
		}
		if (messageType.equals("Admin")) {
			log.info(sdf.format(new Date()) + "|" + messageType + "|" + tcName + "|" + tcStepId + "|    " + "|" + description);
		}
		else {
			log.info(sdf.format(new Date()) + "|" + messageType + "|" + tcName + "|" + tcStepId + "|" + statusText + "|" + description);
		}
	}

	public static void logTestCaseHeader(String tcId, String tcDesc) {
		logTestCaseSepStar();
		log.info("Test Case Id    = " + tcId);
		log.info("Test Case Desc  = " + tcDesc);
		log.info("Test Case Start = " + new Date());
		logTestCaseDash();
	}

	public static void logTestCaseFooter(int tcChecks, int tcErrors, String tcKnownIssues, String tcTime) {
		logTestCaseDash();
		log.info("Test Case Checks  = " + tcChecks);
		log.info("Test Case Errors  = " + tcErrors);
		log.info("Test Case Known Issues = " + tcKnownIssues);
		log.info("Test Case Status  = " + (((tcErrors > 0) || (tcChecks == 0)) ? "FAIL" : "PASS"));
		log.info("Test Case Time 	= " + new Date());
		log.info("Test Case End 	= " + new Date());
		logTestCaseSepStar();

	}

	public static void logTestResults(String testCaseID) {
		/*
		 * //TODO check if test already was logged for pass or failure
		 * Boolean result = FunctionalTestCaseTracker.isSuccess(testCaseID);
		 * if (result != null) {
		 * if (!loggedTestCasesFinished.contains(testCaseID)) {
		 * if (result) {
		 * log.info("Test " + testCaseID + " PASSED" );
		 * } else {
		 * log.info("Test " + testCaseID + " FAILED" );
		 * }
		 * loggedTestCasesFinished.add(testCaseID);
		 * }
		 * }
		 */
	}

	/**
	 * copied and modified from cvs.adligo.org jse_util ... JSEThrowableHelper
	 * 
	 * @param preText
	 *            (the intention text for stack trace lines)
	 * @param trace
	 * @param lineFeed
	 * @return
	 */
	public static String getStackTraceAsString(StackTraceElement[] trace, String message) {
		String lineFeed = System.getProperty("line.separator");

		StringBuilder buf = new StringBuilder();
		buf.append(" <");
		buf.append(message);
		buf.append(">");

		buf.append(lineFeed);

		for (int j = 0; j < trace.length; j++) {
			// do twice as many indents for the stack trace
			buf.append("\t\t");
			buf.append(" at ");
			buf.append(trace[j].toString());
			buf.append(lineFeed);
		}
		return buf.toString();
	}

	
}
