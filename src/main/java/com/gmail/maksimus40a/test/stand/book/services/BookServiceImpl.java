package com.gmail.maksimus40a.test.stand.book.services;

import com.gmail.maksimus40a.test.stand.bases.BaseService;
import com.gmail.maksimus40a.test.stand.bases.SearchRepository;
import com.gmail.maksimus40a.test.stand.book.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("book-service")
public class BookServiceImpl implements BaseService<Book> {

    private final String LIMIT_FIELD_NOTATION = "limit";

    private SearchRepository<Book> bookRepository;
    private List<String> bookFieldsNames = new ArrayList<>(Arrays.asList("category", "author", "title", "price"));

    @Autowired
    public BookServiceImpl(@Qualifier("book-repository") SearchRepository<Book> bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllEntities() {
        return bookRepository.getAllEntities();
    }

    @Override
    public List<Book> getEntitiesByCriteria(Map<String, String> requestParams) {
        return bookRepository.getEntitiesByCriteria(getSearchCriteria(requestParams), getLimit(requestParams));
    }

    private String getSearchCriteria(Map<String, String> requestParams) {
        return requestParams.get(bookFieldsNames.stream()
                .filter(fieldName -> requestParams.keySet()
                        .stream()
                        .anyMatch(field -> field.equals(fieldName)))
                .findFirst()
                .orElseThrow(() -> new NoSuchSearchCriteriaException("Not such search criteria.")));
    }

    private int getLimit(Map<String, String> requestParams) {
        return Integer.parseInt(requestParams.getOrDefault(LIMIT_FIELD_NOTATION, String.valueOf(Integer.MAX_VALUE)));
    }

    @Override
    public Optional<Book> getEntityById(Integer id) {
        return bookRepository.getEntityById(id);
    }

    @Override
    public Book addEntity(Book book) {
        return bookRepository.addEntity(book);
    }

    @Override
    public Optional<Book> editEntityById(Integer id, Book editedBook) {
        return bookRepository.editEntity(id, editedBook);
    }

    @Override
    public Optional<Book> deleteEntityById(Integer id) {
        return bookRepository.deleteEntityById(id);
    }
}