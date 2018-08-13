package com.gmail.maksimus40a.test.stand.entity.employee.domain;

import com.gmail.maksimus40a.test.stand.entity.base.IdEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Employee extends IdEntity {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String career;

    @Builder.Default
    private List<String> skills = new ArrayList<>();
}