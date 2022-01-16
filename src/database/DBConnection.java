package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
*Class responsible for setting the default database connection, which is *automatically used as reference by every repository. See the abstract repository *class
*/
public class DBConnection {
    private Connection connection;

    // When constructed, the specific connection is set up.
    public DBConnection() {
        try {
            this.connection = DriverManager
                    .getConnection("jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=true;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method so that every repository can easily acces the required database
    // connection setup
    protected Connection getConnection() {
        return this.connection;
    }
}