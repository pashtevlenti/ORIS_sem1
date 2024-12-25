package ru.itis.crud.service;


import lombok.RequiredArgsConstructor;
import ru.itis.crud.model.Coach;
import ru.itis.crud.model.Sportsman;
import ru.itis.crud.model.User;
import ru.itis.crud.repository.CoachRepository;
import ru.itis.crud.repository.SportsmanRepository;

import java.util.List;

@RequiredArgsConstructor
public class SportsmanService {
    private  final SportsmanRepository repository;

    public void update(Long id, Sportsman sportsman){
        repository.updateByUserId(id,sportsman);
    }
    public Sportsman getByUserId(Long id){
        return repository.findByUserId(id);
    }

    public List<User> getSportsmenByCoach(Long coachId) {
       return repository.findSportsmenByCoach(coachId);
    }

    public List<User> getSportsmenByCoachAndGroupId(Long coachId, Long groupId) {
        return repository.findSportsmenByCoachAndGroupId(coachId,groupId);
    }
}
