package com.example.medhagupta.todolist;

/**
 * Created by Medha Gupta on 11/3/2016.
 */
public class ToDoList {
    private String title, details;
    int id;
    public ToDoList() {
    }

    public ToDoList(String title, String details) {
        this.title = title;
        this.details = details;
        id=0;

    }

    public ToDoList(String title, String details, int id) {
        this.id=id;
        this.title = title;
        this.details = details;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
