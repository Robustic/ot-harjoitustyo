package seacsp.db;

import java.util.*;
import java.sql.*;
import seacsp.calculations.Timehistory;
import java.io.File;
import java.text.DecimalFormat;

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
        addListAsTable(timehistory.getTimehistory(), rowId);
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
    
    public void addListAsTable(ArrayList<Double> timehistoryAsList, int rowId) throws SQLException {
        initializeNewTable(rowId);
        String connectionPath = "jdbc:sqlite:" + dbFile.toString();        
        String listAsString = "";
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        for (int i = 0; i < timehistoryAsList.size(); i++) {
            if (i > 0) {
                listAsString = listAsString + ", ";
            }
            listAsString = listAsString + "(" + df.format(timehistoryAsList.get(i)) + ")";
        }
        Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
        String newTableName = "Timehistorylist" + rowId;
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + newTableName
            + " (acceleration) VALUES " + listAsString);
        stmt.executeUpdate();
        stmt.close();    
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
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString(), "sa", "");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timehistory WHERE id = ? ORDER BY id ASC");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        double deltaT = rs.getDouble("deltat");
        String name = rs.getString("name");
        stmt.close();
        rs.close();
        connection.close();
        Timehistory timehistory = new Timehistory(getTableAsList(key), deltaT, name);
        return timehistory;
    }
    
    public ArrayList<Double> getTableAsList(int rowId) throws SQLException {
        ArrayList<Double> list = new ArrayList<>();
        String tableName = "Timehistorylist" + rowId;
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString(), "sa", "");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + tableName +  " ORDER BY id ASC");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(rs.getDouble("acceleration"));
        }
        stmt.close();
        rs.close();
        connection.close();
        return list;
    }
    
    public ArrayList<Timehistory> listWithCollectionId(int collectionId) throws SQLException {
        ArrayList<Integer> rowIdList = getRowIdList(collectionId);
        ArrayList<Timehistory> timehistoryList = new ArrayList<>();
        for (int i = 0; i < rowIdList.size(); i++) {
            timehistoryList.add(read(rowIdList.get(i)));
        }
        return timehistoryList;
    }
    
    public ArrayList<Integer> getRowIdList(int collectionId) throws SQLException {
        ArrayList<Integer> rowIdList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString(), "sa", "");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timehistory WHERE datacollection_id = ? ORDER BY id ASC");
        stmt.setInt(1, collectionId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            rowIdList.add(rs.getInt("id"));
        }
        stmt.close();
        rs.close();
        connection.close();
        return rowIdList;
    }
}
