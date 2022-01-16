package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import logic.InputValidation;

public class PostalCodeTest {
    /**
     * @subcontract null postalCode {
     * @requires postalCode == null;
     * @signals (NullPointerException) postalCode == null;
     *          }
     * 
     */
    @Test(expected = NullPointerException.class)
    public void formatPostalCodeNullPointerTest() {
        InputValidation.formatPostalCode(null);
    }

    /**
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
     */

     //as there can be no spaces in a method name, the _ is used as a substitude

    @Test
    public void postalCode1234_ABEnsuresIsValidPostalCode() {
        // Arrange
        String test = "1234 AB";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        // Assert
        assertEquals(test, formattedPostalCode);
    }

    @Test
    public void postalCode1000_ZZEnsuresIsValidPostalCode() {
        // Arrange
        String test = "1000 ZZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        // Assert
        assertEquals(test, formattedPostalCode);
    }

    @Test
    public void postalCode5000_ZZEnsuresIsValidPostalCode() {
        // Arrange
        String test = "5000 ZZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        // Assert
        assertEquals(test, formattedPostalCode);
    }

    @Test
    public void postalCode9999_ZZEnsuresIsValidPostalCode() {
        // Arrange
        String test = "9999 ZZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        int number = Integer.valueOf(formattedPostalCode.substring(0, 4).trim());
        // Assert
        assertEquals(9999, number);
    }

    @Test
    public void postalCode1000_AAEnsuresPostalCodeContainsTwoLetters() {
        // Arrange
        String test = "1000 AA";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        int length = formattedPostalCode.substring(4).trim().length();
        // Assert
        assertEquals(2, length);
    }

    public void postalCode1000_ZZEnsuresPostalCodeContainsTwoLetters() {
        // Arrange
        String test = "1000 ZZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        int length = formattedPostalCode.substring(4).trim().length();
        // Assert
        assertEquals(2, length);
    }

    @Test
    public void postalCode1000_aZEnsuresLetterIsCapitalized() {
        // Arrange
        String test = "1000 aZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        char letter = formattedPostalCode.substring(4).trim().charAt(0);
        // Assert
        assertEquals('A', letter);
    }

    @Test
    public void postalCode1000_abEnsuresLetterIsCapitalized() {
        // Arrange
        String test = "1000 ab";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        char letter = formattedPostalCode.substring(4).trim().charAt(1);
        // Assert
        assertEquals('B', letter);
    }

    @Test
    public void postalCode1000_ZZEnsuresLetterIsCapitalized() {
        // Arrange
        String test = "1000 ZZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        char letter = formattedPostalCode.substring(4).trim().charAt(0);
        // Assert
        assertEquals('Z', letter);
    }

    @Test
    public void postalCode1000_aGEnsuresLetterIsCapitalized() {
        // Arrange
        String test = "1000 aG";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        char letter = formattedPostalCode.substring(4).trim().charAt(1);
        // Assert
        assertEquals('G', letter);
    }


    /**
     * @subcontract invalid postalCode {
     * @requires no other valid precondition;
     * @signals (IllegalArgumentException);
     *          }
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void givenStringIsgabgoujsabdgiuadbgiuadbjadbgEnsuresIllegalArgumentException() {
        InputValidation.formatPostalCode("gabgoujsabdgiuadbgiuadbjadbg");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenStringIsAZ_4500EnsuresIllegalArgumentException() {
        InputValidation.formatPostalCode("AZ 4500");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenStringIs999AzEnsuresIllegalArgumentException() {
        InputValidation.formatPostalCode("999Az");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenStringIs10000abEnsuresIllegalArgumentException() {
        InputValidation.formatPostalCode("10000ab");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenStringIs0123aaEnsuresIllegalArgumentException() {
        InputValidation.formatPostalCode("0123aa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenStringIs0999_a1EnsuresIllegalArgumentException() {
        InputValidation.formatPostalCode("0999 a1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenStringIs1000_aaaEnsuresIllegalArgumentException() {
        InputValidation.formatPostalCode("1000 aaa");
    }

}
