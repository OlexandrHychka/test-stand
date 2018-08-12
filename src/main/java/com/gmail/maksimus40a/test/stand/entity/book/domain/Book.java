package com.gmail.maksimus40a.test.stand.entity.book.domain;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Book {

    private Integer id;
    private String category;
    private String author;
    private String title;
    private BigDecimal price;

    public Book(String category, String author, String title, BigDecimal price) {
        this.category = category;
        this.author = author;
        this.title = title;
        this.price = price;
    }
}