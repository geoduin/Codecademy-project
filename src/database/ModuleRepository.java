package database;

import java.sql.Statement;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import domain.Module;
import domain.Status;

public class ModuleRepository extends Repository<Module> {

    public ModuleRepository() {
        super();
    }

    /*
     * Insert a new module into the database, based of an instance of module as
     * parameter
     */
    @Override
    public void insert(Module module) {
        // Collecting all required values into variables.
        LocalDate date = module.getDate();
        Status status = module.getStatus();
        String title = module.getTitle();
        int version = module.getVersion();
        int trackingNumber = module.getPositionWithinCourse();
        String description = module.getDescription();
        String contactName = module.getContactName();
        String email = module.getEmailAddress();

        /*
         * ContentID (from the ContentItem table) needs to be used when inserting into
         * the
         * Module table. See relational model to overview the relationship
         * between the two tables
         */
        int contentID = 0;

        // Creating the ContentItem part first
        String contentItemQuery = "INSERT INTO ContentItem VALUES (?, ?, ?, ?)";
        try (PreparedStatement contenItemStatement = this.connection.getConnection()
                .prepareStatement(contentItemQuery)) {
            // Query arguments
            contenItemStatement.setString(1, title);
            contenItemStatement.setString(2, description);
            contenItemStatement.setString(3, date.toString());
            contenItemStatement.setString(4, status.toString());

            contenItemStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Fetching the newly created ContentID to use in the Module table insert
        try (Statement statement = this.connection.getConnection().createStatement()) {
            ResultSet result = statement
                    .executeQuery("SELECT TOP 1 ContentID FROM ContentItem ORDER BY ContentID DESC");
            // Turns "result" into String value
            while (result.next()) {
                contentID = result.getInt("ContentID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // The contact table is involved to check wether the user given email already
        // exists
        String contactEmailQuery = "SELECT Email FROM Contact WHERE Email = ?";
        try (PreparedStatement contactEmailStatement = this.connection.getConnection()
                .prepareStatement(contactEmailQuery)) {

            contactEmailStatement.setString(1, email);
            ResultSet result = contactEmailStatement.executeQuery();

            // If the email does not exist, a new Contact record will be created
            if (result.next() == false) {
                createContact(email, contactName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Now that the involved ContentItem and Contact tables have been sorted out,
        // the final insert into the Module table is done
        String query = "INSERT INTO Module VALUES (?, NULL, ?, ?, ?)";
        try (PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query)) {
            // query arguments
            preparedStatement.setInt(1, contentID);
            preparedStatement.setInt(2, version);
            preparedStatement.setInt(3, trackingNumber);
            preparedStatement.setString(4, email);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // A contact within a Module is a separate table 'Contact'. Method to insert a
    // new contact
    private void createContact(String email, String contactName) {
        String query = "INSERT INTO Contact VALUES (?, ?)";
        try (PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, contactName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Updates module in database
    @Override
    public void update(Module module) {
        // Collection of needed variables
        String description = module.getDescription();
        String status = module.getStatus().toString();
        int trackingNumber = module.getPositionWithinCourse();
        String contactEmail = module.getEmailAddress();
        String contactName = module.getContactName();
        String title = module.getTitle();
        int version = module.getVersion();
        int contentID = 0;
        // Checks if contact with provided email exists, if not: creates new contact
        // with provided email
        String contactEmailQuery = "SELECT Email FROM Contact WHERE Email = ?";
        try (PreparedStatement preparedStatement = this.connection.getConnection()
                .prepareStatement(contactEmailQuery)) {

            preparedStatement.setString(1, contactEmail);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next() == false) {
                createContact(contactEmail, contactName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Gets the ContentItem which is necessary to create a record in the Module
        // table
        String contentIDQuery = "SELECT m.ContentID FROM Module m JOIN ContentItem c ON c.ContentID = m.ContentID WHERE Title = ? AND Version = ?";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(contentIDQuery)) {

            statement.setString(1, title);
            statement.setInt(2, version);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                contentID = result.getInt("ContentID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Updates the two tables related to a Module in the database 1/2
        String updateQuery = "UPDATE ContentItem SET Description = ?, Status = ? WHERE ContentID = ?";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(updateQuery)) {
            statement.setString(1, description);
            statement.setString(2, status);
            statement.setInt(3, contentID);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Updates the two tables related to a Module in the database 2/2
        String updateQuery2 = "UPDATE Module SET ContactEmail = ?, PositionInCourse = ? WHERE ContentID = ?";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(updateQuery2)) {
            statement.setString(1, contactEmail);
            statement.setInt(2, trackingNumber);
            statement.setInt(3, contentID);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // The identifiers (title and version) is retrieved from the method argument
    // (instance of Module) and used to delete the matching record from the database
    @Override
    public void delete(Module module) {
        String sql = "SELECT m.ContentID FROM Module m JOIN ContentItem c ON c.ContentID = m.ContentID WHERE Title = ? AND Version = ?";

        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            // Module identifiers
            String title = module.getTitle();
            int version = module.getVersion();

            statement.setString(1, title);
            statement.setInt(2, version);

            // Because Module is also a ContentItem in the database, the ContentID has to
            // retrieved to perform a cascade delete on it.
            ResultSet result = statement.executeQuery();
            int contentID = 0;
            while (result.next()) {
                contentID = result.getInt("ContentID");
            }
            statement.executeUpdate("DELETE FROM ContentItem WHERE ContentID = " + contentID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // An alternative delete method, this time via the ContentID. Sends true or
    // false so that the user will be informed if the module is linked to a course,
    public boolean delete(int id) {
        try (Statement statement = this.connection.getConnection().createStatement()) {
            statement.executeUpdate(
                    "DELETE FROM ContentItem WHERE ContentID IN (Select ContentID FROM Module WHERE ContentID = " + id
                            + " AND CourseName IS NULL)");

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    // Instantiate all existing Modules, based on the involved tables: ContentItem,
    // Module and Contact
    @Override
    public ArrayList<Module> retrieve() {
        try (Statement statement = this.connection.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(
                    "SELECT *, m.ContentID FROM Module m JOIN ContentItem ON m.ContentID = ContentItem.ContentID JOIN Contact ON m.ContactEmail = Contact.Email");
            ArrayList<Module> modules = new ArrayList<>();

            while (result.next()) {
                LocalDate localDate = LocalDate.parse(result.getString("CreationDate"));
                Status status = Status.valueOf(result.getString("Status"));
                String title = result.getString("Title");
                int version = result.getInt("Version");
                int trackingNumber = result.getInt("PositionInCourse");
                String description = result.getString("Description");
                String contactName = result.getString("Name");
                String emailAddress = result.getString("Email");
                String nameOfRelatedCourse = result.getString("CourseName");
                int contentID = result.getInt("Module.ContentID");
                modules.add(new Module(localDate, status, title, version, trackingNumber, description, contactName,
                        emailAddress, nameOfRelatedCourse, contentID));
            }

            return modules;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * To showcase distinct modules by the combination of name and version (their
     * identifier), a map is returned that
     * links the module id to the version and title combination
     */
    public Map<String, Integer> getAllModuleNames(Boolean notAssignedToACourse) {
        HashMap<String, Integer> modules = new HashMap<>();
        try (Statement statement = this.connection.getConnection().createStatement()) {

            ResultSet resultSet;

            // The method can also be used to only select modules which do not have a
            // relation with a course yet
            if (Boolean.TRUE.equals(notAssignedToACourse)) {
                resultSet = statement
                        .executeQuery(
                                "SELECT Title, Version , m.ContentID FROM Module m JOIN ContentItem c ON c.ContentID = m.ContentID WHERE CourseName IS NULL");
            } else {
                resultSet = statement.executeQuery(
                        "SELECT Title, Version , m.ContentID FROM Module m JOIN ContentItem c ON c.ContentID = m.ContentID ");
            }

            /*
             * Saving the Module title and version combination as a key which hold their ID.
             * That way, the map can used to quickly perform other Module database
             * actions
             * with their ID and thus less code is necessary
             */
            while (resultSet.next()) {
                String key = resultSet.getString("Title") + " (Versie: " + resultSet.getInt("Version") + ")";
                modules.put(key, resultSet.getInt("ContentID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    // An overloaded variant that gives the modules which are linked to a specific
    // course
    public Map<String, Integer> getAllModuleNames(String courseName) {
        HashMap<String, Integer> modules = new HashMap<>();

        String sql = "SELECT Title, Version , m.ContentID FROM Module m JOIN ContentItem c ON c.ContentID = m.ContentID WHERE CourseName = ?";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();

            // Using the same map method as explained in the previous method
            while (resultSet.next()) {
                String key = resultSet.getString("Title") + " (Version: " + resultSet.getInt("Version") + ")";
                modules.put(key, resultSet.getInt("ContentID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    // Instantiate one Module from the database based on its ID
    public Module retrieveModuleByID(int id) {
        LocalDate date = null;
        Status status = null;
        String title = "";
        int version = 0;
        int positionWithinCourse = 0;
        String description = "";
        String contactName = "";
        String emailAddress = "";
        String nameOfRelatedCourse = null;
        int cID = 0;

        // Retrieve from the ContentItem and Module table
        try (Statement statement = this.connection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT *, m.ContentID AS cID FROM Module m JOIN ContentItem c ON m.ContentID = c.ContentID WHERE c.ContentID = "
                            + id);

            while (resultSet.next()) {
                title = resultSet.getString("Title");
                version = resultSet.getInt("Version");
                positionWithinCourse = resultSet.getInt("PositionInCourse");
                emailAddress = resultSet.getString("ContactEmail");
                date = (LocalDate.parse(resultSet.getString("CreationDate")));
                status = Status.valueOf(resultSet.getString("Status"));
                description = resultSet.getString("Description");
                nameOfRelatedCourse = resultSet.getString("CourseName");
                cID = resultSet.getInt("cID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Retrieve from the Contact table to get the contact's name
        String sql = ("SELECT * FROM Contact WHERE Email = ?");
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            statement.setString(1, emailAddress);

            ResultSet contactRetrieve = statement.executeQuery();

            while (contactRetrieve.next()) {
                contactName = contactRetrieve.getString("Name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Module(date, status, title, version, positionWithinCourse, description, contactName,
                emailAddress, nameOfRelatedCourse, cID);
    }

    // Linking a module with a course
    public void assignModuleToCourse(String courseName, int id) {
        String sql = "UPDATE Module SET CourseName = ? WHERE ContentID = ?";
        try (PreparedStatement statement = this.connection.getConnection().prepareStatement(sql)) {
            statement.setString(1, courseName);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to unlink a module from whatever course it has a relationship with
    public void unassignModuleToCourse(int id) {
        try (Statement statement = this.connection.getConnection().createStatement()) {
            statement.executeUpdate("UPDATE Module SET CourseName = NULL WHERE ContentID = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}