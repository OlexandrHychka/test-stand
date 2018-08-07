package com.gmail.maksimus40a.test.stand.book.services;

import com.gmail.maksimus40a.test.stand.book.domain.Book;
import com.gmail.maksimus40a.test.stand.book.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final String LIMIT_FIELD_NOTATION = "limit";
    private final String NO_SUCH_CRITERIA_MARKER = "???";

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(@Qualifier("db") BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    public List<Book> getBooksByCriteria(Map<String, String> params) {
        Integer limit = params.containsKey(LIMIT_FIELD_NOTATION) ?
                Integer.valueOf(params.get(LIMIT_FIELD_NOTATION)) :
                Integer.MAX_VALUE;
        Field[] fields = Book.class.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        String criteriaName = null;
        for (Field field : fields) {
            if (params.containsKey(field.getName())) {
                criteriaName = field.getName();
            }
        }
        String criteriaValue = params.getOrDefault(criteriaName, NO_SUCH_CRITERIA_MARKER);
        if (criteriaValue.equals(NO_SUCH_CRITERIA_MARKER)) {
            String message = "No books with this search request parameter = " + criteriaValue;
            throw new NoSuchSearchCriteriaException(message);
        }
        return bookRepository.getBooksByCriteria(criteriaName, criteriaValue, limit);
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