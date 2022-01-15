package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import logic.InputValidation;

public class ValidGradeTest {
    /**
     * 
     * 
     * @desc this method will check if grade is within valid range(1-10)
     * @subcontract value within valid range
     * @requires value <= 10 && value > 0
     * @ensures \result = true
     */
    @Test
    public void testIfValidGradeGivesTrueWithGradeOne() {
        // Arrange
        int grade = 1;
        // Act
        boolean result = InputValidation.isValidGrade(grade);
        // Assert
        assertEquals(true, result);
    }

    /**
     * @subcontract value within valid range
     * @requires value <= 10 && value > 0
     * @ensures \result = true
     */
    @Test
    public void testIfValidGradeGivesTrueWithGradeTen() {
        // Arrange
        int grade = 10;
        // Act
        boolean result = InputValidation.isValidGrade(grade);
        // Assert
        assertEquals(true, result);
    }

    /**
     * @subcontract value within valid range
     * @requires value <= 10 && value > 0
     * @ensures \result = true
     */
    @Test
    public void testIfValidGradeGivesTrueWithGradeFive() {
        // Arrange
        int grade = 5;
        // Act
        boolean result = InputValidation.isValidGrade(grade);
        // Assert
        assertEquals(true, result);
    }

    /**
     * @subcontract value within valid range
     * @requires value == 0
     * @ensures \result = false
     */
    @Test
    public void testIfValidGradeGivesFalseIfGradeIsZero() {
        // Arrange
        int grade = 0;
        // Act
        boolean result = InputValidation.isValidGrade(grade);
        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract value within valid range
     * @requires value > 10
     * @ensures \result = false
     */
    @Test
    public void testIfValidGradeGivesFalseIfGradeIsTooHigh() {
        // Arrange
        int grade = 11;
        // Act
        boolean result = InputValidation.isValidGrade(grade);
        // Assert
        assertEquals(false, result);
    }
}
