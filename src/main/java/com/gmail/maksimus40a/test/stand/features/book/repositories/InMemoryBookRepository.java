package com.gmail.maksimus40a.test.stand.features.book.repositories;

import com.gmail.maksimus40a.test.stand.features.base.abstraction.AbstractInMemoryRepository;
import com.gmail.maksimus40a.test.stand.features.book.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Repository
@Qualifier("book-repository")
public class InMemoryBookRepository extends AbstractInMemoryRepository<Book> {

    @Autowired
    public InMemoryBookRepository(@Qualifier("book-search") Function<String, Predicate<Book>> searchFunction) {
        super(searchFunction);
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
}