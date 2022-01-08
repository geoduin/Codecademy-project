package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Gender;

//Does not use any of the methods from repository, so it does not extend the abstract class.
public class StatisticsRepository{
    DBConnection dbConnection;

    public StatisticsRepository() {
        this.dbConnection = new DBConnection();
    }

    //Retrieves percentage of aquired certificates compared to all enrollments by gender
    public int retrievePercentageAquiredCertificates(Gender gender) {
        int percentageAquiredCertificates = 0;
        try {
            PreparedStatement preparedStatement = this.dbConnection.getConnection().prepareStatement("SELECT 100 * (1.0 * COUNT(Certificate.CertificateID)) / (1.0 * COUNT(Enrollment.ID)) AS Percentage FROM Enrollment LEFT JOIN Certificate ON Enrollment.ID = Certificate.EnrollmentID WHERE Enrollment.Email IN (SELECT Email FROM Student WHERE Gender = ?)");
            preparedStatement.setString(1, gender.name());
            ResultSet result = preparedStatement.executeQuery();
            preparedStatement.close();

            while(result.next()) {
                percentageAquiredCertificates = result.getInt("Percentage");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return percentageAquiredCertificates;
    }
    
    //retrieves the ContentID of each module and the average progressions of all students for that module. 
    //Hashmap is in the format <ID, Percentage>
    public HashMap<Integer, Integer> retrieveAverageProgressionPerModule(String courseName) {
        HashMap<Integer, Integer> percentages = new HashMap<>();
        try {
            PreparedStatement statement = this.dbConnection.getConnection().prepareStatement("SELECT ContentID, AVG(Percentage) AS AverageProgression FROM Progress WHERE ContentID IN (SELECT ContentID FROM Course JOIN Module ON Course.CourseName = Module.CourseName WHERE Course.CourseName = ?) GROUP BY ContentID");
            statement.setString(1, courseName);
            ResultSet result = statement.executeQuery();
            while(result.next()) { 
                int key = result.getInt("ContentID");
                int percentage = result.getInt("AverageProgression");
                percentages.put(key, percentage);
            }
            statement.close();
            return percentages;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        
    }

    //Retrieves progression per module for a selected course
    public HashMap<Integer, Integer> retrieveProgressionPerModule(String studentEmail, String courseName) {
        HashMap<Integer, Integer> percentagesPerModule = new HashMap<>();
        try {
            PreparedStatement preparedStatement = this.dbConnection.getConnection().prepareStatement("SELECT ContentID, Percentage FROM Progress WHERE StudentEmail = ? AND ContentID IN (SELECT ContentID FROM Course JOIN Module ON Course.CourseName = Module.CourseName WHERE Course.CourseName = ?)");
            preparedStatement.setString(1, studentEmail);
            preparedStatement.setString(2, courseName);
            
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int contentID = result.getInt("ContentID");
                int percentage = result.getInt("Percentage");
                percentagesPerModule.put(contentID, percentage);
            }
            preparedStatement.close();
            return percentagesPerModule;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    





















    //Retrieves the number of certificates that a student has achieved, see SQL documentation for explanation of the query
    public ArrayList<Integer> retrieveStudentCertificates(String studentEmail) { 
        ArrayList<Integer> certificates = new ArrayList<>();
        try {
            PreparedStatement statement = this.dbConnection.getConnection().prepareStatement("SELECT CertificateID FROM Student JOIN Enrollment ON Student.Email = Enrollment.Email JOIN Certificate ON Certificate.EnrollmentID = Enrollment.ID WHERE Student.Email = ?");
            statement.setString(1, studentEmail);
            ResultSet result = statement.executeQuery();
            statement.close();
            while(result.next()) { 
                certificates.add(result.getInt("CertificateID"));
            }
            return certificates;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    
    }
    






    



    
}