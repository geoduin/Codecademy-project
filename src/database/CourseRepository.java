package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Course;

public class CourseRepository extends Repository<Course> {

    @Override
    public void insert(Course domainObject) {
        String name = domainObject.getName();
        String topic = domainObject.getTopic();
        String difficulty = domainObject.getDifficulty().name();

        try {
            Statement statement = this.connection.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO Course VALUES ('"+name+"', '"+topic+"', '"+difficulty+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Course domainObject) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Course domainObject) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ArrayList<Course> retrieve() {
        // TODO Auto-generated method stub
        return null;
    }    
}