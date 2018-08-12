package com.gmail.maksimus40a.test.stand.entity.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String career;

    @Builder.Default
    private List<String> skills = new ArrayList<>();
}