package com.trabbitproject.habits.reward;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "rewards")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reward {
    @Id
    private ObjectId objectId;
    private ObjectId userId;
    private String description;
    private LocalDateTime awardedDate;
}
