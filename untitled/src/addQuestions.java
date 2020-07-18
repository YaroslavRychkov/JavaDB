import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addQuestions {
    //type in new questions in alphanumeric characters to add to your database of questions. A unique ID is autogenerated.
    static void add(String newQuestion) throws SQLException {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9 ]+");

        String str = newQuestion;
        Matcher matcher = pattern.matcher(str);

        if (matcher.matches()) {
            try {

                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Q&A?&serverTimezone=UTC", "root", "root");
                Statement myStmt = myConn.createStatement();
                String questionSafe = newQuestion.replace("'", "\\'");
                myStmt.executeUpdate("INSERT INTO Questions (Question) VALUES (" + "'" + questionSafe + "'" + ")");
                //if successful, prints out "updated".
                System.out.println("updated");
            } catch (Exception exc) {
                exc.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws SQLException {
        while (true) {
            try {

                Scanner scan = new Scanner(System.in);
                String s;
                s = scan.nextLine();
                add(s);
            } catch (Exception exc){
                exc.printStackTrace();
            }
        }
    }
}