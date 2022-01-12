package Test.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import logic.InputValidation;

public class TestAreNumbers {
    /**
     * @desc checks if input values are decimals
     * 
     * @subcontract value is negative{
     * @requires value < 0
     * @ensures result = false}
     * 
     * @subcontract value is not an decimal{
     * @requires value != [0-9]
     * @ensures \result = false}
     * 
     * @subcontract value is a decimal{
     * @requires value >= 0
     * @ensures \result = true}
     */

    @Test
    public void testAreNumbersAreDecimals() {
        // Arrange
        String number = "0";
        // Act
        boolean result = InputValidation
                .areNumbers(number);
        // Assert
        assertEquals(true, result);
    }

    @Test
    public void testAreNumbersAreNotDecimals() {
        // Arrange
        String number = "e";
        // Act
        boolean result = InputValidation
                .areNumbers(number);
        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testAreNumbersAreDecimalsNumbersAreNegative() {
        // Arrange
        String number = "-1";
        // Act
        boolean result = InputValidation
                .areNumbers(number);
        // Assert
        assertEquals(false, result);
    }
}
