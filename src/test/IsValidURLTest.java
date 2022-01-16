package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import logic.InputValidation;

public class IsValidURLTest {
    /**
     * @desc this method checks if the url meets these requirements: <https ||
     *       http>://<Subdomain at least one character>.<domainName at Least one
     *       character>.<tld at least one character>
     * 
     * 
     * 
     *       /**
     * @subcontract url lacks a protocol
     * @requires !url.startsWith(http||https)
     * @ensures result = false
     */
    @Test
    public void testIsValidURLWithoutHttp() {
        // Arrange
        String url = "s://www.test.nl";
        // Act
        boolean result = InputValidation.isValidURL(url);
        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract url lacks subdomain
     * @requires url.split["://"][1].split["\."][0].length < 1
     * @ensures result = false
     */
    @Test
    public void testIsValidURLWithoutSubdomain() {
        // Arrange
        String url = "https://.test.nl";
        // Act
        boolean result = InputValidation.isValidURL(url);
        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract url lacks domainName
     * @requires url.split["://"][1].split["\."][1].length < 1
     * @ensures result = false
     */
    @Test
    public void testIsValidURLWithoutDomainName() {
        // Arrange
        String url = "https://www..nl";
        // Act
        boolean result = InputValidation.isValidURL(url);
        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract url lacks top level domain
     * @requires url.split["://"][1].split["\."][2].length < 1
     * @ensures result = false
     */
    @Test
    public void testIsValidURLWithoutTLD() {
        // Arrange
        String url = "https://www.TEST.";
        // Act
        boolean result = InputValidation.isValidURL(url);
        // Assert
        assertEquals(false, result);
    }

    /**
     * @subcontract url has the correct format
     * @requires url.split["://"][1].split["\."][2].length > 1 &&
     *           url.split["://"][1].split["\."][1].length > 1 &&
     *           url.split["://"][1].split["\."][0].length > 1 &&
     *           url.startsWith(http||https)
     * @ensures result = true
     * 
     */
    @Test
    public void testIsValidURLWithValidURL() {
        // Arrange
        String url = "http://www.test.nl";
        // Act
        boolean result = InputValidation.isValidURL(url);
        // Assert
        assertEquals(true, result);
    }

    /**
     * @subcontract url has the correct format
     * @requires url.split["://"][1].split["\."][2].length > 1 &&
     *           url.split["://"][1].split["\."][1].length > 1 &&
     *           url.split["://"][1].split["\."][0].length > 1 &&
     *           url.startsWith(http||https)
     * @ensures result = true
     * 
     */

    @Test
    public void testIsValidURLWithValidSubdomain() {
        // Arrange
        String url = "http://BrightSpace.test.nl";
        // Act
        boolean result = InputValidation.isValidURL(url);
        // Assert
        assertEquals(true, result);
    }
}
