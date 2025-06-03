package hooks;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.loop.framework.core.Loop;
import com.loop.framework.core.PageObjectBase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	public static WebDriver driver;
	public static Properties p;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static final Logger logger = LogManager.getLogger(Hooks.class);

//	@Before
//	public void setup(Scenario scenario) throws IOException {
//		extent = ExtentReportManager.getExtentReports();
//		test = extent.createTest(scenario.getName());
//		logger.info("Starting Scenario: " + scenario.getName());
//
//		driver = PageObjectBase.initializeBrowser();
//		p = PageObjectBase.getProperties();
//		driver.get(p.getProperty("appURL"));
//		driver.manage().window().maximize();
//	}
//
//	@After
//	public void teardown(Scenario scenario) {
//		if (scenario.isFailed()) {
//			test.fail("Scenario Failed: " + scenario.getName());
//			logger.error("Scenario Failed: " + scenario.getName());
//		} else {
//			test.pass("Scenario Passed: " + scenario.getName());
//			logger.info("Scenario Passed: " + scenario.getName());
//		}
//		driver.quit();
//		extent.flush();
//	}

	@Before
	public void setup(Scenario scenario) throws IOException {
		// Loop.reporter.startTest(scenario.getName());
		driver = PageObjectBase.initializeBrowser();
		p = PageObjectBase.getProperties();
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}

	@After
	public void tearDown(Scenario scenario) {
		Loop.reporter.flushReports();
		driver.quit();
		// extent.flush();
	}
}
