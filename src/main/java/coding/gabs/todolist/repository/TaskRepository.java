package coding.gabs.todolist.repository;

import coding.gabs.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<List<Task>> findAllByOwnerId(UUID ownerId);
}
