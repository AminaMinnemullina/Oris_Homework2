package ru.oris.homework.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder

public class User {
    private long id;
    private String name;
    private String surname;
    private Integer age;

    private String city;
    private String country;
    private String email;
}
