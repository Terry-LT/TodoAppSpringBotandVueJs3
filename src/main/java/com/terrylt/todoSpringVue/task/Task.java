package com.terrylt.todoSpringVue.task;

import com.terrylt.todoSpringVue.appuser.AppUser;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Task {
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    @Id
    private Long id;
    @JoinColumn(nullable = false,name = "app_user_id")//https://www.baeldung.com/jpa-join-column
    private AppUser appUser;
    private String title;
    @Nullable
    private String body;
    private String priority = "!";
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Task(AppUser appUser, String title, String body, String priority, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.appUser = appUser;
        this.title = title;
        this.body = body;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", appUser=" + appUser +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", priority='" + priority + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
