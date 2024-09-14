package com.bloger.web.books_market.models;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class BooksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String descriptions;
    private String years;
    private String author;
    private String category;
    private Integer price;
    private String rentalPeriod;

    public BooksEntity(String title, String descriptions, String years, String author, String category, Integer price) {
        this.title = title;
        this.descriptions = descriptions;
        this.years = years;
        this.author = author;
        this.category = category;
        this.price = price;
    }
}