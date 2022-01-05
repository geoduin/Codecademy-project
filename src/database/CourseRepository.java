package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Course;
import domain.Difficulty;

//Class responsible for database communication regarding courses. 
public class CourseRepository extends Repository<Course> {

    // CoursRepository IS a repository and thus extends the abstract repo class
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
            Statement statement = connection.getConnection()
                    .createStatement();
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

    // Perform the same type of delete action, but now using the name of the course
    // the method receives on call as argument
    public void delete(String courseName) {
        try {
            // In the CourseRecommendation table, delete all records where the given
            // CourseName argument is involved
            String sql2 = "DELETE FROM CourseRecommendation WHERE CourseName = ? OR RecommendedCourse = ?";
            PreparedStatement statement2 = connection.getConnection().prepareStatement(sql2);

            statement2.setString(1, courseName);
            statement2.setString(2, courseName);
            statement2.executeUpdate();

            // Now delete the actual Course record
            String sql = "DELETE FROM Course WHERE CourseName = ?";
            PreparedStatement statement = connection.getConnection().prepareStatement(sql);

            statement.setString(1, courseName);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
             * If an exception occurs perform no action and futher exception handling,
             * because the course is already set as
             * recommendation (which is fine).
             */
        }

    }
}