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

    @Override
    public ArrayList retrieve() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insert(Enrollment enrollment) {
        // TODO Auto-generated method stub
        Connection connect = this.connection.getConnection();
        try (PreparedStatement statement = connect.prepareStatement("INSERT INTO Enrollment VALUES(?,?,?)")) {
            statement.setObject(1, enrollment.getDateOfEnrollment());
            statement.setString(2, enrollment.getStudent().getEmail());
            statement.setString(3, enrollment.getCourse().getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.err.println("Enrollment cannot be inserted");
            e.printStackTrace();
        }

        try {
            List<Integer> list = collectContentIDs(enrollment);
            for (Integer id : list) {
                if (hasStudentProgress(enrollment.getStudent().getEmail(), id) == 0) {
                    insertProgress(enrollment, id);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void insertProgress(Enrollment enrollment, int contentId) {
        Connection connect = this.connection.getConnection();
        try (PreparedStatement state = connect
                .prepareStatement("INSERT INTO Progress (StudentEmail, ContentID) VALUES (?, ?)")) {
            state.setString(1, enrollment.getStudent().getEmail());
            state.setInt(2, contentId);
            state.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
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

    public int hasStudentProgress(String email, int contentID) {
        int count = 0;
        try (PreparedStatement query = this.connection.getConnection()
                .prepareStatement("SELECT COUNT(*)AS result FROM Progress WHERE StudentEmail = ? AND ContentID =?")) {
            query.setString(1, email);
            query.setInt(2, contentID);
            ResultSet set = query.executeQuery();
            set.next();
            count = set.getInt("result");
        } catch (Exception e) {
            // TODO: handle exception
            return count;
        }
        return count;
    }

    // Updates the enrollment
    @Override
    public void update(Enrollment domainObject) {
        // TODO Auto-generated method stub

    }

    // Delete method not being used.
    @Override
    public void delete(Enrollment domainObject) {
        // TODO Auto-generated method stub

    }

}