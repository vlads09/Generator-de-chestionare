package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Verify {

    /**
     * <p>Checks if the username is "already taken" (already in users.txt file).</p>
     * @param name username
     * @return <p>1 - username is available</p>
     * <p>0 - username is taken</p>
     */
    public static int UserExists(String name){
        try {
            File myObj = new File("src/main/java/com/example/project/users.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (name.equals(data[0])) {
                    System.out.println("{ 'status' : 'error', 'message' : 'User already exists'}");
                    myReader.close();
                    return 0;
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * <p>Checks if the command was written entirely. (Create user only)</p>
     * @param args full command
     * @return <p>1 - command was written correctly</p>
     * <p>0 - command didn't provide enough arguments</p>
     */
    public static int ProvideUser(String[] args){
        if (args.length == 1) {
            System.out.println("{ 'status' : 'error', 'message' : 'Please provide username'}");
            return 0;
        } else if (args.length == 2) {
            System.out.println("{ 'status' : 'error', 'message' : 'Please provide password'}");
            return 0;
        }
        return 1;
    }

    /**
     * <p>Checks if the command was written correctly, having the arguments of command as parameters given.</p>
     * @param names syntax when writing the username
     * @param passwords syntax when writing the password
     * @return <p>1 - correct syntax</p>
     * <p>0 - wrong syntax</p>
     */
    public static int CorrectSyntax(String[] names, String[] passwords){
        if (names.length != 2 || !names[0].equals("-u ")) {
            System.out.println("Please provide correct syntax");
            return 0;
        }
        if (passwords.length != 2 || !passwords[0].equals("-p ")) {
            System.out.println("Please provide correct syntax");
            return 0;
        }
        return 1;
    }

    /**
     * <p>Verifies for other commands than create user if the user authenticated correctly.</p>
     * @param args full command
     * @return <p>1 - authentication succeeded</p>
     * <p>0 - authentication failed</p>
     */
    public static int NeedToBeAuthenticated(String[] args){
        if (args.length < 3) {
            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return 0;
        }

        String[] names = args[1].split("'");
        if (names.length < 2 || !names[0].equals("-u ")) {
            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return 0;
        }

        String[] passwords = args[2].split("'");
        if (passwords.length < 2 || !passwords[0].equals("-p ")) {
            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return 0;
        }
        return 1;
    }

    /**
     * Verifies if the user gave the right information when logging in or certain commands have enough arguments
     * (submitting answers for a quiz or deleting a quiz).
     * @param args full command
     * @param name username
     * @param password user's password
     * @return <p>1 - logged in successfully</p>
     * <p>0 - login failed</p>
     */
    public static int LoginFailed(String[] args, String name, String password){
        try {
            int found = 0;
            File myObj = new File("src/main/java/com/example/project/users.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (name.equals(data[0]) && password.equals(data[1])) {
                    found = 1;
                    break;
                }
            }
            myReader.close();
            if (found == 0) {
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return 0;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if (args.length == 3) {
            if (args[0].equals("-submit-quizz") || args[0].equals("-delete-quizz-by-id")){
                System.out.println("{ 'status' : 'error', 'message' : 'No quizz identifier was provided'}");
                return 0;
            }
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return 0;
        }

        return 1;
    }

    /**
     * Verifies if the argument of the command that should contain the question has it or not
     * @param texts argument that contains the question text
     * @return <p>1 - question was written</p>
     * <p>0 - no question text provided</p>
     */
    public static int NoQuestionText(String[] texts){

        if (texts.length < 2 || !texts[0].equals("-text ")) {
            System.out.println("{ 'status' : 'error', 'message' : 'No question text provided'}");
            return 0;
        }
        return 1;
    }

    /**
     * <p>Checks if there are any answers provided by the user when creating a question</p>
     * @param args full command
     * @return <p>1 - there is at least an answer</p>
     * <p>0 - there was no answer written</p>
     */
    public static int NoAnswer(String[] args){
        if (args.length == 5) {
            System.out.println("{ 'status' : 'error', 'message' : 'No answer provided'}");
            return 0;
        }
        return 1;
    }

    /**
     * Verifies if the user provided answers for the question correctly. Firstly, it checks if there is more than
     * one answer. Secondly, the method verifies if the answers have the all conditions in order to be provided.
     * Thirdly, it returns an error if the answer was written more than once and lastly the method checks whether
     * there were two or more correct answers provided in a single type question, a single correct answer was provided
     * for a multiple type question or no correct answers were provided.
     * @param answers question's answers
     * @param type single or multiple question
     * @return <p>1 - no issues with the answers</p>
     * <p>0 - problems providing the answers for the question</p>
     */
    public static int AnswersIssues(ArrayList<String> answers, String type){
        if (answers.size() == 2 && (answers.get(0).charAt(8) == answers.get(1).charAt(8))) {
            System.out.println("{ 'status' : 'error', 'message' : 'Only one answer provided'}");
            return 0;
        }

        int count_correct = 0;
        int answer = 1;

        for (int i = 0; i < answers.size(); i += 2) {
            if ((i + 1) >= answers.size() || answers.get(i).charAt(8) != answers.get(i + 1).charAt(8)) {
                String[] whichs = answers.get(i).split(" ");
                String which = whichs[1];
                if (which.equals("'0'") || which.equals("'1'")) {
                    System.out.println("{ 'status' : 'error', 'message' : 'Answer " + answer + " has no answer description'}");
                    return 0;
                } else {
                    System.out.println("{ 'status' : 'error', 'message' : 'Answer " + answer + " has no answer correct flag'}");
                    return 0;
                }

            }

            String[] take_yes = answers.get(i).split(" ");
            String take = take_yes[1];

            for (int j = 0; j < i; j += 2){
                String[] take_sames = answers.get(j).split(" ");
                String take_same = take_sames[1];
                if (take.equals(take_same)){
                    System.out.println("{ 'status' : 'error', 'message' : 'Same answer provided more than once'}");
                    return 0;
                }
            }
            String[] values = answers.get(i + 1).split(" ");
            String value = values[1];
            if (value.equals("'1'")) count_correct++;
            answer++;
        }

        if (count_correct == 0) {
            System.out.println("{ 'status' : 'error', 'message' : 'No correct answer provided'}");
            return 0;
        }

        if (count_correct == 1 && type.equals("'multiple'")){
            System.out.println("{ 'status' : 'error', 'message' : 'Multiple correct answer question'}");
        }

        if (count_correct > 1 && type.equals("'single'")) {
            System.out.println("{ 'status' : 'error', 'message' : 'Single correct answer question has more " +
                    "than one correct answer'}");
            return 0;
        }
        return 1;
    }

    /**
     * <p>Checks if the question is "already provided" (already in questions.txt file).</p>
     * @param text the question
     * @return <p>1 - question was not yet provided</p>
     * <p>0 - question already exists</p>
     */
    public static int QuestionExists(String text) {
        try {
            File myObj = new File("src/main/java/com/example/project/questions.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (text.equals(data[3])) {
                    System.out.println("{ 'status' : 'error', 'message' : 'Question already exists'}");
                    myReader.close();
                    return 0;
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * <p>Similar to LoginFailed but it is only used when a command only requires username and password.</p>
     * @param name username
     * @param password user's password
     * @return <p>1 - logged in successfully</p>
     * <p>0 - login failed</p>
     */
    public static int LoginFailed2(String name, String password){
        try {
            int found = 0;
            File myObj = new File("src/main/java/com/example/project/users.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (name.equals(data[0]) && password.equals(data[1])) {
                    found = 1;
                    break;
                }
            }
            myReader.close();
            if (found == 0) {
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return 0;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * <p>Checks if the quiz is "already created" (already in quizzes.txt file).</p>
     * @param name_q quiz's name
     * @return <p>1 - quiz was not created</p>
     * <p>0 - quiz already exists</p>
     */
    public static int QuizExists(String name_q) {
        try {
            File myObj = new File("src/main/java/com/example/project/quizzes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (name_q.equals(data[3])) {
                    System.out.println("{ 'status' : 'error', 'message' : 'Quizz name already exists'}");
                    myReader.close();
                    return 0;
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * <p>Verifies if the questions of the quiz exist in the questions.txt file by providing the ids.</p>
     * @param questions questions added in the quiz by the user
     * @return <p>1 - Question was found in the questions.txt file</p>
     * <p>0 - Question with the ID provided in the quiz doesn't exist</p>
     */
    public static int QuestionID(ArrayList<String> questions){
        try {
            for (String question : questions) {
                int found = 0;
                String[] verify_id = question.split("'");
                File myObj = new File("src/main/java/com/example/project/questions.txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String[] data = myReader.nextLine().split(",");
                    if (verify_id[1].equals(data[data.length - 1])) {
                        found = 1;
                        break;
                    }
                }
                myReader.close();
                if (found == 0) {
                    System.out.println("{ 'status' : 'error', 'message' : 'Question ID for question " + verify_id[1]
                            + " does not exist'}");
                    return 0;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * Checks if the quiz exists in the quizzes.txt file by looking at the id.
     * @param id_q quiz id that needs to be founded
     * @return <p>1 - quiz was found</p>
     * <p>0 - quiz doesn't exist</p>
     */
    public static int QuizNotFound(String id_q){
        try {
            File myObj = new File("src/main/java/com/example/project/quizzes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (id_q.equals(data[data.length - 1])) {
                    myReader.close();
                    return 1;
                }
            }
            myReader.close();
            System.out.println("{ 'status' : 'error', 'message' : 'No quiz was found'}");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * <p>Checks if the quiz that user wants to submit their answers was created by them or not.</p>
     * @param name quiz's name
     * @return <p>1 - quiz available for the user</p>
     * <p>0 - cannot answer own quiz</p>
     */
    public static int SelfQuiz(String name) {
        try {
            File myObj = new File("src/main/java/com/example/project/quizzes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (name.equals(data[0])) {
                    myReader.close();
                    System.out.println("{ 'status' : 'error', 'message' : 'You cannot answer your own quizz'}");
                    return 0;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 1;
    }
}
