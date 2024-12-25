package ru.itis.dis301.homework;

import lombok.Getter;
@Getter
public class Profession {
    private Long id;
    private String name;

    public Profession() {}
    public Profession(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
