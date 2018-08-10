package com.gmail.maksimus40a.test.stand.book.services;

import com.gmail.maksimus40a.test.stand.book.domain.Book;
import com.gmail.maksimus40a.test.stand.book.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final String LIMIT_FIELD_NOTATION = "limit";

    private BookRepository bookRepository;
    private List<String> bookFieldsNames = new ArrayList<>(Arrays.asList("category", "author", "title", "price"));

    @Autowired
    public BookServiceImpl(@Qualifier("hash-book") BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    public List<Book> getBooksByCriteria(Map<String, String> requestParams) {
        return bookRepository.getBooksByCriteria(getValue(requestParams), getLimit(requestParams));
    }

    private String getValue(Map<String, String> requestParams) {
        return requestParams.get(bookFieldsNames.stream()
                .filter(fieldName -> requestParams.keySet()
                        .stream()
                        .anyMatch(field -> field.equals(fieldName)))
                .findFirst()
                .orElseThrow(() -> new NoSuchSearchCriteriaException("Not such search criteria.")));
    }

    private int getLimit(Map<String, String> requestParams) {
        int limit;
        if (requestParams.containsKey(LIMIT_FIELD_NOTATION)) {
            limit = Integer.parseInt(requestParams.get(LIMIT_FIELD_NOTATION));
            requestParams.remove(LIMIT_FIELD_NOTATION);
        } else {
            limit = Integer.MAX_VALUE;
        }
        return limit;
    }

    @Override
    public Optional<Book> getBookById(Integer id) {
        return bookRepository.getBookById(id);
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.addBook(book);
    }

    @Override
    public Optional<Book> editBook(Integer id, Book editedBook) {
        return bookRepository.editBook(id, editedBook);
    }

    @Override
    public boolean deleteBookById(Integer id) {
        return bookRepository.deleteBookById(id);
    }
}