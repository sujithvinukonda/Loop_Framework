package com.loop.framework.core;

import static com.loop.framework.core.PageObjectBase.getDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

	public static File captureScreenshotAsFile() {
		try {
			File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			File destFile = new File("screenshots/" + UUID.randomUUID() + ".png");
			destFile.getParentFile().mkdirs();
			Files.copy(screenshot.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			return destFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String captureScreenshotAsBase64() {
		try {
			byte[] screenshotBytes = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
			return Base64.getEncoder().encodeToString(screenshotBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
