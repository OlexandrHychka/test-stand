package com.gmail.maksimus40a.test.stand.book.controllers;

import com.gmail.maksimus40a.test.stand.book.domain.Book;
import com.gmail.maksimus40a.test.stand.book.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookstore/book")
public class SecuredBookController {

    private BookService bookService;

    @Autowired
    public SecuredBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    public List<Book> hello() {
        return bookService.getAllBooks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }
}