package ru.itis.dis301.lab09.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Client {
    private Long id;
    private String name;
    private String email;
    private ClientInfo clientInfo;
}
