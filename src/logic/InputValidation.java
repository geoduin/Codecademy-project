package logic;

import java.time.LocalDate;

public class InputValidation {

    // Checks if the date is valid.
    // It also accounts for leapyears.
    public static boolean validateDate(int day, int month, int year) {
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                && (day >= 1 && day <= 31)) {
            return true;
        } else if ((month == 4 || month == 6 || month == 9 || month == 11) && (day >= 1 && day <= 30)) {
            return true;
        } else if ((month == 2) && (day >= 1 && day <= 29) && year % 4 == 0 && (year % 400 == 0)) {
            return true;
        } else if ((month == 2) && (day >= 1 && day <= 29) && (year % 4 == 0 && year % 100 != 0)) {
            return true;
        } else if ((month == 2) && (day >= 1 && day <= 28)) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if the email has at least the following conditions. <At least 1
    // character>@<at least 1 character>.<at least 1 character>
    public static boolean validateMailAddress(String mailAddress) {
        return mailAddress.toLowerCase().matches("^[a-z0-9._%+-]+@[a-z0-9]+\\.[^\\.]+");
    }

    // Check whether the int given as parameter is between 0 and 100
    public static boolean isValidPercentage(int percentage) {
        return (percentage >= 0 && percentage <= 100);
    }

    // Formats the postalcode
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
            // returning the formatted postal code
            return postalCode.substring(0, 4) + " " + postalCode.substring(4).toUpperCase().trim();

        }
        // throws IllegalArgumentException if the String does not match the basic Dutch
        // postal code format.
        throw new IllegalArgumentException();
    }

    // This method checks if the text is not blank. If the text is not blank, then
    // it will give a true value, if the text is blank then it will give false back
    public static boolean fieldIsNotEmpty(String textFromField) {
        return !(textFromField.isBlank());
    }

    // Checks if the given string is a number
    public static boolean areNumbers(String numberValue) {
        return (numberValue.matches("\\d+"));
    }

    // First it checks if inputDate is valid. If not, false. Otherwise it will then
    // check if date is before today and will give true if succeed.
    public static boolean dateIsEarlierThanNow(String day, String month, String year) {
        LocalDate inputDate = null;
        try {
            inputDate = formatDate(year, month, day);
        } catch (NumberFormatException e) {
            return false;
        }
        return inputDate.isBefore(LocalDate.now());
    }

    // Method tries to make a postalcode. If it succeeds, it will give true.
    // Otherwise it will give an exception and will give false.
    public static boolean postalCodeHasTheRightFormat(String postalCode) {
        try {
            String post = formatPostalCode(postalCode);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    // Formats the date to yyyy-mm-dd. If one of the digits are below ten, it will
    // put a 0 before the digit and put it in the LocalDateParse
    public static LocalDate formatDate(String year, String month, String day) {
        if (!(year.matches("//d") || !month.matches("//d") || !day.matches("//d"))) {
            throw new NumberFormatException();
        }
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if (Integer.parseInt(year) < 1000) {
            year = "0" + year;
        }
        return LocalDate.parse(year + "-" + month + "-" + day);
    }

    // Checks if all the requirements of address is met. All the fields must be
    // filled, HouseNr are numbers and postalcode has the right postalcode format
    // If one the booleans value are not met, this method returns false. Otherwise
    // it will return true.
    public static boolean addressIsValid(String street, String houseNr, String postalCode) {
        boolean addressIsFilled = (fieldIsNotEmpty(street) && fieldIsNotEmpty(postalCode) && fieldIsNotEmpty(houseNr));
        boolean houseNumberIsNumber = areNumbers(houseNr);
        boolean postalCodeIsRight = postalCodeHasTheRightFormat(postalCode);
        return (addressIsFilled && houseNumberIsNumber && postalCodeIsRight);
    }

    // Checks if al conditions of date of birth are met. all fields are filled, date
    // cannot be today, it has a valid date
    // If one of the conditions are not met, it will return false. Otherwise it will
    // return true
    public static boolean dateOfBirthIsValid(/* Not null */String day, /* Not null */String month,
            /* Not null */String year) {
        boolean valuesAreNumbers = areNumbers(day) && areNumbers(month) && areNumbers(year);
        boolean dateIsNotNow = dateIsEarlierThanNow(day, month, year);
        boolean dateIsCorrect = validateDate(Integer.parseInt(day), Integer.parseInt(month),
                Integer.parseInt(year));
        boolean dateIsFilled = fieldIsNotEmpty(day) && fieldIsNotEmpty(month) && fieldIsNotEmpty(year);
        return (valuesAreNumbers && dateIsNotNow && dateIsCorrect && dateIsFilled);
    }

    // Checks if the given string is a valid URL format.
    // Regex retrieved from
    // https://learningprogramming.net/java/advanced-java/validate-url-address-with-regular-expression-in-java/
    public static boolean isValidURL(String url) {
        return url.matches("^(https|http)://([a-z]|[A-Z])+\\.([a-z]|[A-Z])+\\.(.)+");
    }

    // Check if the given is is in between 1 and 10
    public static boolean isValidGrade(int grade) {
        return (grade >= 1 && grade <= 10);
    }
}
