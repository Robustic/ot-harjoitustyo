package seacsp.db;

import java.sql.*;

public interface Dao<T, K> {
    void create(T object) throws SQLException;
    T read(K key) throws SQLException;
}
