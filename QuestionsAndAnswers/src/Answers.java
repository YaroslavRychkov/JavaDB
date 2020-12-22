import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//create new Answer with ID Key matching the Question ID
public class Answers {
    String input;
    int qID;

    Answers(String input, int qID) {
        this.input = input;
        this.qID = qID;
    }

    //check if answer is alphanumeric, if yes check if its already in database and
    public String checkAnswer() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9 ]+");

        String str = input;
        Matcher matcher = pattern.matcher(str);

        if (!matcher.matches()) {
            return  "Answer contains special character(s)";
        }

        try {
            //connection to database, here with localhost for test purposes
            Connection myConn = ConnectToDatabase.connectToDB();
            //create statements
            Statement myStmt = myConn.createStatement();
            Statement myStmt2 = myConn.createStatement();

            //execute sql query to select the answer with matching text and Question ID
            String updateString1 =
                    "select * from Answers WHERE ID = ? AND Answer = ?";
            try {
                PreparedStatement getQuestions = myConn.prepareStatement(updateString1);
                getQuestions.setInt(1, qID);
                getQuestions.setString(2, input);
                ResultSet answer = getQuestions.executeQuery();

                if (answer.next()) {
                    //if there is one, show how many thimes this answer was already answered and increment that value by one

                    int timesanswered = answer.getInt("TimesAnswered");
                    String answerID = answer.getString("AnswerID");
                    int tIncrement = timesanswered+1;
                    String updateString = "update Answers set TimesAnswered = " + tIncrement + " where AnswerID = " + answerID;
                    myStmt2.executeUpdate(updateString);
                    return "This answer already exists and was called " + timesanswered + "times. Sorry.";
                }
                //else its a unique answer, and is inserted into the database
                else {
                    Statement s = myConn.createStatement();
                    ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM Answers");
                    r.next();
                    int count = r.getInt("rowcount") ;
                    count++;
                    r.close() ;
                    myStmt.execute("SET FOREIGN_KEY_CHECKS = 0");
                    String updateStatement = "INSERT INTO Answers VALUES (?,?,?,1)";
                    PreparedStatement preparedStmt = myConn.prepareStatement(updateStatement);
                    preparedStmt.setInt (1, qID);
                    preparedStmt.setInt (2, count);
                    preparedStmt.setString (3, input);
                    preparedStmt.execute();
                    //   myStmt.executeUpdate("INSERT INTO Answers VALUES (" + qID + ", " + count + ", " + "'" + input +"'" +  ", 1)");
                    myStmt.execute("SET FOREIGN_KEY_CHECKS = 1");
                    return "Congratulations, you were the first person to answer this way!";
                }


            } catch (Exception exc) {
                exc.printStackTrace();
            }

            }
         catch (Exception e ) {
            e.printStackTrace();
        }


        return "something went wrong";
    }

    //test
    public static void main(String[] args) {
//        String test1 = "IDK yes probably";
//        String test2 = "no way";
//        Answers answer1 = new Answers(test1,2);
//        Answers answer2 = new Answers(test2,2);
//        Answers answer3 = new Answers("one more test",2);
//        System.out.println(answer1.checkAnswer());
//        System.out.println(answer2.checkAnswer());
//        System.out.println(answer3.checkAnswer());

    }
}
