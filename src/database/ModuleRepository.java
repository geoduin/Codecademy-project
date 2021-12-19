package database;

import java.sql.Statement;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import domain.Module;
import domain.Status;

public class ModuleRepository extends Repository {

    public ModuleRepository() {
        super();

    }

    @Override
    public void insert(Object domainObject) {
        if (domainObject instanceof Module) {
            Module module = (Module) domainObject;
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
                Statement statement = this.connection.getConnection().createStatement();
                statement.executeUpdate(
                        ("INSERT INTO ContentItem VALUES('" + date + "', '" + description + "', '" + status + "')"));
                // retrieves the correct content ID
                ResultSet result = statement
                        .executeQuery("SELECT TOP 1 ContentID FROM ContentItem ORDER BY ContentID DESC");
                // Turns "result" into String value
                while (result.next()) {
                    contentID = result.getInt("ContentID");
                }
                // Retrieves email from database
                result = statement.executeQuery("SELECT Email FROM Contact WHERE Email = '" + email + "'");
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
                Statement statement = this.connection.getConnection().createStatement();
                statement.executeUpdate("INSERT INTO Module VALUES (" + contentID + ", NULL, '" + title + "', "
                        + version + ", " + trackingNumber + ", '" + email + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createContact(String email, String contactName) {
        try {
            Statement statement = this.connection.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO Contact VALUES ('" + email + "', '" + contactName + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Updates module in database
    @Override
    public void update(Object domainObject) {
        if (domainObject instanceof Module) {
            // Collection of needed variables
            Module module = (Module) domainObject;
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
                Statement statement = this.connection.getConnection().createStatement();
                ResultSet result = statement
                        .executeQuery("SELECT Email FROM Contact WHERE Email = '" + contactEmail + "'");
                if (result.next() == false) {
                    // Creates contact
                    createContact(contactEmail, contactName);
                }
                // Gets correct content ID
                result = statement.executeQuery(
                        "SELECT ContentID FROM Module WHERE Title = '" + title + "' AND Version = " + version + "");
                while (result.next()) {
                    contentID = result.getInt("ContentID");
                }
                // Updates the relevant tables in the database
                statement.executeUpdate("UPDATE ContentItem SET Description = '" + description + "', Status = '"
                        + status + "' WHERE ContentID = " + contentID + "");
                statement.executeUpdate("UPDATE Module SET ContactEmail = '" + contactEmail + "', OrderNumber = "
                        + trackingNumber + " WHERE ContentID = " + contentID + "");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Object domainObject) {
        if (domainObject instanceof Module) {
            Module module = (Module) domainObject;
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
    }

    @Override
    public ArrayList<Object> retrieve() {
        try {
            Statement statement = this.connection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM Module JOIN ContentItem ON Module.ContentID = ContentItem.ContentID JOIN Contact ON Module.ContactEmail = Contact.Email");
            ArrayList<Object> modules = new ArrayList<>();

            while (result.next()) {
                LocalDate localDate = LocalDate.parse(result.getString("CreationDate"));
                Status status = Status.valueOf(result.getString("Status"));
                String title = result.getString("Title");
                int version = result.getInt("Version");
                int trackingNumber = result.getInt("OrderNumber");
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
}