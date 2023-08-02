package com.example.project;

import java.util.ArrayList;

public class Quizzes {
    private final Users user;

    private final String name;

    private final ArrayList<String> questions;

    private final int my_id;

    static int id = 1;

    public Quizzes(Users user, String name, ArrayList<String> questions) {
        this.user = user;
        this.name = name;
        this.questions = questions;
        this.my_id = id++;
    }

    public Users getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public int getMy_id() {
        return my_id;
    }

    @Override
    public String toString() {
        StringBuilder question = new StringBuilder();
        for (String s : questions){
            question.append(s).append(",");
        }
        return user.toString() + "," + name + "," + question + my_id;
    }
}
