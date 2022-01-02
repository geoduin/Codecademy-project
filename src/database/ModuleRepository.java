package database;

import java.sql.Statement;
import java.time.LocalDate;
import java.lang.Thread.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Module;
import domain.Status;

public class ModuleRepository extends Repository<Module> {

    public ModuleRepository() {
        super();

    }

    @Override
    public void insert(Module module) {

        LocalDate date = module.getDate();
        Status status = module.getStatus();
        String title = module.getTitle();
        int version = module.getVersion();
        int trackingNumber = module.getPositionWithinCourse();
        String description = module.getDescription();
        String contactName = module.getContactName();
        String email = module.getEmailAddress();
        int contentID = 0;
        try {
            // Makes content item
            String contentItemQuery = "INSERT INTO ContentItem VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(contentItemQuery);

            preparedStatement.setString(1, date.toString());
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, status.name());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            // retrieves the correct content ID
            Statement statement = this.connection.getConnection().createStatement();
            ResultSet result = statement
                    .executeQuery("SELECT TOP 1 ContentID FROM ContentItem ORDER BY ContentID DESC");
            // Turns "result" into String value
            while (result.next()) {
                contentID = result.getInt("ContentID");
            }
            // Retrieves email from database
            String contactEmailQuery = "SELECT Email FROM Contact WHERE Email = '?'";
            preparedStatement = this.connection.getConnection().prepareStatement(contactEmailQuery);

            preparedStatement.setString(1, email);
            result = preparedStatement.executeQuery();
            preparedStatement.close();

            // Checks if email is NULL
            if (result.next() == false) {
                // Creates contact
                createContact(email, contactName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // A second try catch block was needed because query after the "if" statement
        // was being skipped over
        try {
            String query = "INSERT INTO Module VALUES (?, NULL, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, contentID);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, version);
            preparedStatement.setInt(4, trackingNumber);
            preparedStatement.setString(5, email);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createContact(String email, String contactName) {
        try {
            String query = "INSERT INTO Contact VALUES (?, ?)";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(query);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, contactName);

            preparedStatement.executeUpdate();
            preparedStatement.close();
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

        try {
            // Checks if contact with provided email exists, if not: creates new contact
            // with provided email
            String contactEmailQuery = "SELECT Email FROM Contact WHERE Email = '?'";
            PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(contactEmailQuery);

            preparedStatement.setString(1, contactEmail);

            ResultSet result = preparedStatement.executeQuery();
            preparedStatement.close();
            if (result.next() == false) {
                // Creates contact
                createContact(contactEmail, contactName);
            }
            // Gets correct content ID
            String contentIDQuery = "SELECT ContentID FROM Module WHERE Title = '?' AND Version = '?'";
            preparedStatement = this.connection.getConnection().prepareStatement(contentIDQuery);

            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, version);

            result = preparedStatement.executeQuery();
            preparedStatement.close();
            while (result.next()) {
                contentID = result.getInt("ContentID");
            }
            // Updates the relevant tables in the database
            String updateQuery = "UPDATE ContentItem SET Description = '?', Status = '?', WHERE ContentID = '?'";
            preparedStatement = this.connection.getConnection().prepareStatement(updateQuery);

            preparedStatement.setString(1, description);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3, contentID);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            String updateQuery2 = "UPDATE Module SET ContactEmail = '?', PositionInCourse = '?', HERE ContentID = '?'";
            preparedStatement = this.connection.getConnection().prepareStatement(updateQuery2);

            preparedStatement.setString(1, contactEmail);
            preparedStatement.setInt(2, trackingNumber);
            preparedStatement.setInt(3, contentID);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Module module) {
        try {
            String title = module.getTitle();
            int version = module.getVersion();
            Statement statement = this.connection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT ContentID FROM Module WHERE Title = '" + title + "' AND Version = '" + version + "'");
            System.out.println(result.toString());
            int contentID = 0;
            while (result.next()) {
                contentID = result.getInt("ContentID");
            }
            statement.executeUpdate("DELETE FROM ContentItem WHERE ContentID = '" + contentID + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            Statement statement = this.connection.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM ContentItem WHERE ContentID = " + id + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Module> retrieve() {
        try {
            Statement statement = this.connection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Module JOIN ContentItem ON Module.ContentID = ContentItem.ContentID JOIN Contact ON Module.ContactEmail = Contact.Email");
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
                modules.add(new Module(localDate, status, title, version, trackingNumber, description, contactName,
                        emailAddress));
            }

            return modules;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // To showcase distinct modules by name and version, a map is returned that
    // links the module ID to the version and title combination
    public HashMap<String, Integer> getAllModuleNames(Boolean notAssignedToACourse) {
        try {

            Statement statement = this.connection.getConnection().createStatement();
            ResultSet resultSet;
            if (notAssignedToACourse) {
                resultSet = statement
                        .executeQuery("SELECT Title, Version , ContentID FROM Module WHERE CourseName IS NULL");
            } else {
                resultSet = statement.executeQuery("SELECT Title, Version , ContentID FROM Module");
            }
            HashMap<String, Integer> modules = new HashMap<>();
            while (resultSet.next()) {
                String key = resultSet.getString("Title") + " (Versie: " + resultSet.getInt("Version") + ")";
                modules.put(key, resultSet.getInt("ContentID"));
            }
            return modules;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Module retrieveModuleByID(int ID) {
        LocalDate date = null;
        Status status = null;
        String title = "";
        int version = 0;
        int positionWithinCourse = 0;
        String description = "";
        String contactName = "";
        String emailAddress = "";

        try {
            Statement statement = this.connection.getConnection().createStatement();
            ResultSet moduleRetrieve = statement.executeQuery("SELECT * FROM Module WHERE ContentID = " + ID + "");

            while (moduleRetrieve.next()) {
                title = moduleRetrieve.getString("Title");
                version = moduleRetrieve.getInt("Version");
                positionWithinCourse = moduleRetrieve.getInt("PositionInCourse");
                emailAddress = moduleRetrieve.getString("ContactEmail");
            }

            try {
                statement = this.connection.getConnection().createStatement();
                ResultSet contentItemRetrieve = statement.executeQuery(
                        "SELECT CreationDate, Status, Description FROM ContentItem WHERE ContentID = " + ID + "");

                while (contentItemRetrieve.next()) {
                    date = LocalDate.parse(contentItemRetrieve.getString("CreationDate"));
                    status = Status.valueOf(contentItemRetrieve.getString("Status"));
                    description = contentItemRetrieve.getString("Description");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

            try {
                statement = this.connection.getConnection().createStatement();
                ResultSet contactRetrieve = statement
                        .executeQuery("SELECT * FROM Contact WHERE Email = '" + emailAddress + "'");

                while (contactRetrieve.next()) {
                    contactName = contactRetrieve.getString("Name");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

            Module module = new Module(date, status, title, version, positionWithinCourse, description, contactName,
                    emailAddress);
            return module;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to link a module with a course
    public void assignModuleToCourse(String courseName, int id) {
        try {
            String sql = "UPDATE Module SET CourseName = ? WHERE ContentID = ?";
            PreparedStatement statement = this.connection.getConnection().prepareStatement(sql);

            statement.setString(1, courseName);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}