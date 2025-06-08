package com.loop.framework.reporting;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.loop.framework.core.DataHandle;

public class ExtentReportManager {

	private static ExtentReports extent;

//	public static ExtentReports getExtentReports() {
//		if (extent == null) {
//			String caseID = DataHandle.getCurrentCaseID(); // ✅ get from static holder
//			if (caseID == null || caseID.isBlank()) {
//				caseID = "UnknownCase";
//			}
//			String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
//			String reportPath = "test-output/ExtentReports/" + caseID + "_" + timeStamp + ".html";
//			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
//			sparkReporter.config().setReportName("Automation Execution Report");
//			sparkReporter.config().setDocumentTitle("Test Report");
//
//			extent = new ExtentReports();
//			extent.attachReporter(sparkReporter);
//			extent.setSystemInfo("Environment", "QA");
//			extent.setSystemInfo("Tester", System.getProperty("user.name"));
//		}
//		return extent;
//	}
	
	public static ExtentReports getExtentReports() {
	    String caseID = DataHandle.getCurrentCaseID(); // Get current test case ID
	    if (caseID == null || caseID.isBlank()) {
	        caseID = "UnknownCase";
	    }
	    String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
	    String reportPath = "test-output/ExtentReports/" + caseID + "_" + timeStamp + ".html";

	    ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
	    sparkReporter.config().setReportName("Automation Execution Report - " + caseID);
	    sparkReporter.config().setDocumentTitle("Test Report for " + caseID);

	    extent = new ExtentReports();
	    extent.attachReporter(sparkReporter);
	    extent.setSystemInfo("Environment", "QA");
	    extent.setSystemInfo("Tester", System.getProperty("user.name"));

	    return extent;
	}

}