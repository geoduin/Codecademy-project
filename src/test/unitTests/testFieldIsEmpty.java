package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import logic.*;

public class testFieldIsEmpty {

    /**
     * @desc this methods checks if inputfield is empty
     * 
     * @subcontract input field is not empty{
     * @requires at least one character present except exclusively white space
     * @ensures \result = true
     *          }
     * 
     * @subcontract input field is empty
     * @requires no characters or only whitespace
     * @ensures \ result = false
     */
    @Test
    public void testFieldIsNotEmptyIfAnyCharactersArePresent() {
        // Arrange
        String testTxt = " w";
        // Act
        boolean isNotEmpty = InputValidations.fieldIsNotEmpty(testTxt);
        // Assert
        assertEquals(true, isNotEmpty);
    }

    @Test
    public void testFieldIsNotEmptyIfNoCharactersArePresent() {
        // Arrange
        String testTxt = "";
        // Act
        boolean isEmpty = InputValidations.fieldIsNotEmpty(testTxt);
        // Assert
        assertEquals(false, isEmpty);
    }

    @Test
    public void testFieldIsNotEmptyIfOnlyWhiteSpaceArePresent() {
        // Arrange
        String testTxt = "                              ";
        // Act
        boolean isEmpty = InputValidations.fieldIsNotEmpty(testTxt);
        // Assert
        assertEquals(false, isEmpty);
    }
}
