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
    private List<String> employeeFieldsNames = new ArrayList<>();

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void init() {
        Stream.of(Employee.class.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .map(Field::getName)
                .forEach(employeeFieldsNames::add);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public List<Employee> getEmployeesByCriteria(Map<String, String> requestParams) {
        int limit = (requestParams.containsKey(LIMIT_FIELD_NOTATION)) ?
                Integer.parseInt(requestParams.get(LIMIT_FIELD_NOTATION)) :
                Integer.MAX_VALUE;
        requestParams.remove(LIMIT_FIELD_NOTATION);
        if (requestParams.size() > 1) throw new UnsupportedOperationException("Not support multiply search.");
        String fieldName = null;
        for (String fn : employeeFieldsNames) {
            if (requestParams.containsKey(fn)) {
                fieldName = fn;
            }
        }
        if (Objects.isNull(fieldName)) throw new NoSuchSearchCriteriaException("Not such search criteria.");
        String fieldValue = requestParams.get(fieldName);
        return employeeRepository.getEmployeesByCriteria(fieldName, fieldValue, limit);
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