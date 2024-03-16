package com.kaiju.kaijuapi.service;

import com.kaiju.kaijuapi.entity.Task;

import java.util.List;

public interface TaskService {

    void createTask(Task task);

    Task getTask(Long id);
    Task updateTask(Task task);

    List<Task> getAllTasks();
    void deleteTask(Long id);

//    List<Task> getTasksByUser(User user);
}
