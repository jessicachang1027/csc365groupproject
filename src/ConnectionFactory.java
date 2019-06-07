import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connect to Database
 */
public class ConnectionFactory {
    /**
     * Get a connection to database
     * @return Connection object
     */

    public static Connection getConnection(String driver, String url, String user, String pass)
    {
        try {
            Class.forName(driver).newInstance();
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}