package database;
import java.util.ArrayList;

public abstract class Repository<T> {

    DBConnection connection;

    public Repository() {
        //Default URL "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=true;" 
        this.connection = new DBConnection();
    }


    
    public void insert(T domainObject) {

    }

    public void update(T domainObject) {

    }

    public void delete(T domainObject) {

    }

    public ArrayList<T> retrieve() {
        return null;
    }
}
