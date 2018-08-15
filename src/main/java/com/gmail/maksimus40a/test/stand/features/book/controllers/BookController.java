package com.gmail.maksimus40a.test.stand.features.book.controllers;

import com.gmail.maksimus40a.test.stand.features.base.interfaces.BaseService;
import com.gmail.maksimus40a.test.stand.features.book.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/library/book")
public class BookController {

    private BaseService<Book> bookService;

    @Autowired
    public BookController(@Qualifier("book-service") BaseService<Book> bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) Map<String, String> parametersOfQuery) {
        return (parametersOfQuery.isEmpty()) ?
                new ResponseEntity<>(bookService.getAllEntities(), HttpStatus.FOUND) :
                new ResponseEntity<>(bookService.getEntitiesByCriteria(parametersOfQuery), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        Book book = bookService
                .getEntityById(id)
                .orElseThrow(() -> new NoSuchElementException("There isn't resource with such id " + id));
        return new ResponseEntity<>(book, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book, UriComponentsBuilder ucb) {
        Book createdBook = bookService.addEntity(book);
        return ResponseEntity
                .created(ucb.path("/api/library/book/{id}").buildAndExpand(createdBook.getId()).toUri())
                .body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> editBookById(@PathVariable Integer id, @RequestBody Book book) {
        Book editedBook = bookService
                .editEntityById(id, book)
                .orElseThrow(() -> new NoSuchElementException("There isn't resource with such id " + id));
        return ResponseEntity.ok(editedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Integer id) {
        bookService.deleteEntityById(id);
        return ResponseEntity.ok().build();
    }
}