package com.gmail.maksimus40a.test.stand.features.book.domain;

import com.gmail.maksimus40a.test.stand.features.base.IdEntity;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
public class Book extends IdEntity {

    private String category;
    private String author;
    private String title;
    private BigDecimal price;

    public Book(Integer id , String category, String author, String title, BigDecimal price) {
        super(id);
        this.category = category;
        this.author = author;
        this.title = title;
        this.price = price;
    }
}