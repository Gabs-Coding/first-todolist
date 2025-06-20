package coding.gabs.todolist.service;

import coding.gabs.todolist.entity.Task;
import coding.gabs.todolist.exception.InvalidLocalDateTime;
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

        if (task.getEndingDateTime().isBefore(task.getStartDateTime())) {
            throw new InvalidLocalDateTime("The ending date of the task shouldn't be before the starting date.");
        }

        return taskRepository.save(task);
    }
}
