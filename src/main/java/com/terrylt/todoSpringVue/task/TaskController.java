package com.terrylt.todoSpringVue.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/task")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }
    @GetMapping(path = "{taskId}")
    public Task getTaskById(@PathVariable(name = "taskId") Long taskId){
        return taskService.getTaskById(taskId);
    }

    @PutMapping(path = "{taskId}")
    public Task updateTask(@PathVariable(name = "taskId") Long taskId,@RequestBody TaskRequest taskRequest){
        return taskService.updateTask(taskId,taskRequest);
    }

    @PostMapping
    public Task addTask(@RequestBody TaskRequest taskRequest){
        return taskService.addTask(taskRequest);
    }

    @DeleteMapping(path = "{taskId}")
    public void deleteTask(@PathVariable(name = "taskId") Long taskId){
        taskService.deleteTask(taskId);
    }
}
