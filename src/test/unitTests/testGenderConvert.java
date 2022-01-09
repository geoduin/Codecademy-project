package Test.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import domain.Gender;
import logic.InputValidations;

public class TestGenderConvert {

    /**
     * @desc this method converts string to gender
     *       You can only choose from 'O', 'F' and 'M'
     * 
     * @subcontract converts M String to Gender.M
     * @requires String combobox.value() = M
     * @return \result = Gender.M
     * 
     * @subcontract converts F String to Gender.F
     * @requires String combobox.value() = F
     * @return \result = Gender.F
     * 
     * @subcontract converts O String to Gender.O
     * @requires String combobox.value() = O
     * @return \result = Gender.O
     * 
     */

    @Test
    public void testConvertGenderToM() {
        // Arrange
        String male = "M";
        // Act
        Gender man = InputValidations.convertToGender(male);
        // Assert
        assertEquals(Gender.M, man);
    }

    @Test
    public void testConvertGenderToF() {
        // Arrange
        String girl = "F";
        // Act
        Gender women = InputValidations.convertToGender(girl);
        // Assert
        assertEquals(Gender.F, women);
    }

    @Test
    public void testConvertGenderToO() {
        // Arrange
        String unknown = "O";
        // Act
        Gender what = InputValidations.convertToGender(unknown);
        // Assert
        assertEquals(Gender.O, what);
    }
}
