package com.gmail.maksimus40a.test.stand.security.repository;

import com.gmail.maksimus40a.test.stand.security.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Qualifier("user-repository")
public class InMemoryUserRepository implements UserRepository {

    private Map<Integer, User> users = new ConcurrentHashMap<>();
    private AtomicInteger nextIdGenerator = new AtomicInteger(1);

    @Override
    public Optional<User> findByUserName(String userName) {
        return users.values()
                .stream()
                .filter(user -> user.getUsername().equals(userName))
                .findFirst();
    }

    @Override
    public User addUser(User user) {
        checkNotUniqueUsername(user);
        Integer id = nextId();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public void addUsers(Collection<User> users) {
        users.forEach(this::addUser);
    }

    private void checkNotUniqueUsername(User user) {
        if (findByUserName(user.getUsername()).isPresent()) {
            throw new SuchUserIsPresentException("User with a such username is present, please choose a new user name.");
        }
    }

    private Integer nextId() {
        return nextIdGenerator.getAndIncrement();
    }
}
