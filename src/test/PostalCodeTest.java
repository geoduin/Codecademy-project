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

    @Test
    public void returnedPostalCodeNumberIsBiggerThan999Test() {
        // Arrange
        String test = "1234 AB";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        // Assert
        assertEquals("1234 AB", formattedPostalCode);
    }

    @Test
    public void acceptsNumbersBetween999And9999Test1() {
        // Arrange
        String test = "1000 ZZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        int number = Integer.valueOf(formattedPostalCode.substring(0, 4).trim());
        // Assert
        assertEquals(1000, number);
    }

    @Test
    public void acceptsNumbersBetween999And9999Test2() {
        // Arrange
        String test = "5000 ZZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        int number = Integer.valueOf(formattedPostalCode.substring(0, 4).trim());
        // Assert
        assertEquals(5000, number);
    }

    @Test
    public void acceptsNumbersBetween999And9999Test3() {
        // Arrange
        String test = "9999 ZZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        int number = Integer.valueOf(formattedPostalCode.substring(0, 4).trim());
        // Assert
        assertEquals(9999, number);
    }

    @Test
    public void letterPartOfPostalCodeIs2LettersLongTest1() {
        // Arrange
        String test = "1000 AA";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        int length = formattedPostalCode.substring(4).trim().length();
        // Assert
        assertEquals(2, length);
    }

    @Test
    public void returnsRightLetterTest1() {
        // Arrange
        String test = "1000 aZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        char letter = formattedPostalCode.substring(4).trim().charAt(0);
        // Assert
        assertEquals('A', letter);
    }

    @Test
    public void returnsRightLetterTest2() {
        // Arrange
        String test = "1000 ab";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        char letter = formattedPostalCode.substring(4).trim().charAt(1);
        // Assert
        assertEquals('B', letter);
    }

    @Test
    public void returnsRightLetterTest3() {
        // Arrange
        String test = "1000 ZZ";
        // Act
        String formattedPostalCode = InputValidation.formatPostalCode(test);
        char letter = formattedPostalCode.substring(4).trim().charAt(0);
        // Assert
        assertEquals('Z', letter);
    }

    @Test
    public void returnsRightLetterTest4() {
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
    public void formatPostalCodeDoesNotAcceptIllegalArguments1() {
        InputValidation.formatPostalCode("gabgoujsabdgiuadbgiuadbjadbg");
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeDoesNotAcceptIllegalArguments2() {
        InputValidation.formatPostalCode("AZ 4500");
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeDoesNotAcceptIllegalArguments3() {
        InputValidation.formatPostalCode("999Az");
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeDoesNotAcceptIllegalArguments4() {
        InputValidation.formatPostalCode("10000ab");
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeDoesNotAcceptIllegalArguments5() {
        InputValidation.formatPostalCode("0123aa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeDoesNotAcceptIllegalArguments6() {
        InputValidation.formatPostalCode("0999 a1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeDoesNotAcceptIllegalArguments7() {
        InputValidation.formatPostalCode("10000 aaa");
    }

}
