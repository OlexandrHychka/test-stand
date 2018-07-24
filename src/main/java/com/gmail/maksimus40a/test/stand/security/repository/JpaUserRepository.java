package com.gmail.maksimus40a.test.stand.security.repository;

import com.gmail.maksimus40a.test.stand.security.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
