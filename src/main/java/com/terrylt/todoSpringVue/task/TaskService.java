package com.terrylt.todoSpringVue.task;

import com.terrylt.todoSpringVue.appuser.AppUser;
import com.terrylt.todoSpringVue.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    public Task addTask(TaskRequest taskRequest){
        //TODO: Only auth users can add task
        Optional<AppUser> appUser = appUserRepository.findById(taskRequest.getApp_user_id());
        //check if user exists
        if (appUser.isEmpty()){
            throw new IllegalStateException("Such user does not exists");
        }
        Task task = new Task(
                appUser.get(),
                taskRequest.getTitle(),
                taskRequest.getBody(),
                taskRequest.getPriority(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        //Save task to database
        taskRepository.save(task);
        return task;
    }
    public Task getTaskById(Long taskId){
        //Check if task exists
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()){
            throw new IllegalStateException("Task with such id does not exists!");
        }
        return task.get();
    }
    public Task updateTask(Long taskId,TaskRequest taskRequest){
        //TODO: Only owner can update task
        //Check if task exists
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()){
            throw new IllegalStateException("Task with such id does not exists!");
        }
        task.get().setBody(taskRequest.getBody());
        task.get().setTitle(taskRequest.getTitle());
        task.get().setPriority(taskRequest.getPriority());
        task.get().setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task.get());

        return task.get();
    }
    public void deleteTask(Long taskId){
        //TODO: Only owner can delete task
        //TODO: Only auth users can add task
        //Check if task exists
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()){
            throw new IllegalStateException("Task with such id does not exists!");
        }
        //Delete task from database
        taskRepository.delete(task.get());
    }
}
