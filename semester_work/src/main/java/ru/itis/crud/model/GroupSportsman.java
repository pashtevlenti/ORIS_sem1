package ru.itis.crud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupSportsman {
    private Long id;
    private String groupName;
    private Long coachId;

    private User coach;
    private List<ScheduleTraining> trainings;
    private List<User> sportsmen;
}
