package com.gmail.maksimus40a.test.stand.employee.repositories;

import com.gmail.maksimus40a.test.stand.employee.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    int countOfEntities();

    List<Employee> getAllEmployees();

    List<Employee> getEmployeesByCriteria(String fieldName, String fieldValue, int limit);

    Optional<Employee> getEmployeeById(Integer id);

    Employee addEmployee(Employee employee);

    Optional<Employee> editEmployeeById(Integer id, Employee employee);

    boolean deleteEmployeeById(Integer id);
}
