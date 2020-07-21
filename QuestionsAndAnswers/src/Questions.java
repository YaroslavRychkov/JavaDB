import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.Properties;

//create Statement with all Questions and methods for calling next and getting current Question ID
public class Questions {
    int qID;
    Statement myQs;
    ResultSet myRs;

    Questions()  throws SQLException {
        try {

            //connection to database, here with localhost for test purposes
            File configFile = new File("config.properties");
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            String host = props.getProperty("host", "localhost");
            String user = props.getProperty("user","root");
            String pw = props.getProperty("pass","root");
            String port = props.getProperty("port","3303");
            props.load(reader);
            Connection myConn = DriverManager.getConnection("jdbc:mysql://"+ host +":"+port+"/Q&A?&serverTimezone=UTC", user, pw);
//            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Q&A?&serverTimezone=UTC", "root", "root");
            myQs = myConn.createStatement();
            myRs = myQs.executeQuery("select * from Questions");

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public int getqID() {
        return qID;
    }

    public String nextQuestion() throws SQLException {
        try {
             if (myRs.next()) {
                 qID = myRs.getInt("ID");
                 return qID + ": " + myRs.getString("Question");
    }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return "No more Questions available";
    }

}
