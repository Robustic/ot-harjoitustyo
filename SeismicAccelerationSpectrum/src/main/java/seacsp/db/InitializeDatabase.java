package seacsp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class InitializeDatabase {
   
    public void initializeDatabase(String dbname) throws SQLException {
        Connection connection = null;
        String dbPath = "jdbc:sqlite:" + dbname;
        connection = DriverManager.getConnection(dbPath, "sa", "");
        addTimehistoryTable(connection);
        connection.close();
    }
    
    public void addTimehistoryTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.

        statement.executeUpdate("DROP TABLE IF EXISTS Timehistory");
        statement.executeUpdate("CREATE TABLE Timehistory ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR(255), "
                + "deltat DOUBLE, "
                + "datacollection_id INTEGER);");            
        statement.close();
    }
}
