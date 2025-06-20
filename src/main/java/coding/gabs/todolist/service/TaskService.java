package coding.gabs.todolist.service;

import coding.gabs.todolist.entity.Task;
import coding.gabs.todolist.exception.InvalidLocalDateTime;
import coding.gabs.todolist.exception.TaskNotFound;
import coding.gabs.todolist.repository.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task, HttpServletRequest request) {
        task.setOwnerId((UUID) request.getAttribute("userId"));
        URI taskURI = URI.create("/task/" + task.getId());
        request.setAttribute("taskURI", taskURI);

        if (task.getEndingDateTime() != null &&
            task.getStartDateTime().isAfter(
                    task.getEndingDateTime())) {
            throw new InvalidLocalDateTime("Ending date time must be after starting date time.");
        }

        return taskRepository.save(task);
    }

    public List<Task> getTasksFromActiveUser(HttpServletRequest request) {
        UUID taskOwnerId = (UUID) request.getAttribute("userId");
        Optional<List<Task>> tasks = this.taskRepository.findAllByOwnerId(taskOwnerId);

        if (tasks.isEmpty()) {
            throw new TaskNotFound("Any tasks attributed to this owner has not been found.");
        }

        return tasks.get();
    }
}
