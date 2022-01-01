package database;

import java.util.ArrayList;

public abstract class Repository<T> {

    DBConnection connection;

    public Repository() {
        // Default URL
        // "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=true;"
        this.connection = new DBConnection();
    }

    public abstract void insert(T domainObject);

    public abstract void update(T domainObject);

    public abstract void delete(T domainObject);

    public abstract ArrayList<T> retrieve();
}
