package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import logic.*;

public class FieldIsNotEmptyTest {

    /**
     * @desc this methods checks if inputfield is empty
     */
    /**
     * @subcontract input field is not empty{
     * @requires at least one character present except exclusively white space
     * @ensures \result = true
     *          }
     */
    @Test
    public void testFieldIsNotEmptyIfAnyCharactersArePresent() {
        // Arrange
        String testTxt = " w";
        // Act
        boolean isNotEmpty = InputValidation.fieldIsNotEmpty(testTxt);
        // Assert
        assertEquals(true, isNotEmpty);
    }

    /*
     * @subcontract input field is empty
     * 
     * @requires no characters or only whitespace
     * 
     * @ensures \ result = false
     */
    @Test
    public void testFieldIsNotEmptyIfNoCharactersArePresent() {
        // Arrange
        String testTxt = "";
        // Act
        boolean isEmpty = InputValidation.fieldIsNotEmpty(testTxt);
        // Assert
        assertEquals(false, isEmpty);
    }

    /*
     * @subcontract input field is empty
     * 
     * @requires no characters or only whitespace
     * 
     * @ensures \ result = false
     */
    @Test
    public void testFieldIsNotEmptyIfOnlyWhiteSpaceArePresent() {
        // Arrange
        String testTxt = "                              ";
        // Act
        boolean isEmpty = InputValidation.fieldIsNotEmpty(testTxt);
        // Assert
        assertEquals(false, isEmpty);
    }
}