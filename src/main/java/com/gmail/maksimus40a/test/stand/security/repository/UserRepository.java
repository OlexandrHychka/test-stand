package com.gmail.maksimus40a.test.stand.security.repository;

import com.gmail.maksimus40a.test.stand.security.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUserName(String userName);

    User addUser(User user);

    List<User> findAll();

    void deleteAll();

    void addUsers(Collection<User> users);
}