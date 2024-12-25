package ru.itis.crud.service;


import lombok.RequiredArgsConstructor;
import ru.itis.crud.model.Coach;
import ru.itis.crud.model.Worker;
import ru.itis.crud.repository.CoachRepository;
import ru.itis.crud.repository.WorkerRepository;

@RequiredArgsConstructor
public class WorkerService {
    private  final WorkerRepository repository;

    public void update(Long id, Worker worker){
        repository.updateByUserId(id,worker);
    }
    public Worker getByUserId(Long id){
        return repository.findByUserId(id);
    }

}
