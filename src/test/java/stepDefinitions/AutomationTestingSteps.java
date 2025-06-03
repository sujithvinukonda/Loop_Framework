package stepDefinitions;

import org.openqa.selenium.WebDriver;

import com.loop.framework.core.PageObjectBase;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.AutomationTestingPractice;

public class AutomationTestingSteps {

	WebDriver driver = PageObjectBase.getDriver();
	AutomationTestingPractice ATP = new AutomationTestingPractice(driver);

	@When("I click the name field")
	public void click_name() {
		ATP.clickName();
	}

	@Then("I enter details")
	public void enter_name() {
		ATP.enterName();
		ATP.enterEmail();
		ATP.enterPhone();
	}

}
