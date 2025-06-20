package coding.gabs.todolist.service;

import coding.gabs.todolist.entity.Task;
import coding.gabs.todolist.repository.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task, HttpServletRequest request) {
        task.setOwner((UUID) request.getAttribute("userId"));

        return taskRepository.save(task);
    }
}
