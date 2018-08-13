package com.gmail.maksimus40a.test.stand.entity.employee.services;

import com.gmail.maksimus40a.test.stand.entity.base.BaseRepository;
import com.gmail.maksimus40a.test.stand.entity.base.BaseService;
import com.gmail.maksimus40a.test.stand.entity.book.services.NoSuchSearchCriteriaException;
import com.gmail.maksimus40a.test.stand.entity.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Qualifier("employee-service")
public class EmployeeServiceImpl implements BaseService<Employee> {

    private final String LIMIT = "limit";

    private BaseRepository<Employee> employeeRepository;
    private List<String> fieldsOfEntity;

    @Autowired
    public EmployeeServiceImpl(BaseRepository<Employee> employeeRepository,
                               @Qualifier("employee-fields") List<String> fieldsOfEntity) {
        this.employeeRepository = employeeRepository;
        this.fieldsOfEntity = fieldsOfEntity;
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
        return requestParams.get(fieldsOfEntity.stream()
                .filter(fieldName -> requestParams.keySet()
                        .stream()
                        .anyMatch(field -> field.equals(fieldName)))
                .findFirst()
                .orElseThrow(() -> new NoSuchSearchCriteriaException("There is no entity by the required criterion.")));
    }

    private int getLimit(Map<String, String> requestParams) {
        return Integer.parseInt(requestParams.getOrDefault(LIMIT, String.valueOf(Integer.MAX_VALUE)));
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