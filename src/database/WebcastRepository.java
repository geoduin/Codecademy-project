package database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Status;
import domain.Webcast;

public class WebcastRepository extends Repository<Webcast> {

    @Override
    public void insert(Webcast domainObject) {
        // initializes connection
        Connection connection = this.connection.getConnection();
        // creating contentID variable to use outside the trycatch block
        int contentID = -1;
        // Creates contentItem in database and retrieves the content ID
        try {

            // Creates new speaker if the speaker doesn't already exist, creates
            // organization if organization doesn't already exist
            PreparedStatement checkSpeaker = connection.prepareStatement("SELECT * FROM Speaker WHERE Name = ? AND OrganizationName = ?");
            checkSpeaker.setString(1, domainObject.getSpeaker());
            checkSpeaker.setString(2, domainObject.getOrganization());
            ResultSet speakerResult = checkSpeaker.executeQuery();
            // Returns true if the speaker doesn't exist
            if (speakerResult.next() == false) {

                PreparedStatement checkOrg = connection.prepareStatement("SELECT * FROM Organization WHERE Name = ?");
                checkOrg.setString(1, domainObject.getOrganization());
                ResultSet organization = checkOrg.executeQuery();
                // returns true if the organization does not exist
                if (organization.next() == false) {
                    // creates organization
                    PreparedStatement organizationCreator = connection.prepareStatement("INSERT INTO Organization VALUES(?)");
                    organizationCreator.setString(1, domainObject.getOrganization());
                    organizationCreator.executeUpdate();
                }
                // creates speaker
                PreparedStatement speakerCreator = connection.prepareStatement("INSERT INTO Speaker Values(?, ?)");
                speakerCreator.setString(1, domainObject.getSpeaker());
                speakerCreator.setString(2, domainObject.getOrganization());
                speakerCreator.executeUpdate();

            }

            PreparedStatement contentItemInserter = connection.prepareStatement("INSERT INTO ContentItem VALUES(?, ?, ?, ?)");
            // Setting prepared statement variables
            contentItemInserter.setString(1, domainObject.getTitle());
            contentItemInserter.setString(2, domainObject.getDescription());
            contentItemInserter.setString(3, LocalDate.now().toString());
            contentItemInserter.setString(4, domainObject.getStatus().toString());
            contentItemInserter.executeUpdate();

            //retrieves ID of newly created ContentItem
            Statement getContentIDFromContentItem = connection.createStatement();
            ResultSet result = getContentIDFromContentItem.executeQuery("SELECT TOP 1 ContentID FROM ContentItem ORDER BY ContentID DESC");
            while (result.next()) {
                contentID = result.getInt("ContentID");
            }

            
            // creates webcast in database
            PreparedStatement webcastCreator = connection.prepareStatement("INSERT INTO Webcast VALUES(?, ?, ?, ?)");
            webcastCreator.setInt(1, contentID);
            webcastCreator.setString(2, domainObject.getUrl());
            webcastCreator.setInt(3, domainObject.getDurationInMinutes());
            webcastCreator.setString(4, domainObject.getSpeaker());
            webcastCreator.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //deletes content item if there is an error in creating a webcast
            try {
                PreparedStatement deleter = connection.prepareStatement("DELETE FROM ContentItem WHERE ContentID = ?");
                deleter.setInt(1, contentID);
                deleter.executeUpdate();
                
            } catch (SQLException webcastException) {
                webcastException.printStackTrace();
            }

        }
    }
    //updates ContentItem (all changeable columns of webcast are located there in the database)
    @Override
    public void update(Webcast domainObject) {
        Connection connection = this.connection.getConnection();

        try {

            PreparedStatement updateWebcast = connection.prepareStatement("UPDATE ContentItem SET Title = ? , Description = ? , Status = ? WHERE ContentID = ?");

            updateWebcast.setString(1, domainObject.getTitle());
            updateWebcast.setString(2, domainObject.getDescription());
            updateWebcast.setString(3, domainObject.getStatus().toString());
            updateWebcast.setInt(4, getIDFromURL(domainObject.getUrl()));
            updateWebcast.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void delete(Webcast domainObject) {
        Connection connection = this.connection.getConnection();
        try {
            PreparedStatement deleteWebcast = connection.prepareStatement("DELETE FROM ContentItem WHERE ContentID = ?");
            deleteWebcast.setInt(1, getIDFromURL(domainObject.getUrl()));
            deleteWebcast.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //not usefull for webcast
    @Override
    public ArrayList<Webcast> retrieve() {
        // TODO Auto-generated method stub
        return null;
    }
    //result set will be empty if the title of a module is given
    public Webcast retrieveByTitle(String webcastTitle) { 
        Connection connection = this.connection.getConnection();
        //retrieves all values needed to create a webcast object based on the (unique) URL.
        try {
            PreparedStatement retrieveByTitle = connection.prepareStatement("SELECT Title, Webcast.SpeakerName, Speaker.OrganizationName, Duration, URL, Status, CreationDate, Description FROM Webcast JOIN ContentItem ON ContentItem.ContentID = Webcast.ContentID JOIN Speaker ON Webcast.SpeakerName = Speaker.Name WHERE Title = ?");
            retrieveByTitle.setString(1, webcastTitle);
            ResultSet result = retrieveByTitle.executeQuery();
            while(result.next()) { 
                String title = result.getString("Title");
                String speaker = result.getString("SpeakerName");
                String organization = result.getString("OrganizationName");
                int durationInMinutes = result.getInt("Duration");
                Status status = Status.valueOf(result.getString("Status"));
                LocalDate date = LocalDate.parse(result.getString("CreationDate"));
                String description = result.getString("Description");
                String url = result.getString("URL");

                Webcast webcast = new Webcast(title, speaker, organization, durationInMinutes, url, status, date, description);
                return webcast;
        }

        return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        
    }

    //returns a hashmap with the webcast URL as key and the webcast title as value.
    public HashMap<String, String> getAllWebcastNamesAndURL(){
        HashMap<String, String> webcasts = new HashMap<>();

        try {
            Statement statement = this.connection.getConnection().createStatement();
            ResultSet webcastResult = statement.executeQuery("SELECT Title, URL FROM Webcast JOIN ContentItem ON Webcast.ContentID = ContentItem.ContentID");
            while(webcastResult.next()) {
                String url = webcastResult.getString("URL");
                String title = webcastResult.getString("Title");
                webcasts.put(url, title);
            }
            return webcasts;



        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }
    

    private int getIDFromURL(String url) { 
        try {
            Connection connection = this.connection.getConnection();
            PreparedStatement getID = connection.prepareStatement("SELECT ContentID FROM Webcast WHERE URL = ?");
            getID.setString(1, url);
            ResultSet idResult = getID.executeQuery();
            int id = -1;
            while(idResult.next()) {
                id = idResult.getInt("ContentID");
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
