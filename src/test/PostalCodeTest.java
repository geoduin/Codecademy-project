package test;

import static org.junit.Assert.assertTrue;
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
    public void returnedPostalCodeNumberIsBiggerThan999Test1() {
        assertTrue(Integer.valueOf(InputValidation.formatPostalCode("1000 AZ").substring(0,4)) > 999);
    }
    @Test
    public void returnedPostalCodeNumberIsBiggerThan999Test2() {
        assertTrue(Integer.valueOf(InputValidation.formatPostalCode("2513 AA").substring(0,4)) > 999);
    }
    @Test
    public void returnedPostalCodeNumberIsSmallerOrEqualTo9999Test1() {
        assertTrue(Integer.valueOf(InputValidation.formatPostalCode("9999 AA").substring(0,4)) <= 9999);
    }
    @Test
    public void returnedPostalCodeNumberIsSmallerOrEqualTo9999Test2() {
        int postCodeNumbers = Integer.valueOf(InputValidation.formatPostalCode("1234 AB").substring(0,4));
        assertTrue(postCodeNumbers <= 9999);

    }
    





    @Test
    public void formatPostalCodeAcceptsValidPostalCodesTest3() {
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
        InputValidation.formatPostalCode("0999 a1");
        InputValidation.formatPostalCode("10000 aaa");
    }

}