package com.gmail.maksimus40a.test.stand.entity.book.controllers;

import com.gmail.maksimus40a.test.stand.entity.base.BaseService;
import com.gmail.maksimus40a.test.stand.entity.book.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookstore/book")
public class SecuredBookController {

    private BaseService<Book> bookService;

    @Autowired
    public SecuredBookController(BaseService<Book> bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    public List<Book> hello() {
        return bookService.getAllEntities();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookService.addEntity(book);
    }
}