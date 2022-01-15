package Test.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import logic.InputValidations;

public class TestPostalcodeValidation {
    /**
     * @desc checks if postalcode input is valid
     * 
     * @subcontract postalcode has correct format
     * @beforehand postalcode input is going through the formatPostalCode method to
     *             check if the postal is correct and will give a postalcode format
     *             back
     * @requires formatPostalcode has returned correct format
     * @beforehand postalcode input is going through the formatPostalCode method to
     *             check if the postal is correct and will give a postalcode format
     *             back
     * @ensures \result = true
     * 
     * @subcontract postalcode input was not correct
     * @beforehand postalcode input is going through the formatPostalCode method to
     *             check if the postal is correct and will give a postalcode format
     *             back
     * @requires input did not validate to the formatPostalcode
     * @ensures \result = false
     */

    @Test
    public void testPostalCodeFormatCorrect() {
        // Arrange
        String code = "3000NM";

        // Act
        boolean result = InputValidations.postalCodeHasTheRightFormat(code);
        // Asserts
        assertEquals(true, result);
    }

    @Test
    public void testPostalCodeFormatNoLetters() {
        // Arrange
        String code = "3000";

        // Act
        boolean result = InputValidations.postalCodeHasTheRightFormat(code);
        // Asserts
        assertEquals(false, result);
    }

    @Test
    public void testPostalCodeFormatInsufficientDigits() {
        // Arrange
        String code = "300NN";

        // Act
        boolean result = InputValidations.postalCodeHasTheRightFormat(code);
        // Asserts
        assertEquals(false, result);
    }

    @Test
    public void testPostalCodeFormatNoDigits() {
        // Arrange
        String code = "NN";

        // Act
        boolean result = InputValidations.postalCodeHasTheRightFormat(code);
        // Asserts
        assertEquals(false, result);
    }
}