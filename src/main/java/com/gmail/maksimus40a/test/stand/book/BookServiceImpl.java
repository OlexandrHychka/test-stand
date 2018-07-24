package com.gmail.maksimus40a.test.stand.book;

import com.gmail.maksimus40a.test.stand.book.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(@Qualifier("db") BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    public List<Book> getBooksByCriteria(Map<String, String> params) {
        Integer limit = params.containsKey("limit") ? Integer.valueOf(params.get("limit")) : Integer.MAX_VALUE;
        Field[] fields = Book.class.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        String key = "";
        for (Field field : fields) {
            if (params.containsKey(field.getName())) {
                key = field.getName();
            }
        }
        String value = params.get(key);
        return bookRepository.getBooksByCriteria(key, value, limit);
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