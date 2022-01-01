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

    public static void main(String[] args) {
        CourseRepository tesRepository = new CourseRepository();
        tesRepository.insert(new Course("Henk Course", "Henken", "Even henken", Difficulty.HARD));
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
            preppedStatement.setString(3, course.getDifficulty().toString());
            preppedStatement.setString(4, course.getDescription());

            // Executing
            preppedStatement.executeQuery();
            preppedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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