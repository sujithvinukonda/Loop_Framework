package com.loop.framework.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.loop.framework.core.ScreenshotUtil;

public class Reporter {

	private static ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();

	// Each Gherkin step gets its own list of MethodLog objects
	private static ThreadLocal<List<MethodLog>> currentMethodLogs = ThreadLocal.withInitial(ArrayList::new);

	private static ExtentReports extent; // Ensure extent is declared at the class level

	public void startTest(String testName) {
		extent = ExtentReportManager.getExtentReports(); // Initialize a new report for each test case
		ExtentTest test = extent.createTest(testName);
		currentTest.set(test);
	}

	public void flushReports() {
		if (extent != null) {
			extent.flush();
		}
	}

	public void methodPassed() {
//		try {
//			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
//			String screenshot = ScreenshotUtil.captureScreenshotAsBase64();
//			String timestamp = new SimpleDateFormat("hh:mm:ss a").format(new Date());
//
//			currentMethodLogs.get().add(new MethodLog(methodName, timestamp, screenshot, true));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			String screenshot = ScreenshotUtil.captureScreenshotAsBase64();
			String timestamp = new SimpleDateFormat("hh:mm:ss a").format(new Date());
			long executionTime = System.currentTimeMillis();

			// Log structured console output
			// System.out.println("Pass" + executionTime);
			System.out.println("✅ Pass " + timestamp + " Step Passed: " + methodName);

			currentMethodLogs.get().add(new MethodLog(methodName, timestamp, screenshot, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void flushStepLogsTo(ExtentTest gherkinStepNode) {
		for (MethodLog log : currentMethodLogs.get()) {
			try {
				String label = (log.passed ? "✅" : "❌") + " " + log.timestamp + " Step "
						+ (log.passed ? "Passed" : "Failed") + ": " + log.methodName;
				if (log.passed) {
					gherkinStepNode.createNode(label)
							.pass(MediaEntityBuilder.createScreenCaptureFromBase64String(log.screenshot).build());
				} else {
					gherkinStepNode.createNode(label)
							.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(log.screenshot).build())
							.fail(log.error);
				}
			} catch (Exception e) {
				gherkinStepNode.warning("⚠ Failed to attach method log: " + log.methodName);
			}
		}
		currentMethodLogs.get().clear();
	}

	public void methodFailed(String message, Throwable error) {
//		try {
//			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
//			String screenshot = ScreenshotUtil.captureScreenshotAsBase64();
//			String timestamp = new SimpleDateFormat("hh:mm:ss a").format(new Date());
//
//			// Store failed log
//			currentMethodLogs.get().add(new MethodLog(methodName, timestamp, screenshot, false, error));
//
//			org.junit.Assert.fail(message); // Triggers JUnit failure
//		} catch (Exception e) {
//			e.printStackTrace();
//			org.junit.Assert.fail("❌ Reporting failure: " + e.getMessage());
//		}
		try {
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			String screenshot = ScreenshotUtil.captureScreenshotAsBase64();
			String timestamp = new SimpleDateFormat("hh:mm:ss a").format(new Date());
			long executionTime = System.currentTimeMillis();

			// Log structured console output
			// System.out.println("Fail" + executionTime);
			System.out.println("❌ Fail " + timestamp + " Step Failed: " + methodName + " - " + message);

			currentMethodLogs.get().add(new MethodLog(methodName, timestamp, screenshot, false, error));

			org.junit.Assert.fail(message); // Triggers JUnit failure
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail("❌ Reporting failure: " + e.getMessage());
		}
	}

	public ExtentTest getCurrentTest() {
		return currentTest.get();
	}

	// Helper class to track method logs
	private static class MethodLog {
		String methodName, timestamp, screenshot;
		boolean passed;
		Throwable error;

		public MethodLog(String name, String time, String shot, boolean pass) {
			this.methodName = name;
			this.timestamp = time;
			this.screenshot = shot;
			this.passed = pass;
		}

		public MethodLog(String name, String time, String shot, boolean pass, Throwable err) {
			this(name, time, shot, pass);
			this.error = err;
		}
	}
}
