package database;

import java.time.LocalDate;
import java.util.HashMap;

import domain.Status;
import domain.Webcast;
import logic.WebcastLogic;

public class Test {

    public static void main(String[] args) {

        //"A test", "Speaker", "Organization", 1, "www.webcast.nl", Status.CONCEPT, LocalDate.now(), "This is a test"
       
        Webcast webcast = new Webcast("Not a rick roll", "Rick Astley", "PWL", 3, "https://www.youtube.com/watch?v=dQw4w9WgXcQ", Status.ACTIVE, LocalDate.now(), "A perfectly normal webcast");
        WebcastRepository repo = new WebcastRepository();
        repo.insert(webcast);
      
        // logic.editWebcast("https://www.youtube.com/watch?v=dQw4w9WgXcQ", "Test", "Test", Status.ARCHIVED);
        // logic.editWebcast(url, title, description, status);
        // logic.deleteWebcast(webcast);


        

    }
    
}
