package ru.itis.crud.service;

import lombok.RequiredArgsConstructor;
import ru.itis.crud.model.GroupSportsman;
import ru.itis.crud.repository.GroupSportsmanRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GroupSportsmanService {
    private final GroupSportsmanRepository repository;

    public List<Optional<GroupSportsman>> getByCoachId(Long coachId){
        return repository.findByCoachId(coachId);
    }

    public Long create(GroupSportsman groupSportsman) {
        return repository.create(groupSportsman);
    }

    public void createGroupSportsmanToSportsman(Long l, Long id) {
        repository.createGroupSportsmanToSportsman(l,id);
    }

    public List<Optional<GroupSportsman>> getBySportsmanId(Long id) {
        return repository.findBySportsmanId(id);
    }

    public void deleteSportsmanFromGroup(Long groupId, Long sportsmanId) {
        repository.deleteSportsmanFromGroup(groupId,sportsmanId);
    }

    public void deleteGroupById(Long groupId) {
        repository.deleteGroupById(groupId);
    }
}
