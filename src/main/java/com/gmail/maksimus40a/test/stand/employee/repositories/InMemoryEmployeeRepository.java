package com.gmail.maksimus40a.test.stand.employee.repositories;

import com.gmail.maksimus40a.test.stand.bases.SearchRepository;
import com.gmail.maksimus40a.test.stand.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Repository
@Qualifier("employee-repository")
public class InMemoryEmployeeRepository extends SearchRepository<Employee> {

    public InMemoryEmployeeRepository(@Qualifier("employee-search") Function<String, Predicate<Employee>> searchFunction) {
        super(searchFunction);
    }

    @Override
    public int countOfEntities() {
        return entityMap.size();
    }

    @Override
    public List<Employee> getAllEntities() {
        return new ArrayList<>(entityMap.values());
    }

    @Override
    public Optional<Employee> getEntityById(Integer id) {
        if (id <= 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        return Optional.ofNullable(entityMap.get(id));
    }

    @Override
    public Employee addEntity(Employee employee) {
        int id = nextId();
        employee.setId(id);
        entityMap.put(id, employee);
        return employee;
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

    @Override
    public Optional<Employee> deleteEntityById(Integer id) {
        if (id < 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        Employee removed = entityMap.remove(id);
        return Optional.ofNullable(removed);
    }
}