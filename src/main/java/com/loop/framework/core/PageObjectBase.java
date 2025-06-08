package com.loop.framework.core;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PageObjectBase {

	protected static WebDriver driver;
	protected static Properties properties;

	public PageObjectBase(WebDriver driver) {
		PageObjectBase.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public static WebDriver initializeBrowser() throws IOException {
		properties = getProperties();
		String executionEnv = properties.getProperty("execution_env");
		String browser = properties.getProperty("browser").toLowerCase();
		String os = properties.getProperty("os").toLowerCase();
		if (executionEnv.equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// OS configuration
			switch (os) {
			case "windows":
				capabilities.setPlatform(Platform.WINDOWS);
				break;
			case "mac":
				capabilities.setPlatform(Platform.MAC);
				break;
			case "linux":
				capabilities.setPlatform(Platform.LINUX);
				break;
			default:
				System.out.println("No matching OS");
				return null;
			}

			// Browser configuration
			switch (browser) {
			case "chrome":
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				capabilities.setBrowserName("chrome");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			default:
				System.out.println("No matching browser");
				return null;
			}

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

		} else if (executionEnv.equalsIgnoreCase("local")) {
			switch (browser) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			default:
				System.out.println("No matching browser");
				return null;
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driverInstance) {
		driver = driverInstance;
	}

	public static Properties getProperties() throws IOException {
		String path = "\\src\\test\\resources\\configuration\\config.properties";
		FileReader file = new FileReader(System.getProperty("user.dir") + path);
		properties = new Properties();
		properties.load(file);
		return properties;
	}
}
