package Test.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import logic.InputValidations;

public class TestDateIsEarlierThenNow {

    /**
     * @desc checks if input date is earlier or equal to today
     * @beforehand day, month and year will be first formatted through the
     *             formatDate method
     * @subcontract checks if the day tomorrow gives false
     * @requires LocalDate.now().plusDays(1)
     * @ensures \result = false
     * 
     * @subcontract checks if day earlier then today or is today will pass
     * @requires LocalDate.now() or LocalDate.now().minusDays(1)
     * @ensures \result = true
     */
    @Test
    public void testDateIsEarlierThenNowIfDateIsTommorow() {

        // Arrange
        LocalDate date = LocalDate.now().plusDays(1);
        String day = Integer.toString(date.getDayOfMonth());
        String month = Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        // Act
        boolean result = InputValidations.dateIsEarlierThanNow(day, month, year);
        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testDateIsEarlierThenNow() {

        // Arrange
        LocalDate date = LocalDate.now();
        String day = Integer.toString(date.getDayOfMonth());
        String month = Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        // Act
        boolean result = InputValidations.dateIsEarlierThanNow(day, month, year);
        // Assert
        assertEquals(true, result);
    }

    @Test
    public void testDateIsEarlierThenNowIfDateIsYesterday() {

        // Arrange
        LocalDate date = LocalDate.now().minusDays(1);
        String day = Integer.toString(date.getDayOfMonth());
        String month = Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        // Act
        boolean result = InputValidations.dateIsEarlierThanNow(day, month, year);
        // Assert
        assertEquals(true, result);
    }

    @Test
    public void testDateIsEarlierThenNowIfDateIsNotLeapYear() {

        // Arrange
        String day = "29";
        String month = "2";
        String year = "1800";
        // Act
        boolean result = InputValidations.dateIsEarlierThanNow(day, month, year);
        // Assert
        assertEquals(false, result);
    }

}
