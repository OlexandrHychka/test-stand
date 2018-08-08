package com.gmail.maksimus40a.test.stand.employee.services;

import com.gmail.maksimus40a.test.stand.employee.domain.Employee;
import com.gmail.maksimus40a.test.stand.employee.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
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