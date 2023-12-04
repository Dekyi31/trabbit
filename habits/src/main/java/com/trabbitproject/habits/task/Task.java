package com.trabbitproject.habits.task;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private String description;
    private LocalDateTime date;
    private LocalDateTime dueDate;
    private LocalDateTime completedDate;
    private boolean completed;
}
