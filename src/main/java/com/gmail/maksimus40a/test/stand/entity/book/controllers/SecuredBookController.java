package com.gmail.maksimus40a.test.stand.entity.book.controllers;

import com.gmail.maksimus40a.test.stand.entity.base.BaseService;
import com.gmail.maksimus40a.test.stand.entity.book.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<Book>> hello() {
        return new ResponseEntity<>(bookService.getAllEntities(), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addEntity(book));
    }
}