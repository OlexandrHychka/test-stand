package com.gmail.maksimus40a.test.stand.entity.book.services;

import com.gmail.maksimus40a.test.stand.entity.base.AbstractService;
import com.gmail.maksimus40a.test.stand.entity.base.BaseRepository;
import com.gmail.maksimus40a.test.stand.entity.book.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("book-service")
public class BookServiceImpl extends AbstractService<Book> {

    @Autowired
    public BookServiceImpl(@Qualifier("book-repository") BaseRepository<Book> bookRepository,
                           @Qualifier("book-fields") List<String> entityFields) {
        super(bookRepository, entityFields);
    }
}