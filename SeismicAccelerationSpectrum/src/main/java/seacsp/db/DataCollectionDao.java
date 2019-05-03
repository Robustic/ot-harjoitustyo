package seacsp.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import seacsp.data.DataCollection;

/**
 * Class to perform SQL-database writing and reading methods for the DataCollection objects.
 */
public class DataCollectionDao implements Dao<DataCollection, Integer> {
    private File dbFile;
    private TimehistoryDao timehistoryDao;
    
    /**
     * Method to set database file which is used by other methods.
     *
     * @param   dbFile   database file
     */
    public void setDatabaseFile(File dbFile) {
        this.dbFile = dbFile;
    }

    /**
     * Method to set TimehistoryDao object.
     *
     * @param   timehistoryDao   TimehistoryDao object
     */
    public void setTimehistoryDao(TimehistoryDao timehistoryDao) {
        this.timehistoryDao = timehistoryDao;
    }
    
    /**
     * Method to create SQL-object from the DataCollection.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   dataCollection   DataCollection which is copied to the database
     */
    @Override
    public void create(DataCollection dataCollection) throws SQLException {
        String connectionPath = "jdbc:sqlite:" + dbFile.toString();
        Connection connection = DriverManager.getConnection(connectionPath, "sa", "");
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Datacollection"
            + " (name)"
            + " VALUES (?)");
        stmt.setString(1, dataCollection.getName());
        stmt.executeUpdate();
        int rowId = generatedKey(stmt);
        stmt.close();
        connection.close();
        this.timehistoryDao.setDatabaseFile(this.dbFile);
        this.timehistoryDao.setDataCollectionId(rowId);
        for (int i = 0; i < dataCollection.getTimehistories().size(); i++) {
            this.timehistoryDao.create(dataCollection.getTimehistories().get(i));
        }
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
     * Method to read DataCollection SQL-object with given key.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   key   key
     * 
     * @return DataCollection object read from database
     */
    @Override
    public DataCollection read(Integer key) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString(), "sa", "");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Datacollection WHERE id = ? ORDER BY id ASC");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            stmt.close();
            rs.close();
            connection.close();
            return null;
        }
        String name = rs.getString("name");
        stmt.close();
        rs.close();
        connection.close();
        DataCollection dataCollection = new DataCollection(name, this.timehistoryDao.listTimehistoriesWithGivenDataCollectionId(key));
        return dataCollection;
    }
    
    /**
     * Method to check if DataCollection SQL-object already exists with given string as name.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   findName   name
     * 
     * @return true if SQL-object already exists with given input string as name
     */
    public boolean dataCollectionWithGivenNameExist(String findName) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString(), "sa", "");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Datacollection WHERE name = ?");
        stmt.setString(1, findName);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            stmt.close();
            rs.close();
            connection.close();
            return false;
        }
        stmt.close();
        rs.close();
        connection.close();
        return true;
    }
    
    /**
     * Method returns list of the DataCollections in the SQL-database which 
 name does not dataCollectionWithGivenNameExist in the list given as input.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   names   name list
     * 
     * @return list of the DataCollections which name does not dataCollectionWithGivenNameExist in the list given as input
     */
    public ArrayList<DataCollection> listDataCollectionsWithNameNotExistInTheList(ArrayList<String> names) throws SQLException {
        this.timehistoryDao.setDatabaseFile(this.dbFile);
        ArrayList<DataCollection> returnList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString(), "sa", "");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Datacollection ORDER BY id ASC");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            if (!names.contains(rs.getString("name"))) {
                returnList.add(new DataCollection(rs.getString("name"), this.timehistoryDao.listTimehistoriesWithGivenDataCollectionId(rs.getInt("id"))));
            }
        }
        stmt.close();
        rs.close();
        connection.close();
        return returnList;
    }
}
