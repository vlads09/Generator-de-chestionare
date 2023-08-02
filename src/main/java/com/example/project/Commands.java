package com.example.project;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;


public class Commands {
    //args represents the full command given by the user
    public static void CreateUser(String[] args) {

        int rez = Verify.ProvideUser(args);
        if (rez == 0) return;

        String[] names = args[1].split("'");
        String[] passwords = args[2].split("'");

        rez = Verify.CorrectSyntax(names, passwords);
        if (rez == 0) return;

        String name = names[1];
        String password = passwords[1];

        rez = Verify.UserExists(name);
        if (rez == 0) return;

        Users new_user = new Users(name, password);
        Add.User(new_user);
    }

    public static void CreateQuestion(String[] args) {

        int rez = Verify.NeedToBeAuthenticated(args);

        if (rez == 0) return;

        String[] names = args[1].split("'");
        String name = names[1];

        String[] passwords = args[2].split("'");
        String password = passwords[1];

        rez = Verify.LoginFailed(args, name, password);

        if (rez == 0) return;

        String[] texts = args[3].split("'");

        rez = Verify.NoQuestionText(texts);
        if (rez == 0) return;

        String text = texts[1];

        String[] types = args[4].split(" ");
        String type = types[1];

        rez = Verify.NoAnswer(args);
        if (rez == 0) return;

        ArrayList<String> answers = new ArrayList<>(Arrays.asList(args).subList(5, args.length));

        rez = Verify.AnswersIssues(answers, type);
        if (rez == 0) return;

        rez = Verify.QuestionExists(text);
        if (rez == 0) return;

        Users user = new Users(name, password);

        Questions question = new Questions(user, text, type, answers);

        Add.Question(question);
    }

    public static void GetQuestionID(String[] args) {
        int rez = Verify.NeedToBeAuthenticated(args);

        if (rez == 0) return;

        String[] names = args[1].split("'");
        String name = names[1];

        String[] passwords = args[2].split("'");
        String password = passwords[1];

        rez = Verify.LoginFailed(args, name, password);

        if (rez == 0) return;

        String[] texts = args[3].split("'");
        String text = texts[1];

        Get.QuestionID(text);
    }

    public static void GetAllQuestions(String[] args) {
        int rez = Verify.NeedToBeAuthenticated(args);

        if (rez == 0) return;

        String[] names = args[1].split("'");
        String name = names[1];

        String[] passwords = args[2].split("'");
        String password = passwords[1];

        rez = Verify.LoginFailed2(name, password);
        if (rez == 0) return;

        Get.AllQuestions();
    }

    public static void CreateQuiz(String[] args) {
        int rez = Verify.NeedToBeAuthenticated(args);

        if (rez == 0) return;

        String[] names = args[1].split("'");
        String name = names[1];

        String[] passwords = args[2].split("'");
        String password = passwords[1];

        rez = Verify.LoginFailed(args, name, password);

        if (rez == 0) return;

        String[] quizzes = args[3].split("'");
        String name_q = quizzes[1];

        rez = Verify.QuizExists(name_q);
        if (rez == 0) return;

        if (args.length - 4 > 10) {
            System.out.println("{ 'status' : 'error', 'message' : 'Quizz has more than 10 questions'}");
            return;
        }

        ArrayList<String> questions = new ArrayList<>(Arrays.asList(args).subList(4, args.length));

        rez = Verify.QuestionID(questions);
        if (rez == 0) return;

        Users user = new Users(name, password);
        Quizzes quiz = new Quizzes(user, name_q, questions);

        Add.Quiz(quiz);
    }

    public static void GetQuizzByName(String[] args) {
        int rez = Verify.NeedToBeAuthenticated(args);

        if (rez == 0) return;

        String[] names = args[1].split("'");
        String name = names[1];

        String[] passwords = args[2].split("'");
        String password = passwords[1];

        rez = Verify.LoginFailed(args, name, password);

        if (rez == 0) return;

        String[] names_q = args[3].split("'");
        String name_q = names_q[1];

        Get.QuizByName(name_q);
    }

    public static void GetAllQuizzes(String[] args){
        int rez = Verify.NeedToBeAuthenticated(args);
        if (rez == 0) return;

        String[] names = args[1].split("'");
        String name = names[1];

        String[] passwords = args[2].split("'");
        String password = passwords[1];

        rez = Verify.LoginFailed2(name, password);
        if (rez == 0) return;

        Get.AllQuizzes(name);
    }

    public static void GetQuizzDetailsById(String[] args) {
        int rez = Verify.NeedToBeAuthenticated(args);
        if (rez == 0) return;

        String[] names = args[1].split("'");
        String name = names[1];

        String[] passwords = args[2].split("'");
        String password = passwords[1];

        rez = Verify.LoginFailed(args, name, password);
        if (rez == 0) return;

        String[] ids = args[3].split("'");
        String id = ids[1];

        Get.QuizzDetailsById(id);
    }

    public static void SubmitQuizz(String[] args) {
        int rez = Verify.NeedToBeAuthenticated(args);
        if (rez == 0) return;

        String[] names = args[1].split("'");
        String name = names[1];

        String[] passwords = args[2].split("'");
        String password = passwords[1];

        rez = Verify.LoginFailed(args, name, password);
        if (rez == 0) return;

        String[] id_qs = args[3].split("'");
        String id_q = id_qs[1];

        rez = Verify.QuizNotFound(id_q);
        if (rez == 0) return;

        rez = Verify.SelfQuiz(name);
        if (rez == 0) return;

        String[] answers = new String[args.length - 4];
        int j = 0;
        for (int i = 4; i < args.length; i++){
            String[] id_as = args[i].split("'");
            String id_a = id_as[1];
            answers[j] = id_a;
            j++;
        }

        Add.Submission(id_q, name, answers);
    }

    public static void DeleteQuizz(String[] args) {
        int rez = Verify.NeedToBeAuthenticated(args);
        if (rez == 0) return;

        String[] names = args[1].split("'");
        String name = names[1];

        String[] passwords = args[2].split("'");
        String password = passwords[1];

        rez = Verify.LoginFailed(args, name, password);
        if (rez == 0) return;

        String[] ids = args[3].split("'");
        String id = ids[1];

        rez = Verify.QuizNotFound(id);
        if (rez == 0) return;

        Add.DeleteQuiz(id);
    }

    public static void GetMySolutions(String[] args) {
        int rez = Verify.NeedToBeAuthenticated(args);
        if (rez == 0) return;

        String[] names = args[1].split("'");
        String name = names[1];

        String[] passwords = args[2].split("'");
        String password = passwords[1];

        rez = Verify.LoginFailed2(name, password);
        if (rez == 0) return;

        Get.Solutions(name);
    }

    /**
     * <p>Creates the files if necessarily, where the data is saved.</p>
     */
    public static void CreateFiles() {
        try {
            File myObj = new File("src/main/java/com/example/project/users.txt");
            //noinspection ResultOfMethodCallIgnored
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File("src/main/java/com/example/project/questions.txt");
            //noinspection ResultOfMethodCallIgnored
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File("src/main/java/com/example/project/quizzes.txt");
            //noinspection ResultOfMethodCallIgnored
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File("src/main/java/com/example/project/submissions.txt");
            //noinspection ResultOfMethodCallIgnored
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * <p>Deletes all the files with the data.</p>
     */
    public static void DeleteFiles() {
        Path path = FileSystems.getDefault().getPath("src/main/java/com/example/project/users.txt");
        try {
            Files.delete(path);
            Users.id = 1;
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(x);
        }

        path = FileSystems.getDefault().getPath("src/main/java/com/example/project/questions.txt");
        try {
            Files.delete(path);
            Questions.id = 1;
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(x);
        }

        path = FileSystems.getDefault().getPath("src/main/java/com/example/project/quizzes.txt");
        try {
            Files.delete(path);
            Quizzes.id = 1;
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(x);
        }

        path = FileSystems.getDefault().getPath("src/main/java/com/example/project/submissions.txt");
        try {
            Files.delete(path);
            Quizzes.id = 1;
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(x);
        }
    }

}
