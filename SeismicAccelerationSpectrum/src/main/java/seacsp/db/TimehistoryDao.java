package seacsp.db;

import java.io.File;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import seacsp.calculations.Timehistory;

/**
 * Class to perform database writing and reading methods for the Timehistory objects.
 */
public class TimehistoryDao implements Dao<Timehistory, Integer> {
    private File dbFile;
    private int dataCollectionId;

    /**
     * Method to set database file which is used by other methods.
     *
     * @param   dbFile   database file
     */
    public void setDatabaseFile(File dbFile) {
        this.dbFile = dbFile;
    }

    /**
     * Method to set DataCollection id related to the Timehistory objects.
     *
     * @param   dataCollectionId   id for the DataCollection object related to the Timehistory objects
     */
    public void setDataCollectionId(int dataCollectionId) {
        this.dataCollectionId = dataCollectionId;
    }
    
    /**
     * Method to create SQL-object from the Timehistory.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   timehistory   Timehistory which is copied to the database
     */
    @Override
    public void create(Timehistory timehistory) throws SQLException {        
        String connectionPath = "jdbc:sqlite:" + dbFile.toString();
        Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Timehistory"
            + " (name, deltat, datacollection_id)"
            + " VALUES (?, ?, ?)");
        stmt.setString(1, timehistory.getName());
        stmt.setDouble(2, timehistory.getDeltaT());
        stmt.setInt(3, this.dataCollectionId);
        stmt.executeUpdate();
        int rowId = generatedKey(stmt);
        stmt.close();
        connection.close();                
        createListAsNewTable(timehistory.getTimehistory(), rowId);
    }
    
    /**
     * Method to get id of the latest object created.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   stmt   statement which used when the latest object created
     * 
     * @return id for the latest object created
     */
    @Override
    public int generatedKey(PreparedStatement stmt) throws SQLException {
        ResultSet rs;
        int rowId;
        try {
            rs = stmt.getGeneratedKeys();
            rs.next();
            if (rs.getInt(1) == 0) {
                throw new SQLException("Generated row number == 0.");
            }
            rowId = rs.getInt(1);            
            rs.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException("No generated sqldb-rows.");
        }                
        return rowId;
    }
    
    /**
     * Method to create new table for time history acceleration values given as input.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   timehistoryAsList   time history acceleration values as list
     * 
     * @param   rowId   Timehistory id related to the acceleration values in the list
     */
    public void createListAsNewTable(ArrayList<Double> timehistoryAsList, int rowId) throws SQLException {
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
    
    /**
     * Method to initialize new empty table "Timehistorylist***" (where *** is Timehistory id number).
     * 
     * @throws SQLException  If exception happens during database operation
     * 
     * @param   rowId   Timehistory id related to the new table
     */
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

    /**
     * Method to read Timehistory SQL-object with given key.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   key   key
     * 
     * @return Timehistory object read from database
     */
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
        Timehistory timehistory = new Timehistory(readTableAsList(key), deltaT, name);
        return timehistory;
    }
    
    /**
     * Method to read time history acceleration values from table.
     * 
     * @throws SQLException  If exception happens during database operation
     * 
     * @param   rowId   Timehistory id related to the acceleration values
     * 
     * @return acceleration values as list related to the Timehistory id
     */
    public ArrayList<Double> readTableAsList(int rowId) throws SQLException {
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
    
    /**
     * Method to get Timehistory objects as list related to the given DataCollection id.
     * 
     * @throws SQLException  If exception happens during database operation
     * 
     * @param   dataCollectionId   DataCollection id
     * 
     * @return Timehistory objects as list related to the given DataCollection id
     */
    public ArrayList<Timehistory> listTimehistoriesWithGivenDataCollectionId(int dataCollectionId) throws SQLException {
        ArrayList<Integer> rowIdList = getTimehistoryIdListWithGivenDataCollectionId(dataCollectionId);
        ArrayList<Timehistory> timehistoryList = new ArrayList<>();
        for (int i = 0; i < rowIdList.size(); i++) {
            timehistoryList.add(read(rowIdList.get(i)));
        }
        return timehistoryList;
    }
    
    /**
     * Method to get Timehistory object id numbers as list related to the given DataCollection id.
     * 
     * @throws SQLException  If exception happens during database operation
     * 
     * @param   dataCollectionId   DataCollection id
     * 
     * @return Timehistory id numbers as list related to the given DataCollection id
     */
    public ArrayList<Integer> getTimehistoryIdListWithGivenDataCollectionId(int dataCollectionId) throws SQLException {
        ArrayList<Integer> rowIdList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString(), "sa", "");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timehistory WHERE datacollection_id = ? ORDER BY id ASC");
        stmt.setInt(1, dataCollectionId);
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
