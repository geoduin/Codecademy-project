package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Course;
import domain.Difficulty;

/*
*Repository that is responsible for the Course domain object. 
*/
public class CourseRepository extends Repository<Course> {

    // CourseRepository IS a repository and thus extends the abstract repo class
    public CourseRepository() {
        super();
    }

    // Inserts an individual course instance into the course table
    @Override
    public void insert(Course course) {
        try {
            // Prepared statement parameter setup
            String sql = "INSERT INTO Course VALUES (?, ?, ?, ?)";
            PreparedStatement preppedStatement = connection.getConnection().prepareStatement(sql);

            // Adding arguments to query based on the course instance
            preppedStatement.setString(1, course.getName());
            preppedStatement.setString(2, course.getTopic());
            preppedStatement.setString(3, course.getDescription());
            preppedStatement.setString(4, course.getDifficulty().toString());

            // Executing
            preppedStatement.executeUpdate();
            preppedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // For every existing Course record, return its name within a list.
    public ArrayList<String> retrieveAllCourseNames() {
        ArrayList<String> courseNames = new ArrayList<>();

        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT CourseName FROM Course");

            while (queryResult.next()) {
                courseNames.add(queryResult.getString("CourseName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseNames;
    }

    // Instantiate an individual course from the database, selected by its name
    // (which is the primary key)
    public Course retrieveCourseByName(String courseName) {
        Course returnValue = null;
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet queryResult = statement
                    .executeQuery("SELECT * FROM Course WHERE CourseName = '" + courseName + "'");

            if (queryResult.next()) {
                returnValue = new Course(queryResult.getString("CourseName"), queryResult.getString("Subject"),
                        queryResult.getString("Description"), Difficulty.valueOf(queryResult.getString("Difficulty")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    /*
     * Based of a course instance, received as argument, update the related record
     * attributes
     * according to the current state of the course instance
     */
    @Override
    public void update(Course course) {
        try {
            String sql = "UPDATE Course SET Subject = ?, Description = ?, Difficulty = ? WHERE CourseName = ?";
            PreparedStatement statement = connection.getConnection().prepareStatement(sql);

            statement.setString(1, course.getTopic());
            statement.setString(2, course.getDescription());
            statement.setString(3, course.getDifficulty().toString());
            statement.setString(4, course.getName());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Same as with an update, only this time the related record gets deleted. Any
    // relations with modules get deleted per 'cascade delete'
    @Override
    public void delete(Course course) {

    }

    /*
     * Delete a course based on its name the method receives as argument. Return
     * true if successful. If a course can't be finally deleted due to existing
     * enrollments, a false will be returned so that the user can be informed via
     * the GUI
     */
    public boolean delete(String courseName) {
        // In the CourseRecommendation table, delete all records where the given
        // CourseName argument is involved
        String deleteStatement1 = "DELETE FROM CourseRecommendation WHERE CourseName = ? OR RecommendedCourse = ?";
        try (PreparedStatement firstDeleteExecution = connection.getConnection().prepareStatement(deleteStatement1)) {

            firstDeleteExecution.setString(1, courseName);
            firstDeleteExecution.setString(2, courseName);
            firstDeleteExecution.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Now delete the actual Course record
        String deleteStatement2 = "DELETE FROM Course WHERE CourseName = ?";
        try (PreparedStatement secondDeleteExecution = connection.getConnection().prepareStatement(deleteStatement2)) {
            secondDeleteExecution.setString(1, courseName);
            secondDeleteExecution.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Course> retrieve() {
        return null;
    }

    // Within in the CourseRecommendation table, define a new
    // recommendation-relationship between two courses
    public void addRecommendedCourse(String courseName, String recommendedCourseName) {
        try {
            String sql = "INSERT INTO CourseRecommendation VALUES (?, ?)";
            PreparedStatement statement = connection.getConnection().prepareStatement(sql);

            statement.setString(1, courseName);
            statement.setString(2, recommendedCourseName);

            statement.executeUpdate();

        } catch (SQLException e) {
            /*
             * If an exception occurs perform no action and further exception handling,
             * because the course is already set as
             * recommendation (which is fine).
             */
        }
    }
}