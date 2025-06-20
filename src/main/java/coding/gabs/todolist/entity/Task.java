package coding.gabs.todolist.entity;

import coding.gabs.todolist.entity.enums.Priority;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_task")
public class Task {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID owner;
    @Column(length = 50, nullable = false)
    private String title;
    private String description;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    private LocalDateTime startDateTime = creationDateTime;
    private LocalDateTime updateDateTime;
    private LocalDateTime endingDateTime;
    private boolean isCompleted = false;
    @Enumerated(EnumType.STRING)
    private Priority priority;
}
