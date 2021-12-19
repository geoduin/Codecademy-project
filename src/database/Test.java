package database;
import javax.swing.event.SwingPropertyChangeSupport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import domain.Module;
import domain.Status;

public class Test {

    public static void main(String[] args) {
        Repository repo = new ModuleRepository();

        // ArrayList<Object> kees = new ArrayList<>();
        // kees = repo.retrieve();

        // //Test of de modules zijn opgehaald
        // kees.stream().filter(Module.class::isInstance)
        // .map(Module.class::cast)
        // .map(Module::getTitle)
        // // .forEach(System.out::println);

        

        LocalDate date = LocalDate.of(2021, 12, 13);
        System.out.println(date);
        
        Module module = new Module(date, Status.ACTIVE, "Test", 1, 123, "This is a test", "Henk", "henk@henk.nl");
        repo.insert(module);
        

    }
}
