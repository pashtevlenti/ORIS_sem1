package ru.itis.crud.service;


import lombok.RequiredArgsConstructor;
import ru.itis.crud.model.Coach;
import ru.itis.crud.model.GroupSportsman;
import ru.itis.crud.model.User;
import ru.itis.crud.repository.CoachRepository;

import java.util.List;

@RequiredArgsConstructor
public class CoachService {
    private  final CoachRepository repository;

    public void update(Long id, Coach coach,String sport){
        repository.updateByUserId(id,coach,sport);
    }
    public Coach getByUserId(Long id){
        return repository.findByUserId(id);
    }
    public List<User> getAvailableCoach(Long sportsmanId){
        return repository.findAvailableCoaches(sportsmanId);
    }
    public List<User> getSportsmanCoaches(Long sportsmanId){
        return repository.findCoaches(sportsmanId);
    }

    public void assignCoachToSportsman(Long sportsmanId, Long i) {
        repository.assignCoachToSportsman(sportsmanId,i);
    }
    public void deleteCoachToSportsman(Long sportsmanId, Long i) {
        repository.deleteCoachToSportsman(sportsmanId,i);
    }

    public List<GroupSportsman> getGroupByCoachId(Long l) {
        return repository.findGroupByCoachId(l);
    }
}
