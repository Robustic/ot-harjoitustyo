package seacsp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to initialize SQL-database with empty tables "Timehistory" and "Datacollection".
 */
public class InitializeDatabase {
   
    /**
     * Method to initalize SQL-database with empty tables.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   dbname   new sql-database file name as string
     */
    public void initializeDatabase(String dbname) throws SQLException {
        String dbPath = "jdbc:sqlite:" + dbname;
        Connection connection = DriverManager.getConnection(dbPath, "sa", "");
        addTimehistoryTable(connection);
        addDataCollectionTable(connection);
        connection.close();
    }
    
    /**
     * Method to initialize "Timehistory" table.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   connection   connection which to be used for initializing
     */
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
    
    /**
     * Method to initialize "Datacollection" table.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   connection   connection which to be used for initializing
     */
    public void addDataCollectionTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        statement.executeUpdate("DROP TABLE IF EXISTS Datacollection");
        statement.executeUpdate("CREATE TABLE Datacollection ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR(255));");            
        statement.close();
    }
}
