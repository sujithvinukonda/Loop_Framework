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

	@Before
	public void setup(Scenario scenario) throws IOException {
		driver = PageObjectBase.initializeBrowser();
		p = PageObjectBase.getProperties();
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}

	@After
	public void tearDown(Scenario scenario) {
		Loop.reporter.flushReports();
		driver.quit();
	}
}
