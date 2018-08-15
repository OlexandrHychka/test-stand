package com.gmail.maksimus40a.test.stand.features.employee.controllers;

import com.gmail.maksimus40a.test.stand.features.base.interfaces.BaseService;
import com.gmail.maksimus40a.test.stand.features.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/itcompany")
public class EmployeeController {

    private BaseService<Employee> employeeService;

    @Autowired
    public EmployeeController(@Qualifier("employee-service") BaseService<Employee> employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) Map<String, String> requestParams) {
        return (requestParams.isEmpty()) ?
                new ResponseEntity<>(employeeService.getAllEntities(), HttpStatus.FOUND) :
                new ResponseEntity<>(employeeService.getEntitiesByCriteria(requestParams), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeService
                .getEntityById(id)
                .orElseThrow(() -> new NoSuchElementException("There isn't resource with such id : " + id));
        return new ResponseEntity<>(employee, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee, UriComponentsBuilder ucb) {
        Employee createdEmployee = employeeService.addEntity(employee);
        return ResponseEntity.
                created(ucb.path("/api/itcompany/{id}").buildAndExpand(createdEmployee.getId()).toUri())
                .body(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> editEmployeeById(@PathVariable Integer id, @RequestBody Employee employee) {
        Employee editedEmployee = employeeService
                .editEntityById(id, employee)
                .orElseThrow(() -> new NoSuchElementException("There isn't resource with such id : " + id));
        return ResponseEntity.ok(editedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Integer id) {
        employeeService.deleteEntityById(id);
        return ResponseEntity.ok().build();
    }
}