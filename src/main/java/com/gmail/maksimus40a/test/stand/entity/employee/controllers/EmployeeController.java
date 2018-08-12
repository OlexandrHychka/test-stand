package com.gmail.maksimus40a.test.stand.entity.employee.controllers;

import com.gmail.maksimus40a.test.stand.entity.base.BaseService;
import com.gmail.maksimus40a.test.stand.entity.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/itcompany")
public class EmployeeController {

    private BaseService<Employee> employeeService;

    @Autowired
    public EmployeeController(@Qualifier("employee-service") BaseService<Employee> employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public List<Employee> getAllEmployees(@RequestParam(required = false) Map<String, String> requestParams) {
        return (requestParams.isEmpty()) ? employeeService.getAllEntities() : employeeService.getEntitiesByCriteria(requestParams);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEntityById(id)
                .orElseThrow(() -> new NoSuchElementException("There isn't employee with such id " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEntity(employee);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editEmployeeById(@PathVariable Integer id, @RequestBody Employee employee) {
        employeeService.editEntityById(id, employee)
                .orElseThrow(() -> new NoSuchElementException("There isn't employee with such id " + id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Integer id) {
        employeeService.deleteEntityById(id);
    }
}