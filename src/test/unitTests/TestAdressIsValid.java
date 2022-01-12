package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import logic.InputValidation;

public class TestAdressIsValid {

    /**
     * @desc An method that combines other methods and checks if all these methods
     *       will pass, in order to give boolean value true
     *       That includes all fields not empty or blank
     *       Housenumner is a decimal
     *       Postal code is correct
     * 
     *       /* @subcontract values are meeting the address criteria
     * @requires All fields filled = true, housenumber is a decimal = true and
     *           postalcode has the right format = true
     * @ensures \ result = true
     */
    @Test
    public void testIfAddresIsValid() {
        // Arrange
        String street = "Nieuwstraat";
        String houseNr = "28";
        String postalCode = "3000AB";
        // Act
        boolean result = InputValidation.addressIsValid(street, houseNr, postalCode);
        // Assert
        assertEquals(true, result);
    }

    /*
     * @subcontract one of the criteria has not been met{
     * 
     * @requires One the fields are empty || HouseNr is not a decimal || postalcode
     * does not fullfill the postalcode requirements
     * 
     * @ensures \result =false
     * }
     */
    @Test
    public void testIfAddresIsValidIfOneOfTheConditionsArentMet() {
        // Arrange
        String street = "";
        String houseNr = "28";
        String postalCode = "3000AB";
        // Act
        boolean result = InputValidation.addressIsValid(street, houseNr, postalCode);
        // Assert
        assertEquals(false, result);
    }

    /*
     * @subcontract one of the criteria has not been met{
     * 
     * @requires One the fields are empty || HouseNr is not a decimal || postalcode
     * does not fullfill the postalcode requirements
     * 
     * @ensures \result =false
     * }
     */
    @Test
    public void testIfAddresIsValidIfPostalcodeConditionIsNotMet() {
        // Arrange
        String street = "Street";
        String houseNr = "28";
        String postalCode = "3000";
        // Act
        boolean result = InputValidation.addressIsValid(street, houseNr, postalCode);
        // Assert
        assertEquals(false, result);
    }

    /*
     * @subcontract one of the criteria has not been met{
     * 
     * @requires One the fields are empty || HouseNr is not a decimal || postalcode
     * does not fullfill the postalcode requirements
     * 
     * @ensures \result =false
     * }
     */
    @Test
    public void testIfAddresIsValidIfHouseNrIsNotDecimal() {
        // Arrange
        String street = "Street";
        String houseNr = "er";
        String postalCode = "3000";
        // Act
        boolean result = InputValidation.addressIsValid(street, houseNr, postalCode);
        // Assert
        assertEquals(false, result);
    }
}
