package stepDefinitions;

import org.openqa.selenium.WebDriver;

import com.loop.framework.core.DataHandle;
import com.loop.framework.core.Loop;

import io.cucumber.java.en.Given;

public class LoginSteps {

	WebDriver driver;

	@Given("I successfully load the case data {string}")
	public void i_successfully_load_case_data(String caseID) {
		DataHandle.loadTestData(caseID);
		DataHandle.setCurrentCaseID(caseID);
		Loop.reporter.startTest("Test Case: " + caseID);
	}
}