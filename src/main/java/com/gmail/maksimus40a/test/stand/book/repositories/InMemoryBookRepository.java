package com.gmail.maksimus40a.test.stand.book.repositories;

import com.gmail.maksimus40a.test.stand.book.domain.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Qualifier("hash-book")
public class InMemoryBookRepository implements BookRepository {

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
    public List<Book> getBooksByCriteria(String criteria, long limit) {
        return bookMap.values()
                .stream()
                .filter(fieldEqualsPredicate(criteria))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private Predicate<Book> fieldEqualsPredicate(String criteria) {
        return employee -> {
            long price;
            try {
                price = Long.parseLong(criteria);
            } catch (NumberFormatException e) {
                return employee.getAuthor().equals(criteria) ||
                        employee.getCategory().equals(criteria) ||
                        employee.getTitle().equals(criteria);
            }
            return employee.getPrice().compareTo(BigDecimal.valueOf(price)) == 0;
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