package com.example.todoapplication.model;

import java.util.ArrayList;

public class SingletonTasks {
    private static SingletonTasks instance;

    public static SingletonTasks getInstance() {
        if (instance == null)
            instance = new SingletonTasks();
        return instance;
    }

    public ArrayList<Task> taskList;
    public ArrayList<Task> taskListFiltered;

    public SingletonTasks() {
        taskList = new ArrayList<>();
        taskListFiltered = new ArrayList<>();
    }
}
