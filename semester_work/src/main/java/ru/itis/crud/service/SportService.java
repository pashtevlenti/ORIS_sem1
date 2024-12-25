package ru.itis.crud.service;


import lombok.RequiredArgsConstructor;
import ru.itis.crud.model.Sport;
import ru.itis.crud.model.User;
import ru.itis.crud.repository.SportRepository;
import ru.itis.crud.repository.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class SportService {

    private final SportRepository repository;

    public List<Optional<Sport>> getAll() {
        return repository.findAll();
    }
    public Sport getById(Long id) {
        return repository.findById(id);
    }

}