package database;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domain.Course;
import domain.Difficulty;
import domain.Gender;
import domain.Webcast;
import domain.Certificate;

//Does not use any of the methods from repository, so it does not extend the abstract class.
public class StatisticsRepository{
    DBConnection dbConnection;

    public StatisticsRepository() {
        this.dbConnection = new DBConnection();
    }

    //Retrieves percentage of acquired certificates compared to all enrollments by gender
    public int retrievePercentageAcquiredCertificates(Gender gender) {
        int percentageAcquiredCertificates = 0;
        try {
            PreparedStatement preparedStatement = this.dbConnection.getConnection().prepareStatement("SELECT 100 * (1.0 * COUNT(Certificate.CertificateID)) / (1.0 * COUNT(Enrollment.ID)) AS Percentage FROM Enrollment LEFT JOIN Certificate ON Enrollment.ID = Certificate.EnrollmentID WHERE Enrollment.Email IN (SELECT Email FROM Student WHERE Gender = ?)");
            preparedStatement.setString(1, gender.name());
            ResultSet result = preparedStatement.executeQuery();
            

            while(result.next()) {
                percentageAcquiredCertificates = result.getInt("Percentage");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return percentageAcquiredCertificates;
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

    //Retrieves top 3 most watched webcasts
    public ArrayList<Webcast> retrieveTop3MostWatchedWebcasts() {
        ArrayList<Webcast> top3Webcasts = new ArrayList<>();
        WebcastRepository repo = new WebcastRepository();
        try {
            Statement statement = this.dbConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT TOP 3 * FROM Webcast ORDER BY Views DESC");

            while (result.next()) {
                int contentID = result.getInt("ContentID");
                top3Webcasts.add(repo.retrieveByTitle(repo.getTitleFromContentID(contentID)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return top3Webcasts;
    }
    
    //Retrieves recommended courses for a selected course
    public ArrayList<Course> retrieveRecommendedCourses(String courseName) {
        ArrayList<Course> retrievedCourses = new ArrayList<>();
        String query = "SELECT * FROM Course WHERE CourseName IN (SELECT RecommendedCourse FROM CourseRecommendation WHERE CourseName = ?)";
        try (PreparedStatement preparedStatement = this.dbConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, courseName);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                retrievedCourses.add(new Course(result.getString("CourseName"), result.getString("Subject"), result.getString("Description"), Difficulty.valueOf(result.getString("Difficulty"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return retrievedCourses;
    }

    //Retrieves the number of certificates that a student has achieved, see SQL documentation for explanation of the query
    public ArrayList<Certificate> retrieveStudentCertificates(String studentEmail) { 
        ArrayList<Certificate> certificates = new ArrayList<>();
        try {
            PreparedStatement statement = this.dbConnection.getConnection().prepareStatement("SELECT Certificate.CertificateID, Student.Name, Certificate.EmployeeName, Certificate.Grade FROM Student JOIN Enrollment ON Student.Email = Enrollment.Email JOIN Certificate ON Certificate.EnrollmentID = Enrollment.ID WHERE Student.Email = ?");
            statement.setString(1, studentEmail);
            ResultSet result = statement.executeQuery();
            while(result.next()) { 
                certificates.add(new Certificate(result.getInt("CertificateID"), result.getString("Name"), result.getString("EmployeeName"), result.getInt("Grade")));
            }
            return certificates;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    //Retrieves the top 3 courses by number of certificates gotten and the number of certificates for that course. 
    public List<Course> retrieveTop3CoursesByNumberOfCertificates() { 
        ArrayList<Course> topCourses = new ArrayList<>();
        try {
            Statement statement = this.dbConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT TOP 3 Course.CourseName, Course.Subject, Course.Description, Course.Difficulty, COUNT(Certificate.CertificateID) AS NrOfCertificates FROM Course LEFT JOIN Enrollment ON Course.CourseName = Enrollment.CourseName LEFT JOIN Certificate ON Enrollment.ID = Certificate.CertificateID GROUP BY Course.CourseName, Course.Subject, Course.Description, Course.Difficulty ORDER BY NrOfCertificates DESC");           
            while(result.next()) { 
                Course course = new Course(result.getString("CourseName"), result.getString("Subject"), result.getString("Description"), Difficulty.valueOf(result.getString("Difficulty")));
                course.setNrOfCertificates(result.getInt("NrOfCertificates"));
                topCourses.add(course);
            }
            return topCourses;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //Retrieves the number of certificates for the selected course, if the query is empty, there are no certificates so the method will return 0.
    public int retrieveNumberOFCertificates(String courseName) { 
        try {
            PreparedStatement statement = this.dbConnection.getConnection().prepareStatement("SELECT COUNT(*) AS 'CourseName' FROM Enrollment JOIN Certificate ON Enrollment.ID = Certificate.EnrollmentID WHERE Enrollment.CourseName = ? GROUP BY Enrollment.CourseName");
            statement.setString(1, courseName);
            ResultSet result = statement.executeQuery();
            while(result.next()) { 
                return result.getInt("CourseName"); 
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        
        
    }
}