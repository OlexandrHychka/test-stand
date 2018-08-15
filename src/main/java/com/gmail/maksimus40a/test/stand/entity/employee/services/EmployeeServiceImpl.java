package com.gmail.maksimus40a.test.stand.entity.employee.services;

import com.gmail.maksimus40a.test.stand.entity.base.AbstractService;
import com.gmail.maksimus40a.test.stand.entity.base.BaseRepository;
import com.gmail.maksimus40a.test.stand.entity.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("employee-service")
public class EmployeeServiceImpl extends AbstractService<Employee> {

    @Autowired
    public EmployeeServiceImpl(@Qualifier("employee-repository") BaseRepository<Employee> employeeRepository,
                               @Qualifier("employee-fields") List<String> fieldsOfEntity) {
        super(employeeRepository, fieldsOfEntity);
    }
}