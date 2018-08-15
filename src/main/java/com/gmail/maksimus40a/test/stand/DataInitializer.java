package com.gmail.maksimus40a.test.stand;

import com.gmail.maksimus40a.test.stand.features.base.interfaces.BaseRepository;
import com.gmail.maksimus40a.test.stand.features.book.domain.Book;
import com.gmail.maksimus40a.test.stand.features.employee.domain.Employee;
import com.gmail.maksimus40a.test.stand.security.domain.User;
import com.gmail.maksimus40a.test.stand.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gmail.maksimus40a.test.stand.security.service.HardcodeUserCredentials.HARDCODE_USER_NAME;
import static com.gmail.maksimus40a.test.stand.security.service.HardcodeUserCredentials.HARDCODE_USER_PASSWORD;
import static com.gmail.maksimus40a.test.stand.utils.JsonUtils.mapJsonFileToListEntities;

@Component
public class DataInitializer implements CommandLineRunner {

    private final String PATH_TO_BOOKS_FILE = "data/books.json";
    private final String PATH_TO_EMPLOYEES_FILE = "data/itstaff.json";

    private BaseRepository<Book> bookRepository;
    private UserRepository userRepository;
    private BaseRepository<Employee> employeeRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(@Qualifier("book-repository") BaseRepository<Book> bookRepository,
                           @Qualifier("user-repository") UserRepository userRepository,
                           @Qualifier("employee-repository") BaseRepository<Employee> employeeRepository,
                           PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws IOException {
        initBooksData();
        initUserData();
        initEmployeeData();
    }

    private void initBooksData() throws IOException {
        List<Book> books = mapJsonFileToListEntities(PATH_TO_BOOKS_FILE, Book.class);
        books.forEach(bookRepository::addEntity);
    }

    private void initEmployeeData() throws IOException {
        List<Employee> employees = mapJsonFileToListEntities(PATH_TO_EMPLOYEES_FILE, Employee.class);
        employees.forEach(employeeRepository::addEntity);
    }

    private void initUserData() {
        userRepository.deleteAll();
        userRepository.addUsers(Arrays.asList(
                User.builder()
                        .username("root")
                        .password(passwordEncoder.encode("root"))
                        .roles(Collections.singletonList("ROLE_USER"))
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .build()
                ,
                User.builder()
                        .username(HARDCODE_USER_NAME.getCredential())
                        .password(passwordEncoder.encode(HARDCODE_USER_PASSWORD.getCredential()))
                        .roles(Collections.singletonList("ROLE_USER"))
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .build()
        ));
    }
}