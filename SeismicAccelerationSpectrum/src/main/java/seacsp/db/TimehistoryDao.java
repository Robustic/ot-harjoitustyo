package seacsp.db;

import java.util.*;
import java.sql.*;
import seacsp.calculations.Timehistory;

public class TimehistoryDao implements Dao<Timehistory, Integer> {
//    final private ArrayList<Double> timehistory;
//    private double deltaT;
//    final private String name;
    
    @Override
    public void create(Timehistory timehistory) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./spectrum.db", "sa", "");

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Timehistory"
            + " (name, deltat)"
            + " VALUES (?, ?)");
        stmt.setString(1, timehistory.getName());
        stmt.setDouble(2, timehistory.getDeltaT());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Override
    public Timehistory read(Integer key) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./spectrum.db", "sa", "");

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Timehistory WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        // Mikäli tulostaulussa ei ole yhtäkään riviä,
        // palautetaan null-viite
        if(!rs.next()) {
            return null;
        }

        // Edellä siirryttiin ensimmäiselle tulostaulun
        // riville -- luodaan asiakas
        Timehistory a = new Timehistory(rs.getDouble("deltat"), rs.getString("name"));

        stmt.close();
        rs.close();
        connection.close();

        return a;
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
