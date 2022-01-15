package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Gender;
import domain.Student;
import domain.Webcast;
import domain.Module;

public class StudentRepository extends Repository<Student> {

    @Override
    public void insert(Student student) {

        String insertStudent = "INSERT INTO Student VALUES(?,?, ? ,?,?)";
        int addresID = -1;
        int i = getAddressID(student);
        // If it exist, then it will asign the addressID to the student
        if (i != -1) {
            addresID = i;
        } else {
            // Else it will create another address and assign the AddressID
            createAddress(student);
            addresID = getAddressID(student);
        }
        // String insertIntoAddres = "INSERT INTO Address VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement prepStatement = this.connection.getConnection().prepareStatement(insertStudent)) {
            prepStatement.setString(1, student.getEmail());
            prepStatement.setString(2, student.getStudentName());
            prepStatement.setObject(3, student.getDateOfBirth());
            prepStatement.setString(4, student.getGender().toString());
            prepStatement.setInt(5, addresID);
            prepStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createAddress(Student student) {
        final String insertAddress = "INSERT INTO Address VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement prepState = this.connection.getConnection().prepareStatement(insertAddress)) {
            prepState.setString(1, student.getstreet());
            prepState.setInt(2, student.getHouseNumber());
            prepState.setString(3, student.getCity());
            prepState.setString(4, student.getCountry());
            prepState.setString(5, student.getPostalCode());
            prepState.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getAddressID(Student student) {
        int id = -1;
        String searchForAddressID = "SELECT TOP 1 AddressID FROM Address " +
                "WHERE Street = ? AND HouseNumber = ? AND City = ? AND Country = ?" +
                " AND PostalCode = ? ORDER BY AddressID ASC";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(searchForAddressID)) {
            statement.setString(1, student.getstreet());
            statement.setInt(2, student.getHouseNumber());
            statement.setString(3, student.getCity());
            statement.setString(4, student.getCountry());
            statement.setString(5, student.getPostalCode());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                id = set.getInt("AddressID");
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void update(Student student) {
        // Will get the address ID
        int addressID = getAddressID(student);
        String updateQuery = "UPDATE Student SET Name = ?, Birthdate = ?, Gender = ?, AddressID = ? WHERE Email = ?";
        try (PreparedStatement prepQuery = this.connection.getConnection().prepareStatement(updateQuery)) {
            if (addressID == -1) {
                // In case the student changes address, the system will create a new address +
                // id
                createAddress(student);
                addressID = getAddressID(student);
            }
            prepQuery.setString(1, student.getStudentName());
            prepQuery.setObject(2, student.getDateOfBirth());
            prepQuery.setString(3, student.getGender().toString());
            prepQuery.setInt(4, addressID);
            prepQuery.setString(5, student.getEmail());
            prepQuery.executeUpdate();
            deleteAddressWithoutResident();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Student student) {
        String deleteQuery = "DELETE FROM Student WHERE Email = ?";
        try (PreparedStatement studentRemoval = this.connection.getConnection().prepareStatement(deleteQuery)) {
            studentRemoval.setString(1, student.getEmail());
            studentRemoval.executeUpdate();
            deleteAddressWithoutResident();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAddressWithoutResident() {
        String deleteAddressQuery = "DELETE FROM Address WHERE NOT EXISTS (SELECT * FROM Student WHERE Student.AddressID = Address.AddressID)";
        try (PreparedStatement deleteAddress = this.connection.getConnection().prepareStatement(deleteAddressQuery)) {
            deleteAddress.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Student> retrieve() {
        return new ArrayList<>();
    }

    public Map<String, String> retrieveNameByEmail() {
        Map<String, String> nameList = new HashMap<>();
        String retrieveQuery = "SELECT Name, Email FROM Student";
        try (PreparedStatement retrieveStatement = this.connection.getConnection().prepareStatement(retrieveQuery)) {
            int i = 1;
            ResultSet rS = retrieveStatement.executeQuery();
            while (rS.next()) {
                nameList.put("(" + Integer.toString(i) + ") " + rS.getString("Name"), rS.getString("Email"));
                i++;
            }

            return nameList;
        } catch (SQLException e) {
            e.printStackTrace();
            return nameList;
        }
    }

    public Student searchForForStudent(String email) {
        Student student = null;
        String studentQuery = "SELECT * FROM Student INNER JOIN Address ON Address.AddressID = Student.AddressID WHERE Email = ?";
        try (PreparedStatement retrieveStudentByEmail = this.connection.getConnection()
                .prepareStatement(studentQuery)) {
            // Email in question
            retrieveStudentByEmail.setString(1, email);
            // Executes query
            ResultSet resultSet = retrieveStudentByEmail.executeQuery();
            // resultset results
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                LocalDate dateOfBirth = LocalDate.parse(resultSet.getString("Birthdate"));
                Gender gender = Gender.valueOf(resultSet.getString("Gender"));
                String street = resultSet.getString("Street");
                int houseNr = resultSet.getInt("HouseNumber");
                String city = resultSet.getString("City");
                String country = resultSet.getString("Country");
                String postalCode = resultSet.getString("PostalCode");
                student = new Student(name, gender, email, dateOfBirth, street, houseNr, postalCode, country, city);
            }
            // Returns a student
            return student;
        } catch (Exception e) {
            e.printStackTrace();
            return student;
        }
    }

    /*
     * Retrieving all the Modules with its progress, related to the Courses in which
     * the
     * Student is enrolled. Returning it as a map, where each module
     * instance is a key, that holds the progression amount as a value
     */
    public Map<Module, Integer> retrieveAllModuleProgressOfStudent(Student student) {
        Map<Module, Integer> modulesWithProgressMap = new HashMap<>();
        final ModuleRepository moduleRepository = new ModuleRepository();

        // Setting the query to get all Modules Content ID's and related progression
        // value
        String sql = "SELECT m.ContentID, Percentage FROM Progress p JOIN Module m ON m.ContentID = p.ContentID WHERE StudentEmail = ? ORDER BY m.PositionInCourse";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            // Query execution
            statement.setString(1, student.getEmail());
            ResultSet resultSet = statement.executeQuery();
            // Instantiating Modules from the results, and putting as value in a map,
            // setting its related progression amount as a key
            while (resultSet.next()) {
                Module module = moduleRepository.retrieveModuleByID(resultSet.getInt("ContentID"));
                int progressPercentage = resultSet.getInt("Percentage");
                modulesWithProgressMap.put(module, progressPercentage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modulesWithProgressMap;
    }

    // Of an individual ContenItem, update the progress the Student has in it
    public void updateProgressOfContentItem(int contentID, String studentEmail, int newAmount) {

        String sql = "UPDATE Progress SET Percentage = ? WHERE ContentID = ? AND StudentEmail = ?";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            // Setting the amount of progress between 0 - 100
            statement.setInt(1, newAmount);
            statement.setInt(2, contentID);
            statement.setString(3, studentEmail);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Retrieving all the Webcasts with its progress, that have a relation with the
     * Student (given as argument) in the Progress table. Returning it as a map,
     * where each Webcast (which contrary to a module, is unique) is a key, that
     * holds the progression amount as a value
     */
    public Map<String, Integer> retrieveAllWebcastProgressOfStudent(String studentEmail) {
        Map<String, Integer> webcastsWithProgressMap = new HashMap<>();

        // Setting the query to get all Webcasts title's and related progression
        // value
        String sql = "SELECT Title, Percentage FROM Progress p JOIN Webcast w ON w.ContentID = p.ContentID JOIN ContentItem co ON co.ContentID = w.ContentID WHERE StudentEmail = ?";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            // Query execution
            statement.setString(1, studentEmail);
            ResultSet resultSet = statement.executeQuery();
            // Setting all Webcasts title's in a map, setting each progression amount as map
            // value
            while (resultSet.next()) {
                String webcastTitle = resultSet.getString("Title");
                int progressPercentage = resultSet.getInt("Percentage");
                webcastsWithProgressMap.put(webcastTitle, progressPercentage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return webcastsWithProgressMap;
    }

    // With the Student key, retrieve all webcasts that exists, but are not in
    // linked with the student in the progress table
    public List<String> getWebcastTitlesForStudentThatHaveNoProgressRelation(String studentEmail) {
        ArrayList<String> webcastNames = new ArrayList<>();

        String sql = "SELECT * FROM ContentItem co JOIN Webcast w ON w.ContentID = co.ContentID WHERE w.ContentID NOT IN (SELECT ContentID FROM Progress WHERE StudentEmail = ?)";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            statement.setString(1, studentEmail);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                webcastNames.add(resultSet.getString("Title"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return webcastNames;
    }


    public List<String> retrieveAllEmails() { 
        try {
            ResultSet result = this.connection.getConnection().createStatement().executeQuery("SELECT Email FROM Student");
            ArrayList<String> studentEmails = new ArrayList<>();
            while(result.next()) { 
                studentEmails.add(result.getString("Email"));
            }
            return studentEmails;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}