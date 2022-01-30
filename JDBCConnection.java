
package repo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            Properties props = new Properties();

            try {
                props.load(JDBCConnection.class.getClassLoader().getResourceAsStream("connection.properties"));
                String endpoint = props.getProperty("endpoint");
                String username = props.getProperty("username");
                String password = props.getProperty("password");
                String url = "jdbc:postgresql://" + endpoint + "/postgres";
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

        return conn;
    }

    public static void main(String[] args) {
    }
}
