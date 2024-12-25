package ru.itis.dis301.lab10.service;

import ru.itis.dis301.lab10.model.Client;
import ru.itis.dis301.lab10.repository.ClientRepository;

import java.util.List;

public class ClientService {

    private ClientRepository repository;

    public ClientService() {
        repository = new ClientRepository();
    }

    public Client save(Client client) {
        return repository.addClient(client);
    }

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Client findById(Long id) {
        return repository.findById(id);
    }
}
