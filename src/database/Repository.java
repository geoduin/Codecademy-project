package database;

import java.util.ArrayList;

/*
*This class explains what the general makeup of a repository is. It uses generic typing (<T>), so that implemented classes can define what the actual domain object is. This way, object casting is not needed and every implemented repository safely uses their actual related domain type
*/
public abstract class Repository<T> {

    DBConnection connection;

    // Every repository gets an association with the database connection class. This
    // way, no duplication in connection setting code is needed
    Repository() {
        // Default URL
        // "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=true;"
        this.connection = new DBConnection();
    }

    // Asks every repository to implement insert DML, using their specific domain
    // object.
    public abstract void insert(T domainObject);

    // Asks every repository to implement update DML, using their specific domain
    // object.
    public abstract void update(T domainObject);

    // Asks every repository to implement delete DML, using their specific domain
    // object.
    public abstract void delete(T domainObject);

    // Asks every repository to implement a retrieval of records and returning
    // instances in a list, using their specific domain
    // object.
    public abstract ArrayList<T> retrieve();
}
