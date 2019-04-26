package seacsp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class InitializeDatabase {
   
    public void initializeDatabase() {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:spectrum.db");
//            connection = DriverManager.getConnection("jdbc:sqlite:./spectrum.db", "sa", "");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("DROP TABLE IF EXISTS Timehistory");
            statement.executeUpdate("CREATE TABLE Timehistory ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name VARCHAR(255), "
                    + "deltat DOUBLE);");
//            statement.executeUpdate("insert into person values(1, 'leo')");
//            statement.executeUpdate("insert into person values(2, 'yui')");
//            ResultSet rs = statement.executeQuery("select * from person");
//            while (rs.next()) {
//                // read the result set
//                System.out.println("name = " + rs.getString("name"));
//                System.out.println("id = " + rs.getInt("id"));
//            }
        }
        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.out.println("Virhe: ");
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            }
            catch(SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}
