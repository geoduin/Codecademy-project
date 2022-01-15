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

    @Override
    public ArrayList<Certificate> retrieve() {
        ArrayList<Certificate> certificates = new ArrayList<>();
        try (Statement statement = this.connection.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM Certificate");

            while (result.next()) {
                certificates.add(new Certificate(result.getInt("CertificateID"), result.getInt("EnrollmentID"), result.getString("EmployeeName"), result.getInt("Grade")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return certificates;
    }
}