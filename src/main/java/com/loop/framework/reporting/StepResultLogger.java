package com.loop.framework.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.loop.framework.core.ScreenshotUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StepResultLogger {

	private static class MethodStep {
		String name;
		String base64Image;

		MethodStep(String name, String img) {
			this.name = name;
			this.base64Image = img;
		}
	}

	private static class FailedMethodStep {
		String name, message, base64Image;
		Throwable error;

		FailedMethodStep(String name, String message, String img, Throwable error) {
			this.name = name;
			this.message = message;
			this.base64Image = img;
			this.error = error;
		}
	}

	private static final ThreadLocal<List<MethodStep>> passedMethods = ThreadLocal.withInitial(ArrayList::new);
	private static final ThreadLocal<List<FailedMethodStep>> failedMethods = ThreadLocal.withInitial(ArrayList::new);

	public static void logPassedMethod(String methodName) {
		try {
			String img = ScreenshotUtil.captureScreenshotAsBase64();
			passedMethods.get().add(new MethodStep(methodName, img));
		} catch (Exception ignored) {
		}
	}

	public static void logFailedMethod(String methodName, String message, Throwable error) {
		try {
			String img = ScreenshotUtil.captureScreenshotAsBase64();
			failedMethods.get().add(new FailedMethodStep(methodName, message, img, error));
		} catch (Exception ignored) {
		}
	}

	public static void flushUnderStep(ExtentTest parentNode) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");

		for (MethodStep step : passedMethods.get()) {
			try {
				String time = sdf.format(new Date());
				parentNode.createNode("✅ " + time + " Step Passed: " + step.name)
						.pass(MediaEntityBuilder.createScreenCaptureFromBase64String(step.base64Image).build());
			} catch (Exception e) {
				parentNode.createNode("Step Passed: " + step.name + " (⚠ Screenshot failed)").fail(e);
			}
		}

		for (FailedMethodStep step : failedMethods.get()) {
			try {
				String time = sdf.format(new Date());
				parentNode.createNode("❌ " + time + " Step Failed: " + step.name + " - " + step.message)
						.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(step.base64Image).build())
						.fail(step.error);
			} catch (Exception e) {
				parentNode.createNode("Step Failed: " + step.name + " (⚠ Screenshot failed)").fail(e);
			}
		}

		passedMethods.get().clear();
		failedMethods.get().clear();
	}
}
