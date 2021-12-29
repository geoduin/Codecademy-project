package database;

import java.sql.Statement;
import java.time.LocalDate;
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
    public void insert(Module domainObject) {

        Module module = domainObject;
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
    public void update(Module domainObject) {

        // Collection of needed variables
        Module module = domainObject;
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
            ResultSet result = statement.executeQuery("SELECT Email FROM Contact WHERE Email = '" + contactEmail + "'");
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
            statement.executeUpdate("UPDATE ContentItem SET Description = '" + description + "', Status = '" + status
                    + "' WHERE ContentID = " + contentID + "");
            statement.executeUpdate("UPDATE Module SET ContactEmail = '" + contactEmail + "', PositionInCourse = "
                    + trackingNumber + " WHERE ContentID = " + contentID + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Module domainObject) {
        Module module = domainObject;
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

    public HashMap<String, Integer> getAllModuleNames() {
        try {

            Statement statement = this.connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Title, Version ,ContentID FROM Module");
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
}