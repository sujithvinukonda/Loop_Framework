package pageObjects;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.loop.framework.core.DataHandle;
import com.loop.framework.core.Loop;
import com.loop.framework.core.PageObjectBase;

import utilities.ElementManagement;

public class AutomationTestingPractice extends PageObjectBase {

	public AutomationTestingPractice(WebDriver driver) {
		super(driver);
	}

	@FindBy(how = How.CSS, using = "input#name")
	private WebElement txt_Name;

	@FindBy(how = How.CSS, using = "input#email")
	private WebElement txt_Email;

	@FindBy(how = How.CSS, using = "input#phone")
	private WebElement txt_Phone;

	// @FindBy(css = "input#name")
	// WebElement txt_Name;

	public void clickName() {
		try {
			assertTrue("Verifying the element present in search Results Screen :: ",
					ElementManagement.isElementPresent(txt_Name));
			// Actions actions = new Actions(driver);
			// actions.moveToElement(txt_Name).click().build().perform();
			txt_Name.click();
			Loop.reporter.methodPassed();
		} catch (Exception e) {
			System.err.println("Error while clicking Web Inputs." + e.getMessage());
			// e.printStackTrace();
		}
	}

	/**
	 * This method is to click Web Inputs
	 */

	public void enterName() {
		try {
			assertTrue("Verifying the name element present in search Results Screen :: ",
					ElementManagement.isElementPresent(txt_Name));
			String name = DataHandle.readFromDataSheet("Name");
			System.out.println(name);
			txt_Name.sendKeys(name);
			Loop.reporter.methodPassed();
		} catch (Exception e) {
			System.err.println("Error while entering Input number");
			e.printStackTrace();
		}
	}

	/**
	 * This method is to click Web Inputs
	 */

	public void enterEmail() {
		try {
			assertTrue("Verifying the Email element present in Screen :: ",
					ElementManagement.isElementPresent(txt_Email));
			String email = DataHandle.readFromDataSheet("Email");
			System.out.println(email);
			txt_Email.sendKeys(email);
			Loop.reporter.methodPassed();
		} catch (Exception e) {
			System.err.println("Error while entering Email");
			e.printStackTrace();
		}
	}

	/**
	 * This method is to click Web Inputs
	 */

	public void enterPhone() {
		try {
			assertTrue("Verifying the phone element present in Screen :: ",
					ElementManagement.isElementPresent(txt_Phone));
			String phone = DataHandle.readFromDataSheet("Phonenumber");
			System.out.println(phone);
			txt_Phone.sendKeys(phone);
			Loop.reporter.methodPassed();
		} catch (Exception | AssertionError e) {
			Loop.reporter.methodFailed("Error while entering phone number ", e);

		}
	}

}
