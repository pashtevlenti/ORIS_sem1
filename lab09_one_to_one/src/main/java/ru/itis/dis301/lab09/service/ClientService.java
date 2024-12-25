package ru.itis.dis301.lab09.service;

import ru.itis.dis301.lab09.model.Client;
import ru.itis.dis301.lab09.repository.ClientRepository;

public class ClientService {

    private ClientRepository repository;

    public ClientService() {
        repository = new ClientRepository();
    }

    public Client save(Client client) {
        return repository.addClient(client);
    }
}
