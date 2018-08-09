package com.gmail.maksimus40a.test.stand.book.services;

import com.gmail.maksimus40a.test.stand.book.domain.Book;
import com.gmail.maksimus40a.test.stand.book.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

@Service
public class BookServiceImpl implements BookService {

    private final String LIMIT_FIELD_NOTATION = "limit";

    private BookRepository bookRepository;
    private List<String> bookFieldsNames = new ArrayList<>();

    @Autowired
    public BookServiceImpl(@Qualifier("hash-book") BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void init() {
        Stream.of(Book.class.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .map(Field::getName)
                .forEach(bookFieldsNames::add);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    public List<Book> getBooksByCriteria(Map<String, String> requestParams) {
        int limit = (requestParams.containsKey(LIMIT_FIELD_NOTATION)) ?
                Integer.parseInt(requestParams.get(LIMIT_FIELD_NOTATION)) :
                Integer.MAX_VALUE;
        requestParams.remove(LIMIT_FIELD_NOTATION);
        if (requestParams.size() > 1) throw new UnsupportedOperationException("Not support multiply search.");
        String fieldName = null;
        for (String fn : bookFieldsNames) {
            if (requestParams.containsKey(fn)) {
                fieldName = fn;
            }
        }
        if (Objects.isNull(fieldName)) throw new NoSuchSearchCriteriaException("Not such search criteria.");
        String fieldValue = requestParams.get(fieldName);
        return bookRepository.getBooksByCriteria(fieldName, fieldValue, limit);
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