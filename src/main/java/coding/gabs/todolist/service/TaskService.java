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

        if (task.getEndingDateTime() != null &&
            task.getStartDateTime().isAfter(
                    task.getEndingDateTime())) {
            throw new InvalidLocalDateTime("Ending date time must be after starting date time.");
        }

        return taskRepository.save(task);
    }
}
