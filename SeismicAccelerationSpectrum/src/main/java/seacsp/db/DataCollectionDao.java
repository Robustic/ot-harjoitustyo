package seacsp.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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

    @Override
    public DataCollection read(Integer key) throws SQLException {
//        Connection connection = DriverManager.getConnection("jdbc:sqlite:./spectrum.db", "sa", "");
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timehistory WHERE id = ?");
//        stmt.setInt(1, key);
//        ResultSet rs = stmt.executeQuery();
//        if (!rs.next()) {
//            return null;
//        }
//        DataCollection timehistory = new DataCollection(rs.getDouble("deltat"), rs.getString("name"));
//        stmt.close();
//        rs.close();
//        connection.close();
//        return timehistory;
        return null;
    }

    @Override
    public DataCollection update(DataCollection object) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public List<DataCollection> list() throws SQLException {
        // ei toteutettu
        return null;
    }
}
