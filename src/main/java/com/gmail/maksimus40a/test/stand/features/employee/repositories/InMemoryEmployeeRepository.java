package com.gmail.maksimus40a.test.stand.features.employee.repositories;

import com.gmail.maksimus40a.test.stand.features.base.abstraction.AbstractInMemoryRepository;
import com.gmail.maksimus40a.test.stand.features.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Repository
@Qualifier("employee-repository")
public class InMemoryEmployeeRepository extends AbstractInMemoryRepository<Employee> {

    public InMemoryEmployeeRepository(@Qualifier("employee-search") Function<String, Predicate<Employee>> searchFunction) {
        super(searchFunction);
    }

    @Override
    public Optional<Employee> editEntity(Integer id, Employee employee) {
        if (id <= 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        Employee updated = entityMap.get(id);
        updated.setId(id);
        updated.setFirstName(employee.getFirstName());
        updated.setLastName(employee.getLastName());
        updated.setEmail(employee.getEmail());
        updated.setCareer(employee.getCareer());
        updated.setSkills(employee.getSkills());
        entityMap.put(id, updated);
        return Optional.of(updated);
    }
}