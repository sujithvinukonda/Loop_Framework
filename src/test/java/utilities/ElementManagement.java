package utilities;

import static org.junit.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.loop.framework.core.PageObjectBase;

public class ElementManagement extends PageObjectBase {

	public ElementManagement(WebDriver driver) {
		super(driver);
	}

	private static final String ELEMENT_NOT_DISPLAYED = " element is not displayed. ";
	private static final String ELEMENT_FORMAT_MESSAGE = "%s %s";
	private static final String ERROR_ELEMENT_INTERACTION = "Error interacting with the element %s. Exception/ Assertion: %s";
	private static final String ERROR_VALIDATIONG_ELEMENT = "Error validating element";

	/**
	 * Validate that the expected element exist and focus the screen on it
	 *
	 * @param element
	 * @param elementName
	 * @return
	 */
	public static boolean elementValidationAndFocus(WebElement element, String elementName, boolean isClickable) {
		try {
			assertTrue(String.format(ELEMENT_FORMAT_MESSAGE, elementName, ELEMENT_NOT_DISPLAYED),
					fluentWaitUtilIsPresent(element, isClickable));
			scrollToObject(element);
			highlightObject(element);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Check if the desired element is visible to highlight it and click on it.
	 *
	 * @param element
	 * @param elementName
	 * @return
	 */
	public static Boolean verifyAndClickElement(WebElement element, String elementName) {
		try {
			assertTrue(ERROR_VALIDATIONG_ELEMENT, elementValidationAndFocus(element, elementName, true));
			element.click();
			return true;
		} catch (Exception | AssertionError e) {
			return false;
		}
	}

	/**
	 * Check if the desired element is visible to highlight it and select it
	 *
	 * @param element
	 * @param elementName
	 * @param visibleText
	 * @return
	 */
	public static Boolean verifyAndSelectComboBox(WebElement element, String elementName, String visibleText) {
		try {
			assertTrue(ERROR_VALIDATIONG_ELEMENT, elementValidationAndFocus(element, elementName, true));
			Select elementToSelect = new Select(element);
			elementToSelect.selectByVisibleText(visibleText);
			return true;
		} catch (Exception | AssertionError e) {
			return false;
		}
	}

	/**
	 * Check if the desired element is visible to highlight it and add the input
	 * information
	 *
	 * @param element
	 * @param elementName
	 * @param informationToAdd
	 * @return
	 */
	public static Boolean verifyAndAddTextInformation(WebElement element, String elementName, String informationToAdd) {
		try {
			assertTrue(ERROR_VALIDATIONG_ELEMENT, elementValidationAndFocus(element, elementName, true));
			element.clear();
			element.sendKeys(informationToAdd);
			return true;
		} catch (Exception | AssertionError e) {
			return false;
		}
	}

	/**
	 * Check if the desired element is visible to highlight it and verify the label
	 * information
	 *
	 * @param element
	 * @param elementName
	 * @param informationToAdd
	 * @return
	 */
	public static Boolean verifyLabelText(WebElement element, String elementName, String expectedInformation,
			boolean fullComparison) {
		try {
			assertTrue(ERROR_VALIDATIONG_ELEMENT, elementValidationAndFocus(element, elementName, false));

			if (fullComparison) {
				assertTrue("The " + elementName + " does not have expected information: " + expectedInformation,
						element.getText().contentEquals(expectedInformation));
			} else {
				assertTrue("The " + elementName + " does not have expected information: " + expectedInformation,
						element.getText().contains(expectedInformation));
			}
			return true;
		} catch (Exception | AssertionError e) {
			return false;
		}
	}

	/**
	 * Wait for a web element until it is unstuck and clickable.
	 *
	 * @param object
	 * @return
	 * @deprecated use {@link fluentWaitUtilIsPresent(WebElement object)} instead.
	 */
	@Deprecated
	public static boolean waitElementUntilIsPresent(WebElement object) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait.until(driver -> ExpectedConditions.visibilityOf(object).apply(driver));
			wait.until(driver -> ExpectedConditions.elementToBeClickable(object).apply(driver));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wait for a web element until it is unstuck and clickable.
	 *
	 * @param object
	 * @param timeOutInSeconds
	 * @return
	 * @deprecated use {@link fluentWaitUtilIsPresent(WebElement object, int
	 *             timeOutInSeconds)} instead.
	 */
	@Deprecated
	public static boolean waitElementUntilIsPresent(WebElement object, int timeOutInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
			wait.until(driver -> ExpectedConditions.visibilityOf(object).apply(driver));
			wait.until(driver -> ExpectedConditions.elementToBeClickable(object).apply(driver));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * If a element is present, returns true, else return false
	 *
	 * @param By whose presence is being checked
	 * @return true if webElement is present, else false
	 */
	public static boolean isElementPresent(WebElement webelement) {
		try {
			boolean exists = false;
			webelement.getTagName();
			exists = true;
			return exists;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wait for a web element until it is unstuck and clickable.
	 *
	 * @param webElement
	 * @param timeOutInSeconds
	 * @return
	 */
	public static boolean fluentWaitUtilIsPresent(WebElement webElement, int timeOutInSeconds, boolean isClickable) {
		try {
			Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeOutInSeconds))
					.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
			wait.until(driver -> ExpectedConditions.visibilityOf(webElement).apply(driver));
			if (isClickable) {
				wait.until(driver -> ExpectedConditions.elementToBeClickable(webElement).apply(driver));
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wait for a web element until it is unstuck and clickable.
	 *
	 * @param webElement
	 * @return
	 */
	public static boolean fluentWaitUtilIsPresent(WebElement webElement, boolean isClickable) {
		try {
			Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofMinutes(1))
					.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
			wait.until(driver -> ExpectedConditions.visibilityOf(webElement).apply(driver));
			if (isClickable) {
				wait.until(driver -> ExpectedConditions.elementToBeClickable(webElement).apply(driver));
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wait for expected windows
	 *
	 * @param webElement
	 * @return
	 */
	public static boolean fluentWaitUtilExpectedWindowsArePresent(int windowsNumber) {
		try {
			Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofMinutes(1))
					.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
			wait.until(driver -> ExpectedConditions.numberOfWindowsToBe(windowsNumber).apply(driver));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Highlights the object in the page with a blue square around it.
	 *
	 * @param object - Selenium WebElement, to be highlighted
	 * @author JM
	 */
	public static void highlightObject(WebElement object) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript(
					"arguments[0].setAttribute('style', 'background: #e0ebeb; border: 3px solid blue;');", object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Scrolls down until the object is visible in the web page
	 *
	 * @param object - Selenium WebElement, to be scrolled down to.
	 * @author JM
	 */
	public static void scrollToObject(WebElement object) {
		try {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({behavior: 'smooth',block: 'center', inline: 'nearest'});", object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
