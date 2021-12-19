package database;

import java.time.LocalDate;

import domain.Module;
import domain.Status;

public class Test {


    public static void main(String[] args) {
        Repository repo = new ModuleRepository();
        Module module = new Module(LocalDate.now(), Status.ACTIVE, "Test", 1, 1234, "This is a test", "Michael Nicht", "m.nicht@klootviool.nl");
        // repo.insert(module);
        repo.update(module);
        

    }
    
}
