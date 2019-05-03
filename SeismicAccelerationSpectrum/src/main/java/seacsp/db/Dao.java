package seacsp.db;

import java.sql.*;

/**
 * Abstract class to describe interface to the SQL-database operations.
 */
public interface Dao<T, K> {
    
    /**
     * Method to create SQL-object from the object.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   object   object
     */
    void create(T object) throws SQLException;
    
    /**
     * Method to get id of the latest object created.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   stmt   statement which used when latest object created
     * 
     * @return id for the latest object created
     */
    int generatedKey(PreparedStatement stmt) throws SQLException;
    
    /**
     * Method to read SQL-object with given key.
     * 
     * @throws SQLException  If exception happens during database operation
     *
     * @param   key   key
     * 
     * @return object read from database
     */
    T read(K key) throws SQLException;    
}
