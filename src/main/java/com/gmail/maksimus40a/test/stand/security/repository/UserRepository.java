package com.gmail.maksimus40a.test.stand.security.repository;

import com.gmail.maksimus40a.test.stand.security.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUserName(String userName);

    User addUser(User user);
}