import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectToDatabase {
    public static Connection connectToDB() throws SQLException, IOException {
        File configFile = new File("config.properties");
        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();
        String host = props.getProperty("host", "127.0.0.1");
        String user = props.getProperty("user", "root");
        String pw = props.getProperty("pass", "root");
        String port = props.getProperty("port", "3306");
        props.load(reader);
        Connection myConn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/Q&A?&serverTimezone=UTC", user, pw);
    return myConn;
    }

}
