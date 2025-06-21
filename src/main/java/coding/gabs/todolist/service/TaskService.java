package coding.gabs.todolist.service;

import coding.gabs.todolist.entity.Task;
import coding.gabs.todolist.exception.InvalidLocalDateTime;
import coding.gabs.todolist.exception.TaskNotFound;
import coding.gabs.todolist.exception.UserNotAuthorized;
import coding.gabs.todolist.repository.TaskRepository;
import coding.gabs.todolist.util.NullAttributesValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
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
        UUID userIdFromRequest = (UUID) request.getAttribute("userId");

        return taskRepository.findAllByOwnerId(userIdFromRequest)
                .orElseThrow(() -> new TaskNotFound("Any tasks attributed to this owner has not been found."));
    }

    public Task update(Task task, HttpServletRequest request, UUID taskId) {
        Task taskFromDatabase = this.taskRepository
                .findById(taskId)
                .orElseThrow(() -> new TaskNotFound("The task to update does not exist."));

        UUID userIdFromRequest = (UUID) request.getAttribute("userId");

        if (!taskFromDatabase.getOwnerId().equals(userIdFromRequest)) {
            throw new UserNotAuthorized("You can't change tasks from other user.");
        }


        NullAttributesValidator.copyNonNullProperties(task, taskFromDatabase);
        taskFromDatabase.setUpdateDateTime(LocalDateTime.now());
        return taskRepository.save(taskFromDatabase);
    }
}
