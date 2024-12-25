package ru.itis.dis301.lab10.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Client {
    private Long id;
    private String name;
    private String email;
    private ClientInfo clientInfo;

    public Client() {}

    public Client(Long id, String name, String email, ClientInfo clientInfo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.clientInfo = clientInfo;
    }
}
