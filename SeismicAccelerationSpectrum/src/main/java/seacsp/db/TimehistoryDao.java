package seacsp.db;

import java.util.*;
import java.sql.*;
import seacsp.calculations.Timehistory;
import java.io.File;

public class TimehistoryDao implements Dao<Timehistory, Integer> {
    private File dbFile;
    private int collectionId;

    public void setDbFile(File dbFile) {
        this.dbFile = dbFile;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }
    
    @Override
    public void create(Timehistory timehistory) throws SQLException {
        String connectionPath = "jdbc:sqlite:" + dbFile.toString();
        Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Timehistory"
            + " (name, deltat, datacollection_id)"
            + " VALUES (?, ?, ?)");
        stmt.setString(1, timehistory.getName());
        stmt.setDouble(2, timehistory.getDeltaT());
        stmt.setInt(3, this.collectionId);
        stmt.executeUpdate();
        int rowId = generatedKey(stmt);
        stmt.close();
        connection.close();                
        addList(timehistory.getTimehistory(), rowId);
    }
    
    public int generatedKey(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();        
        int rowId;
        if (!rs.next()) {
            throw new IllegalArgumentException("No generated sqldb-rows.");
        } else {
            rowId = rs.getInt(1);
        }
        rs.close();
        return rowId;
    }
    
    public void addList(ArrayList<Double> timehistoryAsList, int rowId) throws SQLException {
        initializeNewTable(rowId);
        String connectionPath = "jdbc:sqlite:" + dbFile.toString();
        Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
        for (int i = 0; i < timehistoryAsList.size(); i++) {
            String newTableName = "Timehistorylist" + rowId;
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + newTableName
                + " (acceleration)"
                + " VALUES (?)");
            stmt.setDouble(1, timehistoryAsList.get(i));
            stmt.executeUpdate();
            stmt.close();
        }        
        connection.close();
    }
    
    public void initializeNewTable(int rowId) throws SQLException {
        Connection connection = null;
        String dbPath = "jdbc:sqlite:" + dbFile.toString();
        connection = DriverManager.getConnection(dbPath, "sa", "");
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        String newTableName = "Timehistorylist" + rowId;

        statement.executeUpdate("DROP TABLE IF EXISTS " + newTableName);
        statement.executeUpdate("CREATE TABLE " + newTableName + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "acceleration DOUBLE);");
        statement.close();
        connection.close();
    }

    @Override
    public Timehistory read(Integer key) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./spectrum.db", "sa", "");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timehistory WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Timehistory timehistory = new Timehistory(rs.getDouble("deltat"), rs.getString("name"));
        stmt.close();
        rs.close();
        connection.close();
        return timehistory;
    }

    @Override
    public Timehistory update(Timehistory object) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public List<Timehistory> list() throws SQLException {
        // ei toteutettu
        return null;
    }
}
