package test.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import test.exception.emailExistence;

public class testEmailExistence {
    private emailExistence emailbox = new emailExistence();

    @Test
    public void testEmailExistToGetTrue() {
        // Arrange
        String checkedMail = "Xin20Wang@outlook.com";
        // Act
        boolean emailExist = emailbox.emailExist(checkedMail);
        // Assert
        assertEquals(true, emailExist);
    }

    @Test
    public void testEmailDoesNotExistToGetFalse() {
        // Arrange
        String checkedMail = "Penrose@gmail.com";
        // Act
        boolean emailExist = emailbox.emailExist(checkedMail);
        // Assert
        assertEquals(false, emailExist);
    }

    @Test
    public void testEmailExistGiveFalseIfNotGivingAEmail() {
        // Arrange
        String checkedMail = "Penrose";
        // Act
        boolean emailExist = emailbox.emailExist(checkedMail);
        // Assert
        assertEquals(false, emailExist);
    }

    @Test
    public void testEmailExistIsEmail() {
        // Arrange
        String checkedMail = "Xin";
        // Act
        boolean emailExist = emailbox.emailExist(checkedMail);
        // Assert
        assertEquals(false, emailExist);
    }

}
