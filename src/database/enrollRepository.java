package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import domain.Enrollment;

public class EnrollRepository extends Repository<Enrollment> {

    public EnrollRepository() { 
        super();
    }

    @Override
    public ArrayList retrieve() {
        return null;
    }

    @Override
    public void insert(Enrollment enrollment) {
        boolean makeProgress = true;
        Connection connect = this.connection.getConnection();
        try (PreparedStatement statement = connect.prepareStatement("INSERT INTO Enrollment VALUES(?,?,?)")) {
            statement.setObject(1, enrollment.getDateOfEnrollment());
            statement.setString(2, enrollment.getStudent().getEmail());
            statement.setString(3, enrollment.getCourse().getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Enrollment cannot be inserted");
            e.printStackTrace();
            // If the combination of email, date and course already exist, it will set
            // makeProgress on false and it will not create a progress
            makeProgress = false;
        }

        if (makeProgress) {
            try {
                // Retrieves contentID's from that particular course
                List<Integer> list = collectContentIDs(enrollment);
                for (Integer id : list) {

                    // if there is no student with this contentID in the progress table, it will
                    // create one
                    if (hasStudentProgress(enrollment.getStudent().getEmail(), id) == 0) {
                        insertProgress(enrollment.getStudent().getEmail(), id);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // Based on Student key and Webcast key or module as argument, set a new
    // progress record
    public void insertProgress(String StudentEmail, int contentId) {
        Connection connect = this.connection.getConnection();
        try (PreparedStatement state = connect
                .prepareStatement("INSERT INTO Progress (StudentEmail, ContentID) VALUES (?, ?)")) {
            state.setString(1, StudentEmail);
            state.setInt(2, contentId);
            state.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check if contentID and
    public List<Integer> collectContentIDs(Enrollment enrollment) {
        Connection connect = this.connection.getConnection();
        List<Integer> contentIds = new ArrayList<>();
        try (PreparedStatement stat = connect.prepareStatement("SELECT ContentID FROM Module WHERE CourseName = ?")) {
            stat.setString(1, enrollment.getCourse().getName());
            ResultSet results = stat.executeQuery();
            while (results.next()) {
                contentIds.add(results.getInt("ContentID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentIds;
    }

    // this method will return a 1 if there are students present in the progress
    // table, it will give zero if there isn't
    public int hasStudentProgress(String email, int contentID) {
        int count = 0;
        try (PreparedStatement query = this.connection.getConnection()
                .prepareStatement("SELECT COUNT(*)AS result FROM Progress WHERE StudentEmail = ? AND ContentID =?")) {
            query.setString(1, email);
            query.setInt(2, contentID);
            ResultSet set = query.executeQuery();
            set.next();
            // If result is 1, count = 1
            count = set.getInt("result");
        } catch (Exception e) {
            // if result is 0, it returns count = 0;
            return count;
        }
        return count;
    }

    // Updates the enrollment Currently not being used
    @Override
    public void update(Enrollment domainObject) {

    }

    // Different delete in use.
    @Override
    public void delete(Enrollment domainObject) {

    }

    public void deleteProgressWithoutCourse() {
        try (PreparedStatement statement = this.connection.getConnection()
                .prepareStatement(
                        "DELETE Progress FROM Progress JOIN Module ON Module.ContentID = Progress.ContentID WHERE CourseName IS NULL")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> retrieveEmailsFromCourse(String courseName) {
        String sql = "SELECT DISTINCT StudentEmail FROM Progress JOIN Module ON Module.ContentID = Progress.ContentID WHERE CourseName = ?";
        List<String> emailList = new ArrayList<>();
        // If there are any students, the ResultSet will add the emails to the email
        // list
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            statement.setString(1, courseName);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                emailList.add(set.getString("StudentEmail"));
            }
        } catch (SQLException e) {
            System.err.println("No students");
            return emailList;
        }
        return emailList;
    }

    public void updateProgressWithNewModule(String courseName, int contentID) {
        // Retrieves list of emails who are participating to the course
        List<String> emailList = retrieveEmailsFromCourse(courseName);

        // If there are students, it will insert a new progress with the new module
        if (emailList.size() != 0) {
            for (String studentEmail : emailList) {
                insertProgress(studentEmail, contentID);
            }
        }
    }

}