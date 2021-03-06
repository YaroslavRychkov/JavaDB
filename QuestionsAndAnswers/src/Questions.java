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

            Connection myConn = ConnectToDatabase.connectToDB();
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
