package test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import logic.InputValidation;

public class DateFormattingTest {
    /**
     * @desc formats the input values to a valid localDate
     * 
     * @Subcontract checks if the method puts a 0 before the month if month is below
     *              10
     * @requires Integer.parseInt(month) < 10{
     * @ensures result\ = year +"-"+("0" + Integer.ParseInt(month))+ "-" + (
     *          Integer.parseInt(day))
     *          }
     * 
     * @Subcontract checks if the method puts a 0 before day and month values if
     *              both values are below 10{
     * @requires Integer.parseInt(month) < 10 && Integer.parseInt(day) < 10
     * @ensures result\ = year +"-"+("0" + Integer.ParseInt(month))+ "-" + ("0" +
     *          Integer.parseInt(day) < 10 )
     *          }
     * 
     *          /*
     * 
     * @Subcontract checks the method puts a 0 before the number if the number is
     *              below 10 {
     * @requires Integer.parseInt(day) < 10
     * @ensures result\ = year +"-"+("0" + Integer.ParseInt(month))+ "-" + (
     *          "0" + Integer.parseInt(day))
     *          }
     */
    @Test
    public void testDateFormatIfDayIsBelowTen() {
        // Arrange
        String day = "1";
        String month = "10";
        String year = "2000";
        // Act
        LocalDate result = InputValidation.formatDate(year, month, day);
        // Assert
        assertEquals(LocalDate.parse("2000-10-01"), result);
    }

    /*
     * @Subcontract gives dates without any of the conditions{
     * 
     * @requires month >= 10 || day >= 10
     * 
     * @ensures LocalDate date = LocalDate.parse(year+ "-" + "month" + "-" + day)
     * }
     */
    @Test
    public void testDateFormatIfDayIsABoveTen() {
        // Arrange
        String day = "10";
        String month = "10";
        String year = "2000";
        // Act
        LocalDate result = InputValidation.formatDate(year, month, day);
        // Assert
        assertEquals(LocalDate.parse("2000-10-10"), result);
    }

    /*
     * @Subcontract checks if the method puts a 0 before the month if month is below
     * 10
     * 
     * @requires Integer.parseInt(month) < 10{
     * 
     * @ensures result\ = year +"-"+("0" + Integer.ParseInt(month))+ "-" + (
     * Integer.parseInt(day))
     * }
     */

    @Test
    public void testDateFormatMonthIsBelowTen() {
        // Arrange
        String day = "10";
        String month = "9";
        String year = "2000";
        // Act
        LocalDate result = InputValidation.formatDate(year, month, day);
        // Assert
        assertEquals(LocalDate.parse("2000-09-10"), result);
    }

    /*
     * @Subcontract checks if the method puts a 0 before day and month values if
     * both values are below 10{
     * 
     * @requires Integer.parseInt(month) < 10 && Integer.parseInt(day) < 10
     * 
     * @ensures result\ = year +"-"+("0" + Integer.ParseInt(month))+ "-" + ("0" +
     * Integer.parseInt(day) < 10 )
     * }
     */
    @Test
    public void testDateFormatIfDayAndMonthAreBelowTen() {
        // Arrange
        String day = "1";
        String month = "1";
        String year = "2000";
        // Act
        LocalDate result = InputValidation.formatDate(year, month, day);
        // Assert
        assertEquals(LocalDate.parse("2000-01-01"), result);
    }

    /*
     * @Subcontract checks if year value is below 1000{
     * 
     * @requires Integer.parseInt(year) < 1000
     * 
     * @ensures result = "0" + Integer.parseInt(year) + Integer.parseInt(month) +
     * Integer.parseInt(day)
     * }
     */
    @Test
    public void testDateFormatIfYearIsBelowOneThousand() {
        // Arrange
        String day = "1";
        String month = "10";
        String year = "999";
        // Act
        LocalDate result = InputValidation.formatDate(year, month, day);
        // Assert
        assertEquals(LocalDate.parse("0999-10-01"), result);
    }

    /*
     * @Subcontract checks if all input values not are decimal{
     * 
     * @requires !(day.matches("\\+d")) || !(month.matches("\\+d")) ||
     * !(year.matches("\\+d")) ;
     * 
     * @Signals (NumberFormatException) }
     */
    @Test(expected = NumberFormatException.class)
    public void testDateFormatInputValuesAreDecimals() {
        // Arrange
        String day = "sad";
        String month = "smiley";
        String year = "year";
        // Act
        LocalDate result = InputValidation.formatDate(year, month, day);
        // Assert
        // NumberFormatException
    }
}