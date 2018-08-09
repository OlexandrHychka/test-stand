package com.gmail.maksimus40a.test.stand.employee.services;

import com.gmail.maksimus40a.test.stand.employee.domain.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    List<Employee> getEmployeesByCriteria(Map<String, String> requestParams);

    Optional<Employee> getEmployeeById(Integer id);

    Employee addEmployee(Employee employee);

    Optional<Employee> editEmployeeById(Integer id, Employee employee);

    boolean deleteEmployeeById(Integer id);
}