package com.gmail.maksimus40a.test.stand.book.repositories;

import com.gmail.maksimus40a.test.stand.book.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    int countOfEntities();

    List<Book> getAllBooks();

    Optional<Book> getBookById(Integer id);

    List<Book> getBooksByCriteria(String key, String value, long limit);

    Book addBook(Book book);

    Optional<Book> editBook(Integer id, Book editedBook);

    boolean deleteBookById(Integer id);
}