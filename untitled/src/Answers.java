import java.sql.*;
import java.util.HashMap;
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
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Q&A?&serverTimezone=UTC", "root", "root");

            //create statements
            Statement myStmt = myConn.createStatement();
            Statement myStmt2 = myConn.createStatement();

            //execute sql query to select the answer with matching text and Question ID
            ResultSet answer = myStmt2.executeQuery("select * from Answers WHERE ID = "+ qID + " AND Answer = " + "'%" + input + "%'" );

            //if there is one, show how many thimes this answer was already answered and increment that value by one
            if (answer.next()) {
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
                myStmt.executeUpdate("INSERT INTO Answers VALUES (" + qID + ", " + count + ", " + "'" + input +"'" +  ", 1)");
                myStmt.execute("SET FOREIGN_KEY_CHECKS = 1");
                return "Congratulations, you were the first person to answer this way!";
            }


        } catch (Exception exc) {
            exc.printStackTrace();
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
