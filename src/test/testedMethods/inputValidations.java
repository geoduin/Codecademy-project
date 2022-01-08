package test.testedMethods;

import java.time.DateTimeException;
import java.time.LocalDate;

public class inputValidations {
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

    public static boolean validateDate(int day, int month, int year) {
        try {
            LocalDate date = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }

    public static boolean validateMailAddress(String mailAddress) {
        return mailAddress.toLowerCase().matches("^[a-z0-9._%+-]+@[a-z0-9]+\\.[^\\.]+");
    }

    public static boolean isValidPercentage(int percentage) {

        return (percentage >= 0 && percentage <= 100);

    }

    /**
     * @desc Formats the input postal code to a uniform output in the form
     *       nnnn<space>MM, where nnnn is numeric and > 999 and MM are 2 capital
     *       letters.
     *       Spaces before and after the input string are trimmed.
     * 
     * @subcontract null postalCode {
     * @requires postalCode == null;
     * @signals (NullPointerException) postalCode == null;
     *          }
     * 
     * @subcontract valid postalCode {
     * @requires Integer.valueOf(postalCode.trim().substring(0, 4)) > 999 &&
     *           Integer.valueOf(postalCode.trim().substring(0, 4)) <= 9999 &&
     *           postalCode.trim().substring(4).trim().length == 2 &&
     *           'A' <=
     *           postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <=
     *           'Z' &&
     *           'A' <=
     *           postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <=
     *           'Z';
     * @ensures \result = postalCode.trim().substring(0, 4) + " " +
     *          postalCode.trim().substring(4).trim().toUpperCase()
     *          }
     * 
     * @subcontract invalid postalCode {
     * @requires no other valid precondition;
     * @signals (IllegalArgumentException);
     *          }
     * 
     */

    public static String formatPostalCode(/* non_null */ String postalCode) {
        // Throws exception if postal code is null
        if (postalCode == null) {
            throw new NullPointerException();
        }

        // trims spaces before and after postal code
        postalCode = postalCode.trim();

        // Checks if the given string matches the regular expression for Dutch postal
        // codes.
        // Regular expression found at:
        // https://gist.github.com/jamesbar2/1c677c22df8f21e869cca7e439fc3f5b
        // The regular expression was modified to allow for an infinite number of spaces
        // between the numbers of the postal code and the letters of the postal code and
        // to not accept 0 as the first digit.
        if (postalCode.matches("^[1-9][0-9]{3}\\s*[A-Za-z]{2}$")) {
            // returning the formated postal code
            return postalCode.substring(0, 4) + " " + postalCode.substring(4).toUpperCase().trim();

        }
        // throws IllegalArgumentException if the String does not match the basic Dutch
        // postal code format.
        throw new IllegalArgumentException();

    }

}
