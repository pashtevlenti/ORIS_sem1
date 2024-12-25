package ru.itis.crud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sportsman {
    private Long id;
    private String rank;
    private Long userId;


    private List<GroupSportsman> groupSportsman;
    private List<Coach> coaches;
}
