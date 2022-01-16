package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import domain.Certificate;

public class CertificateRepository extends Repository<Certificate>{
    public CertificateRepository() {
        super();
    }

    // Inserts the certificate given as the parameter into the database
    @Override
    public void insert(Certificate certificate) {
        String query = "INSERT INTO Certificate VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(query)) {
            
            preparedStatement.setInt(1, certificate.getEnrollmentID());
            preparedStatement.setInt(2, certificate.getGrade());
            preparedStatement.setString(3, certificate.getNameOfIssuer());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    // Overwrites/Updates an existing certificate within the database
    @Override
    public void update(Certificate certificate) {
        String query = "UPDATE Certificate SET EnrollmentID = ?, Grade = ?, EmployeeName = ? WHERE CertificateID = ?";
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(query)) {
            
            preparedStatement.setInt(1, certificate.getEnrollmentID());
            preparedStatement.setInt(2, certificate.getGrade());
            preparedStatement.setString(3, certificate.getNameOfIssuer());
            preparedStatement.setInt(4, certificate.getCertificateID());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Deletes the certificates given as a parameter from the database
    @Override
    public void delete(Certificate certificate) {
        String query = "DELETE FROM Certificate WHERE CertificateID = ?";
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(query) ) {
            
            preparedStatement.setInt(1, certificate.getCertificateID());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Retrieves all existing certificates from the database
    @Override
    public ArrayList<Certificate> retrieve() {
        ArrayList<Certificate> certificates = new ArrayList<>();
        try (Statement statement = this.connection.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery("SELECT Certificate.*, Student.Name FROM Certificate JOIN Enrollment ON Certificate.EnrollmentID = Enrollment.ID JOIN Student ON Enrollment.Email = Student.Email");

            while (result.next()) {
                certificates.add(new Certificate(result.getInt("CertificateID"), result.getString("Name"), result.getInt("EnrollmentID"), result.getString("EmployeeName"), result.getInt("Grade")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return certificates;
    }
}