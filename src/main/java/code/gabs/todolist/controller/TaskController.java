package code.gabs.todolist.controller;

import code.gabs.todolist.entity.Task;
import code.gabs.todolist.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/createTask")
    public ResponseEntity<Task> create(@RequestBody Task task, HttpServletRequest request) {
        Task createdTask = taskService.createTask(task, request);
        URI uriOfTheTaskCreated = URI.create("/task/" + createdTask.getId());
        return ResponseEntity.created(uriOfTheTaskCreated).body(createdTask);
    }
}
