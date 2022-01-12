package Test.UnitTests;

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
    public void formatPostalCodeAcceptsValidPostalCodesTest() {
        assertEquals("1000 AB", InputValidation.formatPostalCode("1000ab"));
        assertEquals("2513 AA", InputValidation.formatPostalCode("2513 aA"));
        assertEquals("9999 ZZ", InputValidation.formatPostalCode(" 9999Zz "));
    }

    /**
     * @subcontract invalid postalCode {
     * @requires no other valid precondition;
     * @signals (IllegalArgumentException);
     *          }
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeDoesNotAcceptIllegalArguments() {
        InputValidation.formatPostalCode("gabgoujsabdgiuadbgiuadbjadbg");
        InputValidation.formatPostalCode("AZ 4500");
        InputValidation.formatPostalCode("999Az");
        InputValidation.formatPostalCode("10000ab");
        InputValidation.formatPostalCode("0123aa");
    }

}