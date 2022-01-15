package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import logic.InputValidation;

public class DateIsNotEarlierTest {

    /**
     * @desc checks if input date is earlier or equal to today
     * @beforehand day, month and year will be first formatted through the
     *             formatDate method
     * 
     * 
     *             /*
     * @subcontract checks if the day tomorrow gives false
     * 
     * @requires LocalDate.now().plusDays(1)
     * 
     * @ensures \result = false
     */
    @Test
    public void testDateIsEarlierThenNowIfDateIsTommorow() {

        // Arrange
        LocalDate date = LocalDate.now().plusDays(1);
        String day = Integer.toString(date.getDayOfMonth());
        String month = Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        // Act
        boolean result = InputValidation.dateIsEarlierThanNow(day, month, year);
        // Assert
        assertEquals(false, result);
    }

    /*
     * @subcontract checks if day earlier then today
     * 
     * @requires LocalDate.now().minusDays(1)
     * 
     * @ensures \result = true
     */
    @Test
    public void testDateIsEarlierThenNow() {

        // Arrange
        LocalDate date = LocalDate.now();
        String day = Integer.toString(date.getDayOfMonth());
        String month = Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        // Act
        boolean result = InputValidation.dateIsEarlierThanNow(day, month, year);
        // Assert
        assertEquals(false, result);
    }

    /*
     * @subcontract checks if day earlier then today
     * 
     * @requires LocalDate.now().minusDays(1)
     * 
     * @ensures \result = true
     */
    @Test
    public void testDateIsEarlierThenNowIfDateIsYesterday() {

        // Arrange
        LocalDate date = LocalDate.now().minusDays(1);
        String day = Integer.toString(date.getDayOfMonth());
        String month = Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        // Act
        boolean result = InputValidation.dateIsEarlierThanNow(day, month, year);
        // Assert
        assertEquals(true, result);
    }

}