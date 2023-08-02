package com.example.project;

import java.util.ArrayList;

public class Questions {

    private final Users user;

    private final String text;

    private final String type;

    private final ArrayList<String> answers;

    static int id = 1;

    private final int my_id;

    public Questions(Users user, String text, String type, ArrayList<String> answers) {
        this.user = user;
        this.text = text;
        this.type = type;
        this.answers = answers;
        this.my_id = id++;
    }

    public Users getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public int getMy_id() {
        return my_id;
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (String s : answers){
            answer.append(s).append(",");
        }
        return user.toString() + "," + text + "," + type + "," + answer  + my_id;
    }
}
