package database;

import java.time.LocalDate;

import domain.Status;
import domain.Webcast;

public class Test {

    public static void main(String[] args) {
        Repository<Webcast> repo = new WebcastRepository();
        //"A test", "Speaker", "Organization", 1, "www.webcast.nl", Status.CONCEPT, LocalDate.now(), "This is a test"
        Webcast webcast = new Webcast("A test", "Speaker", "Organization", 1, "www.webcast.nl", Status.ARCHIVED, LocalDate.now(), "This is a test of the new method");
        repo.update(webcast);
    }
    
}
