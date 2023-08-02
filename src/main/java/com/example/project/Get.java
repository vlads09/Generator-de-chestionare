package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Get {
    /**
     * <p>This method prints the id of the question that has the text that was given as a parameter. If the text doesn't
     * exist, it prints an error.</p>
     * @param text question text
     */
    public static void QuestionID(String text){
        try {
            int found = 0;
            File myObj = new File("src/main/java/com/example/project/questions.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (text.equals(data[3])) {
                    found = 1;
                    System.out.println("{ 'status' : 'ok', 'message' : '" + data[data.length - 1] + "'}");
                    break;
                }
            }
            myReader.close();
            if (found == 0) {
                System.out.println("{ 'status' : 'error', 'message' : 'Question does not exist'}");
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * <p>This method prints all the questions found in the questions.txt, specifically the ids and the names.</p>
     */
    public static void AllQuestions(){
        try {
            ArrayList<String> all_questions = new ArrayList<>();
            System.out.print("{ 'status' : 'ok', 'message' : '[");
            File myObj = new File("src/main/java/com/example/project/questions.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                all_questions.add("{\"question_id\" : \"" + data[data.length - 1] + "\", \"question_name\" : \""
                                    + data[3] + "\"}");
            }
            for (int i = 0; i < all_questions.size(); i++) {
                if (i == all_questions.size() - 1) {
                    System.out.print(all_questions.get(i));
                } else {
                    System.out.print(all_questions.get(i) + ", ");
                }
            }
            System.out.println("]'}");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * <p>It prints from the quizzes.txt, the id of the quiz that has the name given as a parameter.</p>
     * @param name_q quiz's name
     */
    public static void QuizByName(String name_q){
        try {
            File myObj = new File("src/main/java/com/example/project/quizzes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (name_q.equals(data[3])) {
                    System.out.println("{ 'status' : 'ok', 'message' : '" + data[data.length - 1] + "'}");
                    myReader.close();
                    return;
                }
            }
            myReader.close();
            System.out.println("{ 'status' : 'error', 'message' : 'Quizz does not exist'}");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * <p>This method prints all the quizzes found in the quizzes.txt, specifying if the user did the quiz or not.</p>
     * @param name username
     */
    public static void AllQuizzes(String name){
        try {
            ArrayList<String> all_quizzes = new ArrayList<>();
            System.out.print("{ 'status' : 'ok', 'message' : '[");
            File myObj = new File("src/main/java/com/example/project/quizzes.txt");
            Scanner myReader = new Scanner(myObj);
            String TorF = "False";
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                File myQuiz = new File("src/main/java/com/example/project/submissions.txt");
                Scanner myQuizzer = new Scanner(myQuiz);
                while(myQuizzer.hasNextLine()){
                    String[] data2 = myQuizzer.nextLine().split(",");
                    if (data[3].equals(data2[1]) && name.equals(data2[0])) {
                        TorF = "True";
                        break;
                    }
                }
                myQuizzer.close();
                all_quizzes.add("{\"quizz_id\" : \"" + data[data.length - 1] + "\", \"quizz_name\" : \"" + data[3] +
                        "\", \"is_completed\" : \"" + TorF + "\"}");
            }
            for (int i = 0; i < all_quizzes.size(); i++){
                if (i == all_quizzes.size() - 1){
                    System.out.print(all_quizzes.get(i));
                } else {
                    System.out.print(all_quizzes.get(i) + ", ");
                }
            }
            System.out.println("]'}");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This method prints the details of a quiz, with the questions' names and indexes and the answers' names and ids
     * from the quiz.
     * @param id quiz's id
     */
    public static void QuizzDetailsById(String id){
        try{
            ArrayList<String> questions = new ArrayList<>();
            int a_id = 0;
            int q_index = 0;
            System.out.print("{ 'status' : 'ok', 'message' : '[");
            File myObj = new File("src/main/java/com/example/project/quizzes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()){
                String[] data = myReader.nextLine().split(",");
                if (id.equals(data[data.length - 1])){
                    File myQuiz = new File("src/main/java/com/example/project/questions.txt");
                    Scanner myQuizzer = new Scanner(myQuiz);
                    while (myQuizzer.hasNextLine()){
                        String[] data2 = myQuizzer.nextLine().split(",");
                       for (int i = 4; i < data.length - 1; i++){
                           String[] data_q = data[i].split("'");
                           String id_q = data_q[1];
                           if (id_q.equals(data2[data2.length - 1])){
                               StringBuilder answers = new StringBuilder();
                               for (int j = 3; j < data2.length - 1; j++){
                                   if (j == 3){
                                       answers.append("{\"question-name\":\"").append(data2[j]).append("\", \"question_index\":\"").append(++q_index).append("\", \"question_type\":\"");
                                   } else if (j == 4) {
                                       data2[j] = data2[j].replace("'", "");
                                       answers.append(data2[j]).append("\", \"answers\":\"[");
                                   } else if (j % 2 != 0){
                                       String[] YorNs = data2[j].split("'");
                                       String YorN = YorNs[1];
                                       answers.append("{\"answer_name\":\"").append(YorN).append("\", \"answer_id\":\"").append(++a_id).append("\"}");
                                   }

                                   if (j == data2.length - 3){
                                       answers.append("]\"}");
                                   } else if (j < data2.length - 3 && j % 2 != 0 && j != 3){
                                       answers.append(", ");
                                   }
                               }
                               questions.add(String.valueOf(answers));
                           }
                       }
                    }
                    myQuizzer.close();
                    for (int k = 0; k < questions.size(); k++){
                        if (k == questions.size() - 1){
                            System.out.print(questions.get(k));
                        } else{
                            System.out.print(questions.get(k) + ", ");
                        }
                    }
                }
            }
            System.out.println("]'}");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This method prints the results of the quizzes the user has taken.
     * @param name username
     */
    public static void Solutions(String name){
        System.out.print("{ 'status' : 'ok', 'message' : '[");
        ArrayList<String> results = new ArrayList<>();
        int index_in_list = 0;
        try {
            File myObj = new File("src/main/java/com/example/project/submissions.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (name.equals(data[0])) {
                    results.add("{\"quiz-id\" : \"" + data[2] + "\", \"quiz-name\" : \"" + data[1] +
                            "\", \"score\" : \"" + data[3] + "\", \"index_in_list\" : \"" + ++index_in_list +
                            "\"}");
                }
            }
            myReader.close();
            for (int i = 0; i < results.size(); i++){
                if (i == results.size() - 1){
                    System.out.print(results.get(i));
                } else{
                    System.out.print(results.get(i) + ", ");
                }
            }

            System.out.println("]'}");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}