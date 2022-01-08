package test.testedMethods;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.zip.DataFormatException;

public class DateTools {
    /**
     * @desc Validates is a given date in the form of day, month and year is valid.
     * 
     * 
     * @subcontract 31 days in month {
     * @requires (month == 1 || month == 3 || month == 5 || month == 7 ||
     *           month == 8 || month == 10 || month == 12) && 1 <= day <= 31;
     * @ensures \result = true;
     *          }
     * 
     * @subcontract 30 days in month {
     * @requires (month == 4 || month == 6 || month == 9 || month == 11) &&
     *           1 <= day <= 30;
     * @ensures \result = true;
     *          }
     * 
     * 
     * @subcontract 29 days in month {
     * @requires month == 2 && 1 <= day <= 29 &&
     *           (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
     * @ensures \result = true;
     *          }
     * 
     * @subcontract 28 days in month {
     * @requires month == 2 && 1 <= day <= 28 &&
     *           (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0));
     * @ensures \result = true;
     *          }
     * 
     * @subcontract all other cases {
     * @requires no other accepting precondition;
     * @ensures \result = false;
     *          }
     * 
     */

    public static void main(String[] args) {
        System.out.println(validateDate(29, 2, 1800));
    }

    public static boolean validateDate(int day, int month, int year) {
        try {
            LocalDate date = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }
}