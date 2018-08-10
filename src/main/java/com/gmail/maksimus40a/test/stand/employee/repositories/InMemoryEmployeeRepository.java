package com.gmail.maksimus40a.test.stand.employee.repositories;

import com.gmail.maksimus40a.test.stand.bases.BaseRepository;
import com.gmail.maksimus40a.test.stand.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Qualifier("employee-repository")
public class InMemoryEmployeeRepository implements BaseRepository<Employee> {

    private Map<Integer, Employee> employees = new ConcurrentHashMap<>();
    private AtomicInteger nextIdGenerator = new AtomicInteger(1);

    @Override
    public int countOfEntities() {
        return employees.size();
    }

    @Override
    public List<Employee> getAllEntities() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public List<Employee> getEntitiesByCriteria(String criteria, long limit) {
        return employees.values()
                .stream()
                .filter(searchPredicate(criteria))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private Predicate<Employee> searchPredicate(String criteria) {
        return employee -> employee.getFirstName().equals(criteria) ||
                employee.getLastName().equals(criteria) ||
                employee.getEmail().equals(criteria) ||
                employee.getCareer().equals(criteria) ||
                employee.getSkills().stream()
                        .anyMatch(skill -> skill.equals(criteria));
    }

    @Override
    public Optional<Employee> getEntityById(Integer id) {
        if (id <= 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        return Optional.ofNullable(this.employees.get(id));
    }

    @Override
    public Employee addEntity(Employee employee) {
        int id = nextId();
        employee.setId(id);
        employees.put(id, employee);
        return employee;
    }

    @Override
    public Optional<Employee> editEntity(Integer id, Employee employee) {
        if (id <= 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        Employee updated = employees.get(id);
        updated.setId(id);
        updated.setFirstName(employee.getFirstName());
        updated.setLastName(employee.getLastName());
        updated.setEmail(employee.getEmail());
        updated.setCareer(employee.getCareer());
        updated.setSkills(employee.getSkills());
        employees.put(id, updated);
        return Optional.of(updated);
    }

    @Override
    public Optional<Employee> deleteEntityById(Integer id) {
        if (id < 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        Employee removed = employees.remove(id);
        return Optional.ofNullable(removed);
    }

    private Integer nextId() {
        return nextIdGenerator.getAndIncrement();
    }
}