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

    //Retrieves percentage of acquired certificates compared to all enrollments by gender, see report for a detailed description of how the query works.
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
    //An ArrayList is used instead of a 2d int array because you cannot get the size of the ResultSet without a 2nd query which increases complexity and possibility of failure unnecessarily. 
    //The int array is in the format [ContentID, Percentage]
    public List<int[]> retrieveAverageProgressionPerModule(String courseName) {
        List<int[]> coursesAndPercentages = new ArrayList<>();
        try {
            PreparedStatement statement = this.dbConnection.getConnection().prepareStatement("SELECT ContentID, AVG(Percentage) AS AverageProgression FROM Progress WHERE ContentID IN (SELECT ContentID FROM Course JOIN Module ON Course.CourseName = Module.CourseName WHERE Course.CourseName = ?) GROUP BY ContentID");
            statement.setString(1, courseName);
            ResultSet result = statement.executeQuery();
            while(result.next()) { 
                int[] courseAndPercentage = {result.getInt("ContentID"), result.getInt("AverageProgression")};          
                coursesAndPercentages.add(courseAndPercentage);
            }
           
            return coursesAndPercentages;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    

    //Retrieves  a students progression per module for a selected course
    //The integer array is in the format of [ContentID, Percentage]
    public List<int[]> retrieveProgressionPerModule(String studentEmail, String courseName) {
        List<int[]> progressOfModules = new ArrayList<>();
        try {
            //The query works by first selecting all ContentID's of the selected course,
            //the query will then select the ContentID from Progress where the student email matches the given email
            //and where the contentID matches the contentID's returned by the subquery, leaving you only with the progress of the student in a specific course.
            PreparedStatement preparedStatement = this.dbConnection.getConnection().prepareStatement(" SELECT ContentID, Percentage FROM Progress WHERE StudentEmail = ? AND ContentID IN (SELECT ContentID FROM Module WHERE CourseName = ?)");
            preparedStatement.setString(1, studentEmail);
            preparedStatement.setString(2, courseName);
            
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int[] idPercentage = {result.getInt("ContentID"), result.getInt("Percentage")};
                progressOfModules.add(idPercentage);
            }
            preparedStatement.close();
            return progressOfModules;
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
            //The query works by sorting all webcasts by their descending order of views and then returning their ContentID.
            Statement statement = this.dbConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT TOP 3 ContentID FROM Webcast ORDER BY Views DESC");

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
        //The query works by retrieving the records from the CourseRecommendation table where CourseName matches the given course name
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
            PreparedStatement statement = this.dbConnection.getConnection().prepareStatement("SELECT 100 * (COUNT(Certificate.CertificateID)) / (COUNT(Enrollment.ID)) AS Percentage FROM Enrollment LEFT JOIN Certificate ON Enrollment.ID = Certificate.EnrollmentID WHERE Enrollment.Email IN (SELECT Email FROM Student WHERE Gender = ?)");
            statement.setString(1, studentEmail);
            ResultSet result = statement.executeQuery();
            while(result.next()) { 
                Certificate certificate = new Certificate(result.getInt("CertificateID"), result.getInt("EnrollmentID"), result.getString("EmployeeName"), result.getInt("Grade"));
                certificate.setCourseName(result.getString("CourseName"));
                certificates.add(certificate);
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

    //Retrieves the number of certificates for the selected course, if the query is empty, there are no certificates so the method will return 
    public int retrieveNumberOfStudentsWhoCompletedCourse(String courseName) { 
        try {
            PreparedStatement statement = this.dbConnection.getConnection().prepareStatement("SELECT COUNT(Email) AS NumberOfStudents FROM Student WHERE Email IN (SELECT StudentEmail FROM Progress WHERE ContentID IN (SELECT ContentID FROM Module  WHERE CourseName = ?) GROUP BY StudentEmail HAVING SUM(Percentage)/COUNT(ContentID) = 100)");
            statement.setString(1, courseName);
            ResultSet result = statement.executeQuery();
            while(result.next()) { 
                return result.getInt("NumberOfStudents"); 
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        
        
    }
}