package database;
import java.util.ArrayList;

public abstract class Repository implements DMLBehaviour {

    DBConnection connection;

    public Repository() {
        //Default URL "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=true;" 
        this.connection = new DBConnection();
    }

    public void insert(Object domainObject) {

    }

    public void update(Object domainObject) {

    }

    public void delete(Object domainObject) {

    }

    public ArrayList<Object> retrieve() {
        return null;
    }
}
