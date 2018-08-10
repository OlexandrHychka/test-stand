package com.gmail.maksimus40a.test.stand.employee.services;

import com.gmail.maksimus40a.test.stand.book.services.NoSuchSearchCriteriaException;
import com.gmail.maksimus40a.test.stand.employee.domain.Employee;
import com.gmail.maksimus40a.test.stand.employee.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final String LIMIT_FIELD_NOTATION = "limit";

    private EmployeeRepository employeeRepository;
    private List<String> employeeFieldsNames = new ArrayList<>(
            Arrays.asList("firstName", "lastName", "email", "career", "skills")
    );

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public List<Employee> getEmployeesByCriteria(Map<String, String> requestParams) {
        return employeeRepository.getEmployeesByCriteria(getValue(requestParams), getLimit(requestParams));
    }

    private String getValue(Map<String, String> requestParams) {
        return requestParams.get(employeeFieldsNames.stream()
                .filter(fieldName -> requestParams.keySet()
                        .stream()
                        .anyMatch(field -> field.equals(fieldName)))
                .findFirst()
                .orElseThrow(() -> new NoSuchSearchCriteriaException("Not such search criteria.")));
    }

    private int getLimit(Map<String, String> requestParams) {
        return Integer.parseInt(requestParams.getOrDefault(LIMIT_FIELD_NOTATION, String.valueOf(Integer.MAX_VALUE)));
    }

    @Override
    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepository.getEmployeeById(id);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.addEmployee(employee);
    }

    @Override
    public Optional<Employee> editEmployeeById(Integer id, Employee employee) {
        return employeeRepository.editEmployeeById(id, employee);
    }

    @Override
    public boolean deleteEmployeeById(Integer id) {
        return employeeRepository.deleteEmployeeById(id);
    }
}