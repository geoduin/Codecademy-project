package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import logic.InputValidation;

public class DateOfBirthTest {
    /**
     * @desc checks if everything with date of birth is correct
     * @subcontract day, month and year values meet the requirements
     * @requires day, month and year fields are filled && day, month and year are
     *           digits && day, month and year are valid dates && day, month and
     *           year are equal or earlier then today
     * 
     * @ensures \result = true
     */
    @Test
    public void testDateOfBirthIsValid() {
        // Arrange
        LocalDate date = LocalDate.now();
        String day = Integer.toString(date.getDayOfMonth());
        String month = Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        // Act
        boolean result = InputValidation.dateOfBirthIsValid(day, month, year);
        // Assert
        assertEquals(true, result);
    }

    /**
     * @subcontract checks if one of the requirements are not met
     * @requires day, month and year fields are not filled || day, month and year
     *           not
     *           digits || day, month and year are not valid dates || day, month and
     *           year later then today
     * @ensures \result = false
     */
    @Test
    public void testDateOfBirthIsValidIfdateIsLater() {
        // Arrange
        LocalDate date = LocalDate.now().plusDays(1);
        String day = Integer.toString(date.getDayOfMonth());
        String month = Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        // Act
        boolean result = InputValidation.dateOfBirthIsValid(day, month, year);
        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract checks if one of the requirements are not met
     * @requires day, month and year fields are not filled || day, month and year
     *           not
     *           digits || day, month and year are not valid dates || day, month and
     *           year later then today
     * @ensures \result = false
     */
    @Test
    public void testDateOfBirthIsValidIfdayIsNotFilledIn() {
        // Arrange
        LocalDate date = LocalDate.now().plusDays(1);
        String day = "";
        String month = Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        // Act
        boolean result = InputValidation.dateIsEarlierThanNow(day, month, year);
        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract checks if one of the requirements are not met
     * @requires day, month and year fields are not filled || day, month and year
     *           not
     *           digits || day, month and year are not valid dates || day, month and
     *           year later then today
     * @ensures \result = false
     */
    @Test
    public void testDateOfBirthIsValidIfDateIsNotValid() {
        // Arrange
        LocalDate date = LocalDate.now().plusDays(1);
        String day = "29";
        String month = "2";
        String year = "1800";
        // Act
        boolean result = InputValidation.dateIsEarlierThanNow(day, month, year);
        // Assert
        assertEquals(false, result);
    }

}