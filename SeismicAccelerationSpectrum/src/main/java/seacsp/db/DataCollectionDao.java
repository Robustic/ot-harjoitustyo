package seacsp.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import seacsp.data.DataCollection;


public class DataCollectionDao implements Dao<DataCollection, Integer> {
    private File dbFile;
    private TimehistoryDao timehistoryDao;

    public void setDbFile(File dbFile) {
        this.dbFile = dbFile;
    }

    public void setTimehistoryDao(TimehistoryDao timehistoryDao) {
        this.timehistoryDao = timehistoryDao;
    }
    
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
        this.timehistoryDao.setDbFile(this.dbFile);
        this.timehistoryDao.setCollectionId(rowId);
        for (int i = 0; i < dataCollection.getTimehistories().size(); i++) {
            this.timehistoryDao.create(dataCollection.getTimehistories().get(i));
        }
    }
    
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
        DataCollection dataCollection = new DataCollection(name, this.timehistoryDao.listWithCollectionId(key));
        return dataCollection;
    }
    
    public boolean exist(String findName) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString(), "sa", "");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Datacollection WHERE name = ? ORDER BY id ASC");
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
    
    public ArrayList<DataCollection> listWithNameNotExistInTheList(ArrayList<String> names) throws SQLException {
        this.timehistoryDao.setDbFile(this.dbFile);
        ArrayList<DataCollection> returnList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString(), "sa", "");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Datacollection ORDER BY id ASC");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            if (!names.contains(rs.getString("name"))) {
                returnList.add(new DataCollection(rs.getString("name"), this.timehistoryDao.listWithCollectionId(rs.getInt("id"))));
            }
        }
        stmt.close();
        rs.close();
        connection.close();
        return returnList;
    }
}
