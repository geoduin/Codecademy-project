package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import domain.Webcast;

import domain.Status;

public class WebcastRepository extends Repository {


    //Creates a new repository, gives connection to database.
    public WebcastRepository() {
     super();
    } 

 //Retrieves all webcasts from the database
 @Override

 //Initializing needed variables
 public ArrayList<String> retrieve() {

    //Returns the (unique) title of all webcasts saved in the database
     try {
        Statement statement = this.connection.getConnection().createStatement();
        ResultSet result = statement.executeQuery("SELECT Title FROM Webcast");
        ArrayList<String> webcasts = new ArrayList<>();
        while(result.next()) {
            webcasts.add(result.getString("Title"));
        }
        return webcasts;
         
     } catch (SQLException e) {
         e.printStackTrace();
         return null;
     }
     
 }

   @Override
   public void update(Object domainObject) {
       // TODO Auto-generated method stub
       
   }

   @Override
   public void delete(Object domainObject) {
       // TODO Auto-generated method stub
       
   }

   @Override
   public void insert(Object domainObject) {
       // TODO Auto-generated method stub
       
   }
    
}
