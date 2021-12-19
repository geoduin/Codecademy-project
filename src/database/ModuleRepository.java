package database;

import java.sql.Statement;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import domain.*;
import domain.Module;
import domain.Status;

public class ModuleRepository extends Repository {

    public ModuleRepository() {
        super();
        
    }

    @Override
    public void insert(Object domainObject) {
        if(domainObject instanceof Module){
            Module module = (Module) domainObject
            String title = module.getTitle();
            int version = module.getVersion();
            int orderNumber = module.getTrackingNumber();
            String contactEmail = module.getEmailAddress();
            
            try {
                Statement statement = this.connection.getConnection().createStatement();
                
                statement.executeQuery("INSERT INTO Module VALUES(NULL, '"+title+"', "+version+", "+orderNumber+", '"+contactEmail+"')");
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // @Override
    // public void update(Object domainObject) {
    //     if(domainObject instanceof Module){
    //         Module module = (Module) domainObject;
    //         try {
    //             Statement statement = this.connection.getConnection().createStatement();
    //             statement.executeQuery();
                
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    @Override
    public void delete(Object domainObject) {
        if(domainObject instanceof Module){
            Module module = (Module) domainObject;
            try {
                String title = module.getTitle();
                int version = module.getVersion();
                Statement statement = this.connection.getConnection().createStatement();
                ResultSet result = statement.executeQuery("SELECT ContentID FROM Module WHERE Title = '"+title+"' AND Version = '"+version+"'");
                System.out.println(result.toString());
                int contentID = 0;
                while(result.next()){
                    contentID = result.getInt("ContentID");
                }
                statement.executeQuery("DELETE FROM ContentItem WHERE ContentID = '"+contentID+"'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Object> retrieve() {
        try {
            Statement statement = this.connection.getConnection().createStatement();
            ResultSet result = statement .executeQuery("SELECT * FROM Module JOIN ContentItem ON Module.ContentID = ContentItem.ContentID JOIN Contact ON Module.ContactEmail = Contact.Email");
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
            modules.add(new Module(localDate, status, title, version, trackingNumber, description, contactName, emailAddress));
            }

            return modules;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
