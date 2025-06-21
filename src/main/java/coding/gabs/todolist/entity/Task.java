package coding.gabs.todolist.entity;

import coding.gabs.todolist.entity.enums.Priority;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_task")
public class Task {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private UUID ownerId;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 512)
    private String description;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    @CreationTimestamp
    private LocalDateTime startDateTime;
    private LocalDateTime updateDateTime;
    private LocalDateTime endingDateTime;
    private boolean isCompleted = false;
    @Enumerated(EnumType.STRING)
    private Priority priority;


    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("title cannot be null");
        }

        if (title.length() > 50) {
            throw new IllegalArgumentException("title length must be less than 50 characters");
        }

        this.title = title;
    }
}
