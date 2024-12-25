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
public class ScheduleTraining {
    private Long id;
    private String dayOfWeek;
    private String time;
    private Long coachId;

    private Coach coach;
    private List<GroupSportsman> groupSportsman;
}
