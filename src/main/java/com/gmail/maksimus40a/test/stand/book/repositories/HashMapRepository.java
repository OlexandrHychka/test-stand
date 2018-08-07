package com.gmail.maksimus40a.test.stand.book.repositories;

import com.gmail.maksimus40a.test.stand.book.domain.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Qualifier("hash")
public class HashMapRepository implements BookRepository {

    private Map<Integer, Book> bookMap = new ConcurrentHashMap<>();
    private AtomicInteger nextIdGenerator = new AtomicInteger(1);

    @Override
    public int countOfEntities() {
        return bookMap.size();
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookMap.values());
    }

    @Override
    public List<Book> getBooksByCriteria(String fieldName, String value, long limit) {
        return bookMap.values()
                .stream()
                .filter(fieldEqualsPredicate(fieldName, value))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private Predicate<Book> fieldEqualsPredicate(String fieldName, String fieldValue) {
        return book -> {
            try {
                return ReflectionUtils.findField(Book.class, fieldName).get(book).equals(fieldValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error occur in repository layer.", e);
            }
        };
    }

    @Override
    public Optional<Book> getBookById(Integer id) {
        if (id <= 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        return Optional.ofNullable(bookMap.get(id));
    }

    @Override
    public Book addBook(Book book) {
        int id = nextId();
        book.setId(id);
        bookMap.put(id, book);
        return book;
    }

    @Override
    public Optional<Book> editBook(Integer id, Book editedBook) {
        if (id <= 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        Book updated = bookMap.get(id);
        updated.setId(id);
        updated.setAuthor(editedBook.getAuthor());
        updated.setCategory(editedBook.getCategory());
        updated.setTitle(editedBook.getTitle());
        updated.setPrice(editedBook.getPrice());
        bookMap.put(id, updated);
        return Optional.of(updated);
    }

    @Override
    public boolean deleteBookById(Integer id) {
        if (id < 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return false;
        Book removed = bookMap.remove(id);
        return Objects.nonNull(removed);
    }

    private Integer nextId() {
        return nextIdGenerator.getAndIncrement();
    }
}