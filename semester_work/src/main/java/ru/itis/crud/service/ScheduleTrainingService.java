package ru.itis.crud.service;

import lombok.RequiredArgsConstructor;
import ru.itis.crud.model.ScheduleTraining;
import ru.itis.crud.repository.ScheduleTrainingRepository;

@RequiredArgsConstructor
public class ScheduleTrainingService {
    private final ScheduleTrainingRepository repository;

    public Long create(ScheduleTraining scheduleTraining) {
        return repository.create(scheduleTraining);
    }

    public void createScheduleTrainingGroupSportsman(Long schedule_training_id, Long group_sportsman_id) {
        repository.createScheduleTrainingGroupSportsman(schedule_training_id,group_sportsman_id);
    }

    public void deleteTrainingFromGroup(Long trainingId, Long groupId) {
        repository.deleteTrainingFromGroup(trainingId,groupId);
    }

    public Long getByDayOfWeekAndTimeAndCoachId(String day, String time, Long id) {
        return repository.findByDayOfWeekAndTimeAndCoachId(day,time,id);
    }

    public boolean getByTrainingIdAndGroupId(Long trainingId, Long groupId) {
        return repository.findByTrainingIdAndGroupId(trainingId,groupId);
    }
}
