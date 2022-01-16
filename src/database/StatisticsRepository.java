package database;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import domain.Course;
import domain.Difficulty;
import domain.Gender;
import domain.Webcast;
import domain.Certificate;

//This repository is a bit unique, while still connects with a database, it does not use any of the methods from Repository, so it does not extend the abstract class.
//This class handles information requests given by the StatisticsLogic class from the StatisticsViews class. 
//So that the StatisticsViews can show the required statistics to the user. 
public class StatisticsRepository{
    DBConnection dbConnection;
    CourseRepository courseRepo;

    public StatisticsRepository() {
        this.dbConnection = new DBConnection();
        this.courseRepo = new CourseRepository();
    }

    //This method is used to provide a percentage statistic in the StatisticsViews class.
    
    
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
            return -1;
        }

        return percentageAcquiredCertificates;
    }
    
    //This method is used to retrieve the average progress in each course module for a given course so it can be displayed on the StatisticsViews. 
    //A List is used instead of a 2d int array because you cannot get the size of the ResultSet without a 2nd query which increases complexity and possibility of failure unnecessarily. 
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

    //This method is used so the app user can check the progress of a given student in a given course
    //A List of integer arrays is used instead of a 2d array since you can't get the length of the ResultSet without either first putting it in a list or using a 2nd query.
    //The int[] is in the format of [ContentID, Percentage]
    public List<int[]> retrieveProgressionPerModule(String studentEmail, String courseName) {
        List<int[]> progressOfModules = new ArrayList<>();
        try {
            //The query works by first selecting all ContentID's of the selected course,
            //the query will then select the ContentID from Progress where the student email matches the given email
            //and where the contentID matches the contentID's returned by the sub query, leaving you only with the progress of the student in a specific course.
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

    //This method gets the 3 most watched webcasts and returns them so that this statistic can be shown in the statistics view. 
    

    public List<Webcast> retrieveTop3MostWatchedWebcasts() {
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
    
    
    //This method is used so you can check which courses are recommended in the gui. 

    public List<Course> retrieveRecommendedCourses(String courseName) {
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

    //Retrieves the number of certificates that a student has achieved.
    public List<Certificate> retrieveStudentCertificates(String studentEmail) { 
        ArrayList<Certificate> certificates = new ArrayList<>();
        try {
            PreparedStatement statement = this.dbConnection.getConnection().prepareStatement("SELECT Certificate.*, Student.Name, Enrollment.CourseName FROM Certificate JOIN Enrollment ON Certificate.EnrollmentID = Enrollment.ID JOIN Student ON Enrollment.Email = Student.Email WHERE Student.Email = ?");
            statement.setString(1, studentEmail);
            ResultSet result = statement.executeQuery();
            while(result.next()) { 
                Certificate certificate = new Certificate(result.getInt("CertificateID"), result.getString("CourseName"), result.getString("Name"), result.getInt("EnrollmentID"), result.getString("EmployeeName"), result.getInt("Grade"));
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
    //The method is used by the StatisticsLogic class to print the most viewed webcasts on the GUI
    //An instance of courseRepo is used to prevent code duplication.
    public List<Course> retrieveTop3CoursesByNumberOfCertificates() { 
        ArrayList<Course> topCourses = new ArrayList<>();
        try {
            Statement statement = this.dbConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT TOP 3 CourseName, COUNT(ID) AS NrOfCertificates FROM Enrollment  JOIN Certificate ON Enrollment.ID = Certificate.EnrollmentID GROUP BY CourseName ORDER BY NrOfCertificates DESC");           
            while(result.next()) { 
                Course course = this.courseRepo.retrieveCourseByName(result.getString("CourseName"));
                course.setNrOfCertificates(result.getInt("NrOfCertificates"));
                topCourses.add(course);
            }
            return topCourses;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //Retrieves the number of certificates for the selected course. 
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