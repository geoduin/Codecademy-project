package Test.UnitTests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import logic.InputValidations;

public class NumericRangeToolsTest {
    /**
     * @subcontract value within valid range {
     * @requires 0 <= percentage <= 100;
     * @ensures \result = true; }
     * 
     * @subcontract value out of range low {
     * @requires percentage < 0;
     * @ensures \result = false;}
     * 
     * @subcontract value out of range high {
     * @requires percentage > 100;
     * @signals \result = false; }
     */

    /*
     * @subcontract value within valid range {
     * 
     * @requires 0 <= percentage <= 100;
     * 
     * @ensures \result = true; }
     */
    @Test
    void testFormatIntRequires0EnsuresTrue() {
        // Arrange
        int input = 0;
        // Act
        boolean result = InputValidations.isValidPercentage(input);
        // Assert
        Assert.assertEquals(true, result);
    }

    @Test
    void testFormatIntRequires100EnsuresTrue() {
        // Arrange
        int input = 100;
        // Act
        boolean result = InputValidations.isValidPercentage(input);
        // Assert
        Assert.assertEquals(true, result);
    }

    /*
     * @subcontract value out of range low {
     * 
     * @requires percentage < 0;
     * 
     * @ensures \result = false;}
     */
    @Test
    void testFormatIntRequiresMinus1EnsuresFalse() {
        // Arrange
        int input = -1;
        // Act
        boolean result = InputValidations.isValidPercentage(input);
        // Assert
        Assert.assertEquals(false, result);
    }

    /*
     * @subcontract value out of range high {
     * 
     * @requires percentage > 100;
     * 
     * @signals \result = false; }
     */
    @Test
    void testFormatIntRequires101EnsuresFalse() {
        // Arrange
        int input = 101;
        // Act
        boolean result = InputValidations.isValidPercentage(input);
        // Assert
        Assert.assertEquals(false, result);
    }
}