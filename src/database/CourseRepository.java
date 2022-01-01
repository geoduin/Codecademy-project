package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Course;

public class CourseRepository extends Repository<Course> {

    @Override
    public void insert(Course course) {
        String name = course.getName();
        String topic = course.getTopic();
        String difficulty = course.getDifficulty().name();

        try {
            Statement statement = this.connection.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO Course VALUES ('"+name+"', '"+topic+"', '"+difficulty+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Course course) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Course course) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ArrayList<Course> retrieve() {
        // TODO Auto-generated method stub
        return null;
    }    
}