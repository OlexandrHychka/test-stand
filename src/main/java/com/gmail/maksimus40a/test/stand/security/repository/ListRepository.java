package com.gmail.maksimus40a.test.stand.security.repository;

import com.gmail.maksimus40a.test.stand.security.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
@Qualifier("list")
public class ListRepository implements UserRepository {

    private List<User> users = new CopyOnWriteArrayList<>();

    @Override
    public Optional<User> findByUserName(String userName) {
        return users.stream()
                .filter(user -> user.getUsername().equals(userName))
                .findFirst();
    }

    @Override
    public User addUser(User user) {
        checkNotUniqueUsername(user);
        users.add(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public void addUsers(Collection<User> anotherUsers) {
        for (User user : anotherUsers) {
            checkNotUniqueUsername(user);
        }
        users.addAll(anotherUsers);
    }

    private void checkNotUniqueUsername(User user) {
        if (findByUserName(user.getUsername()).isPresent()) {
            throw new SuchUserIsPresentException("User with a such username is present, please choose a new user name.");
        }
    }
}
