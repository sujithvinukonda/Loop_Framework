package utilities;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UtilsMethods {

	/**
	 * This method select the option by exposed text.
	 *
	 * @param SelectElement dropdown web element.
	 * @param Value         the visible text to select.
	 */
	public static boolean selectDropDownListByText(WebElement SelectElement, String Value) {
		try {
			Select selectOccupationStatus = new Select(SelectElement);
			selectOccupationStatus.selectByVisibleText(Value);
			return true;
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method select from the dropdown element the option with the inserted
	 * value.
	 *
	 * @param SelectElement the dropdown list web element.
	 * @param Value         value of the option to be searched for.
	 */
	public static boolean selectDropDownListByValue(WebElement SelectElement, String Value) {
		try {
			Select selectOccupationStatus = new Select(SelectElement);
			selectOccupationStatus.selectByValue(Value);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * this method choose a random value from the selected dropdown element
	 *
	 * @param SelectElement the selected dropdown element
	 */
	public static boolean selectRandomDropDownListByValue(WebElement SelectElement) {
		try {
			Select selectOccupationStatus = new Select(SelectElement);
			int selectedOption = new Random().nextInt(selectOccupationStatus.getOptions().size() - 1);
			selectedOption = selectedOption == 0 ? 1 : selectedOption;
			selectOccupationStatus.selectByIndex(selectedOption);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method select from the dropdown element the option with the partial text
	 * value.
	 *
	 * @param SelectElement     the dropdown list web element.
	 * @param FirstPartialText  First partial text of the dropdown value.
	 * @param SecondPartialText Second partial text of the dropdown value.
	 */
	public static boolean selectDropDownListFromPartialText(WebElement SelectElement, String FirstPartialText,
			String SecondPartialText) {
		try {
			boolean optionFind = false;
			Select selectElement = new Select(SelectElement);
			for (WebElement option : selectElement.getOptions()) {
				if (option.getText().contains(FirstPartialText) && option.getText().contains(SecondPartialText)) {
					String value = option.getAttribute("value");
					selectElement.selectByValue(value);
					optionFind = true;
				}
			}
			return optionFind;
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method select from the dropdown element the option with the partial text
	 * value.
	 *
	 * @param SelectElement the dropdown list web element.
	 * @return the complete text of the option selected
	 */
	public static String getOptionSelectedText(WebElement SelectElement) {
		try {
			Select selectElement = new Select(SelectElement);
			return selectElement.getFirstSelectedOption().getText();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * this method generate a random number.
	 *
	 * @param NumDigits the amount of digits that should contain the random number.
	 * @return the random number generated.
	 */
	public static String generateRandomNumber(int NumDigits) {
		try {
			return IntStream.range(0, NumDigits).mapToObj(i -> String.valueOf(new Random().nextInt(9)))
					.collect(Collectors.joining());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
			return null;
		}
	}
}
