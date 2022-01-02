package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Course;
import domain.Difficulty;

//Class responsible for database communication regarding courses. 
public class CourseRepository extends Repository<Course> {

    public CourseRepository() {
        super();
    }

    // Insert individual course
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

    @Override
    public void update(Course course) {

    }

    @Override
    public void delete(Course course) {

    }

    @Override
    public ArrayList<Course> retrieve() {
        return null;
    }
}