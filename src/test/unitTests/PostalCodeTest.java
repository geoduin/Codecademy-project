package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import logic.InputValidations;

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
        InputValidations.formatPostalCode(null);
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
        assertEquals("1000 AB", InputValidations.formatPostalCode("1000ab"));
        assertEquals("2513 AA", InputValidations.formatPostalCode("2513 aA"));
        assertEquals("9999 ZZ", InputValidations.formatPostalCode(" 9999Zz "));
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
        InputValidations.formatPostalCode("gabgoujsabdgiuadbgiuadbjadbg");
        InputValidations.formatPostalCode("AZ 4500");
        InputValidations.formatPostalCode("999Az");
        InputValidations.formatPostalCode("10000ab");
        InputValidations.formatPostalCode("0123aa");
    }

}