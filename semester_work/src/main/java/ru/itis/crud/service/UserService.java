package ru.itis.crud.service;


import lombok.RequiredArgsConstructor;
import ru.itis.crud.model.User;
import ru.itis.crud.repository.UserRepository;


import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Optional<User> getByLogin(String login) {
        return repository.findByLogin(login);
    }


    public Optional<User> getById(Long id) {
        return Optional.empty();
    }

    public Set<Optional<User>> getAll() {
        return Set.of();
    }

    public Long create(User user) {
        return repository.create(user);
    }

    public boolean deleteById(Long id) {
        return false;
    }

    public void update(User user) {
        repository.updateById(user);
    }
}