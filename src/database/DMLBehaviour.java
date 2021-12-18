package database;
import java.util.ArrayList;

public interface DMLBehaviour{


    public void insert(Object domainObject);
    public void update(Object domainObject);
    public void delete(Object domainObject);
    public ArrayList<Object> retrieve();


}