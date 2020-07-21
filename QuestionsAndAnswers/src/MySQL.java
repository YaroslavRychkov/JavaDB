import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

//test class to show all available questions and answers so far
public class MySQL {

    public static void main(String[] args) {


        try {
            //connection to database, here with localhost for test purposes
            File configFile = new File("config.properties");
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            String host = props.getProperty("host", "localhost");
            String user = props.getProperty("user","root");
            String pw = props.getProperty("pass","root");
            String port = props.getProperty("port","3306");
            props.load(reader);
            Connection myConn = DriverManager.getConnection("jdbc:mysql://"+ host +":"+port+"/Q&A?&serverTimezone=UTC", user, pw);
//            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Q&A?&serverTimezone=UTC", "root", "root");

            //create statement
            Statement myStmt = myConn.createStatement();
            Statement myStmt2 = myConn.createStatement();


            //execute sql query
            ResultSet myRs = myStmt.executeQuery("select * from Questions");


            //results set
            while (myRs.next()) {
                int qID = myRs.getInt("ID");
                System.out.println( qID + ". " + myRs.getString("Question"));
                System.out.println();
                ResultSet answers = myStmt2.executeQuery("select * from Answers WHERE ID = "+ qID);
                while (answers.next()) {
                    System.out.println(answers.getString("ID") + ". Answer: " + answers.getString("Answer")+ "\n" + " Times Answered: " + answers.getString("TimesAnswered")
                            );
                }

            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
    }
