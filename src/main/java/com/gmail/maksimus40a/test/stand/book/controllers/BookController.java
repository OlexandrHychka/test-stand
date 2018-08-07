package com.gmail.maksimus40a.test.stand.book.controllers;

import com.gmail.maksimus40a.test.stand.book.domain.Book;
import com.gmail.maksimus40a.test.stand.book.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/library/book")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks(@RequestParam(required = false) Map<String, String> parametersOfQuery) {
        return (parametersOfQuery.isEmpty()) ? bookService.getAllBooks() : bookService.getBooksByCriteria(parametersOfQuery);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id).orElseThrow(NoSuchElementException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editBookById(@PathVariable Integer id, @RequestBody Book book) {
        bookService.editBook(id, book).orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }
}