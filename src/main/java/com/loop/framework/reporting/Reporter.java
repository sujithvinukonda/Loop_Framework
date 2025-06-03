package com.loop.framework.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.loop.framework.core.ScreenshotUtil;

public class Reporter {

	private static final String LOG_LINE = "-----%s";

	private static ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();

	// Each Gherkin step gets its own list of MethodLog objects
	private static ThreadLocal<List<MethodLog>> currentMethodLogs = ThreadLocal.withInitial(ArrayList::new);

	public void startTest(String testName) {
		ExtentTest test = ExtentReportManager.getExtentReports().createTest(testName);
		currentTest.set(test);
	}

	public void flushReports() {
		ExtentReportManager.getExtentReports().flush();
	}

	public void methodPassed() {
		try {
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			String screenshot = ScreenshotUtil.captureScreenshotAsBase64();
			String timestamp = new SimpleDateFormat("hh:mm:ss a").format(new Date());

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
		try {
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			String screenshot = ScreenshotUtil.captureScreenshotAsBase64();
			String timestamp = new SimpleDateFormat("hh:mm:ss a").format(new Date());

			// Store failed log
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
