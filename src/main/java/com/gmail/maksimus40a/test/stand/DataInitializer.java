package com.gmail.maksimus40a.test.stand;

import com.gmail.maksimus40a.test.stand.book.domain.Book;
import com.gmail.maksimus40a.test.stand.book.repositories.BookRepository;
import com.gmail.maksimus40a.test.stand.employee.domain.Employee;
import com.gmail.maksimus40a.test.stand.employee.repositories.EmployeeRepository;
import com.gmail.maksimus40a.test.stand.security.domain.User;
import com.gmail.maksimus40a.test.stand.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static com.gmail.maksimus40a.test.stand.security.service.HardcodeUserCredentials.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(@Qualifier("hash-book") BookRepository bookRepository,
                           @Qualifier("hash-user") UserRepository userRepository,
                           EmployeeRepository employeeRepository,
                           PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initBooksData();
        initUserData();
        initEmployeeData();
    }

    private void initBooksData() {
        Arrays.asList(
                new Book("category1", "author1", "title1", BigDecimal.ONE),
                new Book("repetitionCategory", "author2", "title2", BigDecimal.valueOf(2)),
                new Book("repetitionCategory", "author3", "title3", BigDecimal.valueOf(3)),
                new Book("category3", "author4", "title4", BigDecimal.valueOf(4)),
                new Book("category4", "author5", "title5", BigDecimal.valueOf(5)),
                new Book("category5", "repetitiveAuthor", "title6", BigDecimal.valueOf(6)),
                new Book("category6", "repetitiveAuthor", "title7", BigDecimal.valueOf(7)),
                new Book("category7", "repetitiveAuthor", "title8", BigDecimal.valueOf(8))
        ).forEach(bookRepository::addBook);
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
        userRepository.findAll().forEach(System.out::println);
    }

    private void initEmployeeData() {
        Arrays.asList(
                Employee.builder()
                        .firstName("Vernon")
                        .lastName("Harper")
                        .email("egestas.rhoncus.Proin@massaQuisqueporttitor.org")
                        .career("QA Engineer")
                        .skills(Arrays.asList("Manual testing (Web)", "Manual testing (Mobile)"))
                        .build(),

                Employee.builder()
                        .firstName("Murphy")
                        .lastName("Holmes")
                        .email("faucibus.orci.luctus@Duisac.net")
                        .career("Developer")
                        .skills(Arrays.asList("JS", "Ruby"))
                        .build()
        ).forEach(employeeRepository::addEmployee);
    }
}