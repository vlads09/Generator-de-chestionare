package com.example.project;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Add {
    /**
     * <p>Saves a new user from Users Class in the text file users.txt.</p>
     * @param user object ready to be written
     */
    public static void User(Users user) {
        try (FileWriter fw = new FileWriter("src/main/java/com/example/project/users.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            fw.write(user + "\n");
            System.out.print("{ 'status' : 'ok', 'message' : 'User created successfully'}");
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    /**
     * <p>Saves a new question from Questions Class in the text file questions.txt.</p>
     * @param question object ready to be written
     */
    public static void Question(Questions question) {
        try (FileWriter fw = new FileWriter("src/main/java/com/example/project/questions.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            fw.write(question + "\n");
            System.out.print("{ 'status' : 'ok', 'message' : 'Question added successfully'}");
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    /**
     * <p>Saves a new quiz from Quizzes Class in the text file quizzes.txt.</p>
     * @param quiz object ready to be written
     */
    public static void Quiz(Quizzes quiz) {
        try (FileWriter fw = new FileWriter("src/main/java/com/example/project/quizzes.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            fw.write(quiz + "\n");
            System.out.print("{ 'status' : 'ok', 'message' : 'Quizz added succesfully'}");

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    /**
     * <p>This method calculates the result obtained by an user with "name" as username at a quiz with id "id" having
     * the answers saved in the array "answers". Firstly, it checks if the user did the quiz in the past
     * by accessing the submission.txt file where there are all the submissions made and it returns an error if so.
     * Secondly, if the checking went right, it accesses the questions.txt file to get the answers' correctness and
     * calculates the result based on the ids' of both user's submission and the answers in the questions.</p>
     * @param id quiz's id
     * @param name username
     * @param answers user's answers
     */
    public static void Submission(String id, String name, String[] answers) {
        String name_q = "";
        try {
            float result = 0.00f;
            File myObj = new File("src/main/java/com/example/project/quizzes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                name_q = data[3];
                if (id.equals(data[data.length - 1])) {
                    File myUser = new File("src/main/java/com/example/project/submissions.txt"); // verific daca userul a mai facut quizzul
                    Scanner myRUser = new Scanner(myUser);
                    while (myRUser.hasNextLine()) {
                        String[] dataU = myRUser.nextLine().split(",");
                        if (name.equals(dataU[0]) && name_q.equals(dataU[1])) {
                            System.out.println("{ 'status' : 'error', 'message' : 'You already submitted this quiz'}");
                            myRUser.close();
                            myReader.close();
                            return;
                        }
                    }
                    myRUser.close();

                    File myQuiz = new File("src/main/java/com/example/project/questions.txt");
                    Scanner myQuizzer = new Scanner(myQuiz);
                    int a_id = 0; //answer's id in the quiz
                    int answer = 0; //index of the user's answer
                    float rez_per_q = 100 / ((float) data.length - 5);
                    while (myQuizzer.hasNextLine()) {
                        String[] data2 = myQuizzer.nextLine().split(",");
                        if (data2[4].equals("'single'")) {
                            for (int k = 5; k < data2.length - 1; k += 2) {
                                a_id++;
                                if (Integer.parseInt(answers[answer]) == a_id) {
                                    String[] data2_TorF = data2[k + 1].split("'");
                                    String TorF = data2_TorF[1];
                                    if (TorF.equals("1")) {
                                        //System.out.println("a intrat pe plus");
                                        result += rez_per_q;
                                    } else {
                                        //System.out.println("a intrat pe minus");
                                        result -= rez_per_q;
                                    }
                                    answer++;
                                    if (answer == answers.length) break;
                                }
                            }
                        } else if (data2[4].equals("'multiple'")) {
                            int correct_answers = 0;
                            int wrong_answers = 0;
                            for (int k = 5; k < data2.length - 1; k += 2) {
                                String[] data2_TorF = data2[k + 1].split("'");
                                String TorF = data2_TorF[1];
                                if (TorF.equals("1")) correct_answers++;
                                else wrong_answers++;
                            }

                            for (int k = 5; k < data2.length - 1; k += 2) {
                                a_id++;
                                if (Integer.parseInt(answers[answer]) == a_id) {
                                    String[] data2_TorF = data2[k + 1].split("'");
                                    String TorF = data2_TorF[1];
                                    if (TorF.equals("1")) {
                                        result += rez_per_q / correct_answers;
                                    } else {
                                        result -= rez_per_q / wrong_answers;
                                    }
                                    answer++;
                                    if (answer == answers.length) break;
                                }
                            }

                        }
                    }
                    myQuizzer.close();

                }
            }
            int final_result = Math.round(result);
            if (result < 0) {
                System.out.println("{ 'status' : 'ok', 'message' : '0 points'}");
            } else {
                System.out.println("{ 'status' : 'ok', 'message' : '" + final_result + " points'}");
            }

            try (FileWriter fw = new FileWriter("src/main/java/com/example/project/submissions.txt", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                fw.write(name + "," + name_q + "," + id + "," + final_result);
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * <p>Removes the quiz with id "id" and its information from the quizzes.txt file.</p>
     * @param id quiz's id
     */
    public static void DeleteQuiz(String id) {
        ArrayList<String> newOnes = new ArrayList<>();
        try {
            File myObj = new File("src/main/java/com/example/project/quizzes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (id.equals(data[data.length - 1])) {
                    continue;
                }
                newOnes.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Path path = FileSystems.getDefault().getPath("src/main/java/com/example/project/quizzes.txt");
        try {
            Files.delete(path);
            Users.id = 1;
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(x);
        }

        try {
            File myObj = new File("src/main/java/com/example/project/quizzes.txt");
            //noinspection ResultOfMethodCallIgnored
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter("src/main/java/com/example/project/quizzes.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            if(newOnes.isEmpty()) {
                System.out.println("{ 'status' : 'ok', 'message' : 'Quizz deleted successfully'}");
                return;
            }
            for (String s : newOnes)
                fw.write(s);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

        System.out.println("{ 'status' : 'ok', 'message' : 'Quizz deleted successfully'}");
    }
}
