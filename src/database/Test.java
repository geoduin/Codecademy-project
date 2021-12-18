package database;
import domain.Module;
import domain.Status;

public class Test {

    public static void main(String[] args) {
        Repository repo = new ModuleRepository();
        Module module = new Module("2021-12-18", Status.ACTIVE, "How to insert into Java", 1,  "123456", "Lorem Ipsum", "Henk", "henk@henk.nl");
        repo.insert(module);
    }
    
}
