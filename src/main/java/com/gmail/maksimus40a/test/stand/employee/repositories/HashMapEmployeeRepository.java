package com.gmail.maksimus40a.test.stand.employee.repositories;

import com.gmail.maksimus40a.test.stand.employee.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class HashMapEmployeeRepository implements EmployeeRepository {

    private Map<Integer, Employee> employees = new ConcurrentHashMap<>();
    private AtomicInteger nextIdGenerator = new AtomicInteger(1);

    @Override
    public int countOfEntities() {
        return employees.size();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public Optional<Employee> getEmployeeById(Integer id) {
        if (id <= 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        return Optional.ofNullable(this.employees.get(id));
    }

    @Override
    public Employee addEmployee(Employee employee) {
        int id = nextId();
        employee.setId(id);
        employees.put(id, employee);
        return employee;
    }

    @Override
    public Optional<Employee> editEmployeeById(Integer id, Employee employee) {
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
    public boolean deleteEmployeeById(Integer id) {
        if (id < 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return false;
        Employee removed = employees.remove(id);
        return Objects.nonNull(removed);
    }

    private Integer nextId() {
        return nextIdGenerator.getAndIncrement();
    }
}