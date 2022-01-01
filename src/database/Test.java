package database;

import java.time.LocalDate;
import java.util.HashMap;

import domain.Status;
import domain.Webcast;

public class Test {

    public static void main(String[] args) {
        WebcastRepository repo = new WebcastRepository();
        //"A test", "Speaker", "Organization", 1, "www.webcast.nl", Status.CONCEPT, LocalDate.now(), "This is a test"
        Webcast webcast = new Webcast("Not a rick roll", "Rick Astley", "PWL", 3, "https://www.youtube.com/watch?v=dQw4w9WgXcQ", Status.ACTIVE, LocalDate.now(), "A perfectly normal webcast");
        repo.delete(webcast);
        

    }
    
}
