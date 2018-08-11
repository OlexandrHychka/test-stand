package com.gmail.maksimus40a.test.stand.book.repositories;

import com.gmail.maksimus40a.test.stand.bases.SearchRepository;
import com.gmail.maksimus40a.test.stand.book.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Repository
@Qualifier("book-repository")
public class InMemoryBookRepository extends SearchRepository<Book> {

    @Autowired
    public InMemoryBookRepository(@Qualifier("book-search") Function<String, Predicate<Book>> searchFunction) {
        super(searchFunction);
    }

    @Override
    public int countOfEntities() {
        return entityMap.size();
    }

    @Override
    public List<Book> getAllEntities() {
        return new ArrayList<>(entityMap.values());
    }

    @Override
    public Optional<Book> getEntityById(Integer id) {
        if (id <= 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        return Optional.ofNullable(entityMap.get(id));
    }

    @Override
    public Book addEntity(Book book) {
        int id = nextId();
        book.setId(id);
        entityMap.put(id, book);
        return book;
    }

    @Override
    public Optional<Book> editEntity(Integer id, Book editedBook) {
        if (id <= 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        Book updated = entityMap.get(id);
        updated.setId(id);
        updated.setAuthor(editedBook.getAuthor());
        updated.setCategory(editedBook.getCategory());
        updated.setTitle(editedBook.getTitle());
        updated.setPrice(editedBook.getPrice());
        entityMap.put(id, updated);
        return Optional.of(updated);
    }

    @Override
    public Optional<Book> deleteEntityById(Integer id) {
        if (id < 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) Optional.empty();
        Book removed = entityMap.remove(id);
        reduceId();
        return Optional.ofNullable(removed);
    }
}