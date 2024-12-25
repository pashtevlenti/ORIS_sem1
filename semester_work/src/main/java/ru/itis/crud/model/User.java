package ru.itis.crud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User{
    private Long id;
    private String login;
    private String password;

    private String name;
    private char gender;
    private int age;
    private String phone;
    private String address;

    private Coach coach;
    private Sportsman sportsman;
    private Worker worker;


}
