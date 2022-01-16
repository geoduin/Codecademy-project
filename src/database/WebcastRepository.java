package database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import domain.Status;
import domain.Webcast;

public class WebcastRepository extends Repository<Webcast> {

    // Inserts a webcast into the database
    @Override
    public void insert(Webcast domainObject) {
        // initializes connection
        Connection connection = this.connection.getConnection();
        // creating contentID variable to use outside the try_catch block
        int contentID = -1;
        int speakerID = -1;
        // Creates contentItem in database and retrieves the content ID
        try {

            // Creates new speaker if the speaker doesn't already exist, creates
            // organization if organization doesn't already exist
            PreparedStatement checkSpeaker = connection
                    .prepareStatement("SELECT * FROM Speaker WHERE Name = ? AND OrganizationName = ?");
            checkSpeaker.setString(1, domainObject.getSpeaker());
            checkSpeaker.setString(2, domainObject.getOrganization());
            ResultSet speakerResult = checkSpeaker.executeQuery();

            // Returns true if the speaker doesn't exist
            if (speakerResult.next() == false) {

                // creates speaker
                PreparedStatement speakerCreator = connection.prepareStatement("INSERT INTO Speaker Values(?, ?)");
                speakerCreator.setString(1, domainObject.getSpeaker());
                speakerCreator.setString(2, domainObject.getOrganization());
                speakerCreator.executeUpdate();

            }

            PreparedStatement contentItemInserter = connection
                    .prepareStatement("INSERT INTO ContentItem VALUES(?, ?, ?, ?)");
            // Setting prepared statement variables
            contentItemInserter.setString(1, domainObject.getTitle());
            contentItemInserter.setString(2, domainObject.getDescription());
            contentItemInserter.setString(3, LocalDate.now().toString());
            contentItemInserter.setString(4, domainObject.getStatus().toString());
            contentItemInserter.executeUpdate();

            // retrieves ID of newly created ContentItem
            Statement getContentIDFromContentItem = connection.createStatement();
            ResultSet result = getContentIDFromContentItem
                    .executeQuery("SELECT TOP 1 ContentID FROM ContentItem ORDER BY ContentID DESC");
            while (result.next()) {
                contentID = result.getInt("ContentID");
            }

            // Retrieves speakerID
            PreparedStatement speakerIDRetriever = connection
                    .prepareStatement("SELECT ID FROM Speaker WHERE Name = ? AND OrganizationName = ?");
            speakerIDRetriever.setString(1, domainObject.getSpeaker());
            speakerIDRetriever.setString(2, domainObject.getOrganization());
            ResultSet idRSet = speakerIDRetriever.executeQuery();

            while (idRSet.next()) {
                speakerID = idRSet.getInt("ID");
            }

            // creates webcast in database
            PreparedStatement webcastCreator = connection
                    .prepareStatement(
                            "INSERT INTO Webcast (ContentID, URL, Duration, Views, SpeakerID) VALUES(?, ?, ?, ?, ?)");
            webcastCreator.setInt(1, contentID);
            webcastCreator.setString(2, domainObject.getUrl());
            webcastCreator.setInt(3, domainObject.getDurationInMinutes());
            webcastCreator.setInt(4, domainObject.getView());
            webcastCreator.setInt(5, speakerID);
            webcastCreator.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // deletes content item if there is an error in creating a webcast
            try {
                PreparedStatement deleter = connection.prepareStatement("DELETE FROM ContentItem WHERE ContentID = ?");
                deleter.setInt(1, contentID);
                deleter.executeUpdate();

            } catch (SQLException webcastException) {
                webcastException.printStackTrace();
            }

        }
    }

    // Updates the webcast given as parameter inside the database
    @Override
    public void update(Webcast domainObject) {
        Connection connection = this.connection.getConnection();

        try {

            PreparedStatement updateContentItem = connection.prepareStatement(
                    "UPDATE ContentItem SET Title = ? , Description = ? , Status = ? WHERE ContentID = ?");

            updateContentItem.setString(1, domainObject.getTitle());
            updateContentItem.setString(2, domainObject.getDescription());
            updateContentItem.setString(3, domainObject.getStatus().toString());
            updateContentItem.setInt(4, getIDFromURL(domainObject.getUrl()));
            updateContentItem.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //
    public void updateURL(String initialURL, String newURL) {
        Connection connection = this.connection.getConnection();
        try {
            PreparedStatement updateURL = connection.prepareStatement("UPDATE Webcast SET URL = ? WHERE URL = ?");
            updateURL.setString(1, newURL);
            updateURL.setString(2, initialURL);
            updateURL.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateViews(String url, int views) {
        Connection connection = this.connection.getConnection();
        try (PreparedStatement updateView = connection.prepareStatement("UPDATE Webcast SET Views = ? WHERE URL = ?")) {
            updateView.setInt(1, views);
            updateView.setString(2, url);
            updateView.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Webcast domainObject) {
        Connection connection = this.connection.getConnection();
        try {
            PreparedStatement deleteWebcast = connection
                    .prepareStatement("DELETE FROM ContentItem WHERE ContentID = ?");
            deleteWebcast.setInt(1, domainObject.getID());
            deleteWebcast.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // not usefull for webcast
    @Override
    public ArrayList<Webcast> retrieve() {

        return null;
    }

    // result set will be empty if the title of a module is given
    public Webcast retrieveByTitle(String webcastTitle) {
        Connection connection = this.connection.getConnection();
        // retrieves all values needed to create a webcast object based on the (unique)
        // URL.
        try {
            PreparedStatement retrieveByTitle = connection.prepareStatement(
                    "SELECT Title, Speaker.Name, Speaker.OrganizationName, Duration, URL, Status, CreationDate, Description, Views, Webcast.ContentID, Webcast.SpeakerID FROM Webcast JOIN ContentItem ON ContentItem.ContentID = Webcast.ContentID JOIN Speaker ON Webcast.SpeakerID = Speaker.ID WHERE Title = ?");
            retrieveByTitle.setString(1, webcastTitle);
            ResultSet result = retrieveByTitle.executeQuery();
            while (result.next()) {
                String title = result.getString("Title");
                String speaker = result.getString("Name");
                String organization = result.getString("OrganizationName");
                int durationInMinutes = result.getInt("Duration");
                Status status = Status.valueOf(result.getString("Status"));
                LocalDate date = LocalDate.parse(result.getString("CreationDate"));
                String description = result.getString("Description");
                String url = result.getString("URL");
                int view = result.getInt("Views");
                int contentID = result.getInt("ContentID");
                return new Webcast(title, speaker, organization, durationInMinutes, url, status, date,
                        description, view, contentID);
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    // returns a hashmap with the webcast URL as key and the webcast title as value.
    public ArrayList<String> getAllWebcastNames() {
        ArrayList<String> webcasts = new ArrayList<>();

        try {
            Statement statement = this.connection.getConnection().createStatement();
            ResultSet webcastResult = statement.executeQuery(
                    "SELECT Title FROM Webcast JOIN ContentItem ON Webcast.ContentID = ContentItem.ContentID");
            while (webcastResult.next()) {
                String title = webcastResult.getString("Title");
                webcasts.add(title);
            }
            return webcasts;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public int getIDFromURL(String url) {
        try {
            Connection connection = this.connection.getConnection();
            PreparedStatement getID = connection.prepareStatement("SELECT ContentID FROM Webcast WHERE URL = ?");
            getID.setString(1, url);
            ResultSet idResult = getID.executeQuery();
            int id = -1;
            while (idResult.next()) {
                id = idResult.getInt("ContentID");
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // retrieves the ID of the Webcast from the URL alternate key and returns it.
    public String getTitleFromContentID(int contentID) {
        String title = "";
        String query = "SELECT Title FROM ContentItem WHERE ContentID = " + contentID + "";
        try (Statement statement = this.connection.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                title = result.getString("Title");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return title;
    }

}