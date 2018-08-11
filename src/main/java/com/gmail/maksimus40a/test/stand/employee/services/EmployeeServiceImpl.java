package com.gmail.maksimus40a.test.stand.employee.services;

import com.gmail.maksimus40a.test.stand.bases.BaseService;
import com.gmail.maksimus40a.test.stand.bases.SearchRepository;
import com.gmail.maksimus40a.test.stand.book.services.NoSuchSearchCriteriaException;
import com.gmail.maksimus40a.test.stand.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("employee-service")
public class EmployeeServiceImpl implements BaseService<Employee> {

    private final String LIMIT_FIELD_NOTATION = "limit";

    private SearchRepository<Employee> employeeRepository;
    private List<String> employeeFieldsNames = new ArrayList<>(
            Arrays.asList("firstName", "lastName", "email", "career", "skills")
    );

    @Autowired
    public EmployeeServiceImpl(SearchRepository<Employee> employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEntities() {
        return employeeRepository.getAllEntities();
    }

    @Override
    public List<Employee> getEntitiesByCriteria(Map<String, String> requestParams) {
        return employeeRepository.getEntitiesByCriteria(getSearchCriteria(requestParams), getLimit(requestParams));
    }

    private String getSearchCriteria(Map<String, String> requestParams) {
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
    public Optional<Employee> getEntityById(Integer id) {
        return employeeRepository.getEntityById(id);
    }

    @Override
    public Employee addEntity(Employee employee) {
        return employeeRepository.addEntity(employee);
    }


    @Override
    public Optional<Employee> editEntityById(Integer id, Employee entity) {
        return employeeRepository.editEntity(id, entity);
    }

    @Override
    public Optional<Employee> deleteEntityById(Integer id) {
        return employeeRepository.deleteEntityById(id);
    }
}