package database;

import javax.swing.event.SwingPropertyChangeSupport;
import java.util.ArrayList;
import domain.Module;

public class Test {

    public static void main(String[] args) {
        Repository repo = new ModuleRepository();

        ArrayList<Object> kees = new ArrayList<>();
        kees = repo.retrieve();

        //Test of de modules zijn opgehaald
        kees.stream().filter(Module.class::isInstance)
        .map(Module.class::cast)
        .map(Module::getTitle)
        .forEach(System.out::println);
        

    }
}
