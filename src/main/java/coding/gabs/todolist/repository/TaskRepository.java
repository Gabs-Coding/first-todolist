package coding.gabs.todolist.repository;

import coding.gabs.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
