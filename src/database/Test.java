package database;

import java.time.LocalDate;

import domain.Status;
import domain.Webcast;

public class Test {

    public static void main(String[] args) {
        Repository<Webcast> repo = new WebcastRepository();
        Webcast webcast = new Webcast("SuperTest", "Speaker", "Organization", 1, "www.webcast.nl", Status.CONCEPT, LocalDate.now(), "This is a test");
        repo.insert(webcast);
    }
    
}
