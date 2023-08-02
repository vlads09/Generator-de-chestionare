package com.example.project;

public class Tema1 {

    public static void main(final String[] args) {
        if (args == null) {
            System.out.print("Hello world!");
        } else if (args[0].equals("-create-user")) {
            Commands.CreateFiles();
            Commands.CreateUser(args);
        } else if (args[0].equals("-create-question")) {
            Commands.CreateFiles();
            Commands.CreateQuestion(args);
        } else if (args[0].equals("-get-question-id-by-text")) {
            Commands.CreateFiles();
            Commands.GetQuestionID(args);
        } else if (args[0].equals("-get-all-questions")) {
            Commands.CreateFiles();
            Commands.GetAllQuestions(args);
        } else if (args[0].equals("-create-quizz")) {
            Commands.CreateFiles();
            Commands.CreateQuiz(args);
        } else if (args[0].equals("-get-quizz-by-name")) {
            Commands.CreateFiles();
            Commands.GetQuizzByName(args);
        } else if (args[0].equals("-get-all-quizzes")) {
            Commands.CreateFiles();
            Commands.GetAllQuizzes(args);
        } else if (args[0].equals("-get-quizz-details-by-id")) {
            Commands.CreateFiles();
            Commands.GetQuizzDetailsById(args);
        } else if (args[0].equals("-submit-quizz")){
            Commands.CreateFiles();
            Commands.SubmitQuizz(args);
        } else if (args[0].equals("-delete-quizz-by-id")) {
            Commands.CreateFiles();
            Commands.DeleteQuizz(args);
        } else if (args[0].equals("-get-my-solutions")) {
            Commands.CreateFiles();
            Commands.GetMySolutions(args);
        } else if (args[0].equals("-cleanup-all")) {
            Commands.DeleteFiles();
        } else {
            System.out.print("{'status':'error','message':'Command doesn't exist'}");
        }
    }
}
