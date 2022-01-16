package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import domain.Gender;
import domain.Student;
import domain.Module;

public class StudentRepository extends Repository<Student> {

    @Override
    public void insert(Student student) {

        String insertStudent = "INSERT INTO Student VALUES(?,?,?,?,?)";
        // Retrieves cityID
        int cityId = getCityId(student);
        // If city does not exist(cityID =-1), it will create a new city and assigns the
        // new id to the cityID
        if (cityId == -1) {
            cityId = createCity(student);
        }

        int addressID = -1;
        // retrieves addressID
        int i = getAddressID(student, cityId);
        // If it exist, then it will assign the addressID to the student
        if (i != -1) {
            addressID = i;
        } else {
            // Else it will create another address and assign the AddressID
            createAddress(student, cityId);
            // Gets addressId
            addressID = getAddressID(student, cityId);
        }
        // String insertIntoAddress = "INSERT INTO Address VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement prepStatement = this.connection.getConnection().prepareStatement(insertStudent)) {
            prepStatement.setString(1, student.getEmail());
            prepStatement.setString(2, student.getStudentName());
            prepStatement.setObject(3, student.getDateOfBirth());
            prepStatement.setString(4, student.getGender().name());
            prepStatement.setInt(5, addressID);
            prepStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Method to insert into address table
    public void createAddress(Student student, int cityID) {

        final String insertAddress = "INSERT INTO Address(Street, HouseNumber, CityID, PostalCode)VALUES(?, ?, ?, ?)";
        try (PreparedStatement prepState = this.connection.getConnection().prepareStatement(insertAddress)) {
            prepState.setString(1, student.getStreet());
            prepState.setInt(2, student.getHouseNumber());
            prepState.setInt(3, cityID);
            prepState.setString(4, student.getPostalCode());
            prepState.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves cityId
    // If it exist, it will give id back
    // Not it returns -1
    public int getCityId(Student student) {
        int id = -1;
        String sql = "SELECT TOP 1 ID FROM City WHERE City = ? AND Country = ?";
        // Prepared statement
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            // input
            statement.setString(1, student.getCity());
            statement.setString(2, student.getCountry());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                id = set.getInt("ID");
            }
            return id;
        } catch (Exception e) {
            // Return -1;
            return -1;
        }

    }

    // Creates new city to the City table
    public int createCity(Student student) {

        String sql = "INSERT INTO City VALUES(?,?)";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            statement.setString(1, student.getCity());
            statement.setString(2, student.getCountry());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getCityId(student);
    }

    // Returns addressID if it exists
    // Otherwise it will give give back -1
    public int getAddressID(Student student, int cityId) {
        // id is -1 as default value
        int id = -1;
        String searchForAddressID = "SELECT TOP 1 AddressID FROM Address " +
                "WHERE Street = ? AND HouseNumber = ? AND CityID = ?" +
                " AND PostalCode = ?";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(searchForAddressID)) {
            statement.setString(1, student.getStreet());
            statement.setInt(2, student.getHouseNumber());
            statement.setInt(3, cityId);
            statement.setString(4, student.getPostalCode());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                id = set.getInt("AddressID");
            }
        } catch (SQLException e) {
            // If not, it will return default id, -1
            return id;
        }
        // If id is found, it will return the right addressID
        return id;

    }

    @Override
    public void update(Student student) {
        // Get cityId

        int cityID = getCityId(student);
        if (cityID == -1) {
            // Creates new city and assigns the ID it to the cityID
            cityID = createCity(student);
        }

        // Will get the address ID
        int addressID = getAddressID(student, cityID);

        // Query
        String updateQuery = "UPDATE Student SET Name = ?, Birthdate = ?, Gender = ?, AddressID = ? WHERE Email = ?";
        try (PreparedStatement prepQuery = this.connection.getConnection().prepareStatement(updateQuery)) {
            if (addressID == -1) {
                // In case the student changes address, the system will create a new address +
                // id
                createAddress(student, cityID);
                addressID = getAddressID(student, cityID);
            }
            prepQuery.setString(1, student.getStudentName());
            prepQuery.setObject(2, student.getDateOfBirth());
            prepQuery.setString(3, student.getGender().name());
            prepQuery.setInt(4, addressID);
            prepQuery.setString(5, student.getEmail());
            prepQuery.executeUpdate();
            deleteAddressWithoutResident();
            deleteCityWithoutAddress();
        } catch (SQLException e) {
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
            deleteCityWithoutAddress();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete address without resident.
    // It checks if address without resident exist. If present, it will delete
    // address.
    public void deleteAddressWithoutResident() {
        String deleteAddressQuery = "DELETE FROM Address WHERE NOT EXISTS (SELECT * FROM Student WHERE Student.AddressID = Address.AddressID)";
        try (PreparedStatement deleteAddress = this.connection.getConnection().prepareStatement(deleteAddressQuery)) {
            deleteAddress.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // If there are no foreign key relations between city and Address, it will
    // delete that particular city
    public void deleteCityWithoutAddress() {
        String deleteCity = "DELETE FROM City WHERE NOT EXISTS(SELECT * FROM Address WHERE City.ID = Address.CityID)";
        try (PreparedStatement delete = this.connection.getConnection().prepareStatement(deleteCity)) {
            delete.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Student> retrieve() {
        return new ArrayList<>();
    }

    // Retrieves name and email and put it in a Hashmap
    public List<String[]> retrieveNameByEmail() {
        List<String[]> nameList = new ArrayList<>();
        String retrieveQuery = "SELECT Name, Email FROM Student";
        try (PreparedStatement retrieveStatement = this.connection.getConnection().prepareStatement(retrieveQuery)) {

            ResultSet rS = retrieveStatement.executeQuery();
            while (rS.next()) {
                String[] nameAndEmail = { rS.getString("Name"), rS.getString("Email") };
                nameList.add(nameAndEmail);

            }

            return nameList;
        } catch (SQLException e) {
            e.printStackTrace();
            return nameList;
        }
    }

    public Student searchForForStudent(String email) {
        Student student = null;
        String studentQuery = "SELECT * FROM Student INNER JOIN Address ON Address.AddressID = Student.AddressID INNER JOIN City ON City.ID = Address.CityID WHERE Email = ?";
        try (PreparedStatement retrieveStudentByEmail = this.connection.getConnection()
                .prepareStatement(studentQuery)) {
            // Email in question
            retrieveStudentByEmail.setString(1, email);
            // Executes query
            ResultSet resultSet = retrieveStudentByEmail.executeQuery();
            // results
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
        } catch (SQLException e) {
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

    // Of an individual ContentItem, update the progress the Student has in it
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

    // Retrieves a list of emails
    public List<String> retrieveAllEmails() {
        try {
            ResultSet result = this.connection.getConnection().createStatement()
                    .executeQuery("SELECT Email FROM Student");
            ArrayList<String> studentEmails = new ArrayList<>();
            while (result.next()) {
                studentEmails.add(result.getString("Email"));
            }
            return studentEmails;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}