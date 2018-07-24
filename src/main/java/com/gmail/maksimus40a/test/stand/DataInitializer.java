package com.gmail.maksimus40a.test.stand;

import com.gmail.maksimus40a.test.stand.book.Book;
import com.gmail.maksimus40a.test.stand.book.repositories.BookRepository;
import com.gmail.maksimus40a.test.stand.security.domain.User;
import com.gmail.maksimus40a.test.stand.security.repository.JpaUserRepository;
import com.gmail.maksimus40a.test.stand.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    //TODO Experimental :: must remove
    private JpaUserRepository jpaUserRepository;

    @Autowired
    public DataInitializer(@Qualifier("db") BookRepository bookRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder, JpaUserRepository jpaUserRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public void run(String... args) {
        initBooksData();
        initUserData();
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
        Arrays.asList(
                User.builder()
                        .username("root")
                        .password(this.passwordEncoder.encode("root"))
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build()
                ,
                User.builder()
                        .username("user")
                        .password(this.passwordEncoder.encode("user"))
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build()
        ).forEach(jpaUserRepository::save);
    }
}