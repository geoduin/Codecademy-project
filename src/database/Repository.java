package database;
import java.util.ArrayList;

public abstract class Repository<T> {

    DBConnection connection;

    public Repository() {
        //Default URL "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=true;" 
        this.connection = new DBConnection();
    }


    
    abstract public void insert(T domainObject);

    abstract public void update(T domainObject);

    abstract public void delete(T domainObject);

    abstract public ArrayList<T> retrieve();
}
