package dao;

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
    public static void main(String[] args){
        Connection conn = getConnection("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://csc365.toshikuboi.net:3306/sec05group06",
                "sec05group06", "group06@sec05");
    }

}