package com.kaiju.kaijuapi.dao;

import com.kaiju.kaijuapi.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TaskDAO {

    @PersistenceContext
    EntityManager entityManager;

    public List<Task> getAllTasks() {
        TypedQuery<Task> query = entityManager.createQuery("Select t FROM Task t", Task.class);
        return query.getResultList();
    }

    @Transactional
    public void createTask(Task task) {
        entityManager.persist(task);
    }

    @Transactional
    public boolean deleteTask(Long id) {
        Task taskToDelete = entityManager.find(Task.class, id);
        if(taskToDelete != null) {
            entityManager.remove(taskToDelete);
            return true;
        }
        return false;
    }

    @Transactional
    public Task updateTask(Long id, Task task) {
        Task taskToUpdate = entityManager.find(Task.class, id);
        if(taskToUpdate != null) {
            taskToUpdate.setAssignedEmail(task.getAssignedEmail());
            taskToUpdate.setDescription(task.getDescription());
            taskToUpdate.setStatus(task.getStatus());
            taskToUpdate.setTitle(task.getTitle());
            entityManager.merge(taskToUpdate);
            return taskToUpdate;
        }
        return null;
    }

    public Task getTask(Long id) {
        return entityManager.find(Task.class, id);
    }
}
