package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import test.testedMethods.DateTools;

public class testDate {

    private DateTools tools;

    /*
     * @subcontract all other cases {
     * 
     * @requires no other accepting precondition;
     * 
     * @ensures \result = false;
     * }
     */
    @Test
    public void testDateToolIfZeroDayValueGivesFalse() {
        // Arrange
        int day = 0;
        int month = 1;
        int year = 2012;
        // Act
        boolean isDate = tools.validateDate(day, month, year);
        // Assert
        assertEquals(false, isDate);
    }

    /*
     * @subcontract all other cases {
     * 
     * @requires no other accepting precondition;
     * 
     * @ensures \result = false;
     * }
     */
    @Test
    public void testDateToolIfNegativeMonthValueGivesFalse() {
        // Arrange
        int day = 1;
        int month = -1;
        int year = 2000;
        // Act
        boolean isDate = tools.validateDate(day, month, year);
        // Assert

        assertEquals(false, isDate);
    }

    /*
     * @subcontract 31 days in month {
     * 
     * @requires (month == 1 || month == 3 || month == 5 || month == 7 ||
     * month == 8 || month == 10 || month == 12) && 1 <= day <= 31;
     * 
     * @ensures \result = true;
     * }
     */
    @Test
    public void testDateToolIfDay31IsAValidDateInJanuary() {

        // Arrange
        int day = 31;
        int month = 1;
        int year = 2000;
        // Act
        boolean isDate = tools.validateDate(day, month, year);
        // Assert
        assertEquals(true, isDate);
    }

    /*
     * @subcontract 31 days in month {
     * 
     * @requires (month == 1 || month == 3 || month == 5 || month == 7 ||
     * month == 8 || month == 10 || month == 12) && 1 <= day <= 31;
     * 
     * @ensures \result = true;
     * }
     */
    @Test
    public void testDateToolIfDay31IsAValidDateInMarch() {
        // Arrange
        int day = 31;
        int month = 3;
        int year = 2000;
        // Act
        boolean isDate = tools.validateDate(day, month, year);
        // Assert
        assertEquals(true, isDate);
    }

    /*
     * @subcontract 31 days in month {
     * 
     * @requires (month == 1 || month == 3 || month == 5 || month == 7 ||
     * month == 8 || month == 10 || month == 12) && 1 <= day <= 31;
     * 
     * @ensures \result = true;
     * }
     */
    @Test
    public void testDateToolIfDay31IsAValidDateInJuly() {
        // Arrange
        int day = 31;
        int month = 7;
        int year = 2000;
        // Act
        boolean isDate = tools.validateDate(day, month, year);
        // Assert
        assertEquals(true, isDate);
    }

    /*
     * @subcontract 31 days in month {
     * 
     * @requires (month == 1 || month == 3 || month == 5 || month == 7 ||
     * month == 8 || month == 10 || month == 12) && 1 <= day <= 31;
     * 
     * @ensures \result = true;
     * }
     */
    @Test
    public void testDateToolIfDay31IsAValidDateInAugust() {
        // Arrange
        int day = 31;
        int month = 8;
        int year = 2000;
        // Act
        boolean isDate = tools.validateDate(day, month, year);
        // Assert
        assertEquals(true, isDate);
    }

    /*
     * @subcontract 30 days in month {
     * 
     * @requires (month == 4 || month == 6 || month == 9 || month == 11) &&
     * 1 <= day <= 30;
     * 
     * @ensures \result = true;
     * }
     */
    @Test
    public void testDateToolsIfDayThirtyIsAValidDateInApril() {
        // Arrange
        int day = 30;
        int month = 4;
        int year = 2000;
        // Act
        boolean isThirtyDays = tools.validateDate(day, month, year);
        // Assert
        assertEquals(true, isThirtyDays);
    }

    /*
     * @subcontract 30 days in month {
     * 
     * @requires (month == 4 || month == 6 || month == 9 || month == 11) &&
     * 1 <= day <= 30;
     * 
     * @ensures \result = true;
     * }
     */
    @Test
    public void testDateToolIfThirthyIsAValidDayInSeptember() {
        // Arrange
        int day = 30;
        int month = 9;
        int year = 2000;
        // Act
        boolean isThirtyDays = tools.validateDate(day, month, year);
        // Assert
        assertEquals(true, isThirtyDays);
    }

    /*
     * @subcontract 30 days in month {
     * 
     * @requires (month == 4 || month == 6 || month == 9 || month == 11) &&
     * 1 <= day <= 30;
     * 
     * @ensures \result = true;
     * }
     */
    @Test
    public void testDateToolIfThirthOneIsNotAValidDayInSeptember() {
        // Arrange
        int day = 31;
        int month = 9;
        int year = 2021;
        // Act
        boolean isValidDate = tools.validateDate(day, month, year);
        // Assert
        assertEquals(false, isValidDate);
    }

    /*
     * @subcontract 29 days in month {
     * 
     * @requires month == 2 && 1 <= day <= 29 &&
     * (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
     * 
     * @ensures \result = true;
     * }
     * 
     * @Test
     */
    public void testDateToolIfDayIsALeapYearIn2000() {
        // Arrange
        int day = 29;
        int month = 2;
        int year = 2000;
        // Act
        boolean isLeapYear = tools.validateDate(day, month, year);
        // Assert
        assertEquals(true, isLeapYear);
    }

    /*
     * @subcontract 29 days in month {
     * 
     * @requires month == 2 && 1 <= day <= 29 &&
     * (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
     * 
     * @ensures \result = true;
     * }
     * 
     * @Test
     */
    @Test
    public void testIfDateToolsIsALeapYearIn2012() {
        int day = 29;
        int month = 2;
        int year = 2012;
        // Act
        boolean isLeapYear = tools.validateDate(day, month, year);
        // Assert
        assertEquals(true, isLeapYear);
    }

    /*
     * @subcontract all other cases {
     * 
     * @requires no other accepting precondition;
     * 
     * @ensures \result = false;
     * }
     */
    @Test
    public void testDateToolsIfDayIsNotALeapYearIn1800() {
        int day = 29;
        int month = 2;
        int year = 1800;
        // Act
        boolean isLeapYear = tools.validateDate(day, month, year);
        // Assert
        assertEquals(false, isLeapYear);
    }

    /*
     * @subcontract all other cases {
     * 
     * @requires no other accepting precondition;
     * 
     * @ensures \result = false;
     * }
     */
    @Test
    public void testDateToolIfDateIsNotALeapYearIn2011() {
        int day = 29;
        int month = 2;
        int year = 2011;
        // Act
        boolean isLeapYear = tools.validateDate(day, month, year);
        // Assert
        assertEquals(false, isLeapYear);
    }

}
