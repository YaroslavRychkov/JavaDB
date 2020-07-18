import java.sql.SQLException;
import java.util.Scanner;

public class QnAInterface {


    public static void main(String[] args) throws SQLException {
        //creates set of questions from database
        Questions questionset = new Questions();
        String currentQuestion = questionset.nextQuestion();
        //checks if there are any more questions available
        while (!currentQuestion.equals("No more Questions available")){
            //prints out the current question and asks for an answer
            System.out.println("Question Number ");
            System.out.print(currentQuestion);
            System.out.println();
            System.out.println("Type in your answer here: ");
            Answers answer;
            String answerSaved = "";
            boolean x = true;
            while (x) {
                Scanner scan = new Scanner(System.in);
                String s;
                s = scan.nextLine();
                System.out.println("Answer: " + s);
                answer = new Answers(s,questionset.getqID());
                answerSaved = answer.checkAnswer();
                System.out.println(answerSaved);
                x = false;
            }
            //checks if answer contains special characters, if it does returns a message, asks the same question again. Else goes to next question.
             if (!answerSaved.equals("Answer contains special character(s). Please write your answer using alphanumeric characters")) {
                 currentQuestion = questionset.nextQuestion();
             }
        }
        //tells the user there are no more questions to answer
        System.out.println(currentQuestion);

    }

}
