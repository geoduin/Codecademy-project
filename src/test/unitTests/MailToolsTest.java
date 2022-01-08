package test.unitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test.Methods.inputValidations;

public class MailToolsTest {

    /**
     * @desc Validates if mailAddress is valid. It should be in the form of:
     *       <at least 1 character>@<at least 1 character>.<at least 1 character>
     * 
     * @subcontract no mailbox part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[0].length < 1;
     * @ensures \result = false;
     *          }
     */
    @Test
    public void testValidateMailaddressRequiresAddressContainsNoAtSignEnsuresFalse() {
        // Arrange
        String mailAddress = "henk.butsergmail.nl";

        // Act
        Boolean result = inputValidations.validateMailAddress(mailAddress);

        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract subdomain-tld delimiter {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".").length > 2;
     * @ensures \result = false;
     *          }
     */

    @Test
    public void testValidateMailaddressRequiresMoreThanOneTLDPartEnsuresFalse() {
        // Arrange
        String mailAddress = "henk.butsers@gmail.com.com";

        // Act
        Boolean result = inputValidations.validateMailAddress(mailAddress);

        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract no subdomain part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".")[0].length < 1;
     * @ensures \result = false;
     *          }
     */
    @Test
    public void testValidateMailaddressRequiresAddressWithoutSubdomainEnsuresFalse() {
        // Arrange
        String mailAddress = "henk.butsers@.nl";

        // Act
        Boolean result = inputValidations.validateMailAddress(mailAddress);

        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract no tld part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".")[1].length < 1;
     * @ensures \result = false;
     *          }
     */
    @Test
    public void testValidateMailaddressRequiresAddressWithoutTLDPartEnsuresFalse() {
        // Arrange
        String mailAddress = "henk.butsers@sub";

        // Act
        Boolean result = inputValidations.validateMailAddress(mailAddress);

        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract valid email {
     * @requires no other precondition
     * @ensures \result = true;
     *          }
     * 
     */
    @Test
    public void testValidateMailAddressValidMailInputEnsuresTrue() {
        // Arrange
        String mailAddress = "henk.butsers@gmail.nl";

        // Act
        Boolean result = inputValidations.validateMailAddress(mailAddress);

        // Assert
        assertEquals(true, result);
    }
}