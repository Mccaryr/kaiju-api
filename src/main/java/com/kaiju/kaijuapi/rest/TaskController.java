package com.kaiju.kaijuapi.rest;

import com.kaiju.kaijuapi.dao.TaskDAO;
import com.kaiju.kaijuapi.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskDAO taskDAO;

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        System.out.println("Getting all Tasks");
        return taskDAO.getAllTasks();
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        System.out.println("Getting a task");
        Task task = taskDAO.getTask(id);
        if(task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/task")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        System.out.println("Task being created: " + task);
        taskDAO.createTask(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PatchMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        System.out.println("taskId being updated: " + id);
        Task updatedTask = taskDAO.updateTask(id, task);
        if(updatedTask != null) {
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        System.out.println("Deleting taskId: " + id);
        boolean deletedTask = taskDAO.deleteTask(id);
        if(deletedTask) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
