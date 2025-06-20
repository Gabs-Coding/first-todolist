package coding.gabs.todolist.controller;

import coding.gabs.todolist.entity.Task;
import coding.gabs.todolist.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
        return ResponseEntity.created((URI) request.getAttribute("taskURI"))
                .body(createdTask);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Task>> getTasks(HttpServletRequest request) {
        return ResponseEntity.ok(taskService.getTasksFromActiveUser(request));
    }

}
