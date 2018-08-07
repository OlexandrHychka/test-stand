package com.gmail.maksimus40a.test.stand.book.services;

import com.gmail.maksimus40a.test.stand.book.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {

    List<Book> getAllBooks();

    Optional<Book> getBookById(Integer id);

    List<Book> getBooksByCriteria(Map<String, String> params);

    Book addBook(Book book);

    Optional<Book> editBook(Integer id, Book editedBook);

    boolean deleteBookById(Integer id);
}