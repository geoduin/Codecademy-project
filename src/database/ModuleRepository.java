package database;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import domain.*;
import domain.Module;

public class ModuleRepository extends Repository {

    public ModuleRepository() {
        super();
        
    }

    @Override
    public void insert(Object domainObject) {
        if(domainObject instanceof Module){
            Integer id = 6;
            Module module = (Module) domainObject;
            String courseName = "Test";
            String title = module.getTitle();
            int version = module.getVersion();
            int orderNumber = module.getTrackingNumber();
            String contactEmail = module.getEmailAddress();
            
            try {
                Statement statement = this.connection.getConnection().createStatement();
                
                statement.executeQuery("INSERT INTO Module VALUES("+id+", '"+courseName+"', '"+title+"', "+version+", "+orderNumber+", '"+contactEmail+"')");
                
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

    // @Override
    // public ArrayList<Object> retrieve() {
    //     return super.retrieve();
    // }
}
