package com.bloger.web.books_market.repository;

import com.bloger.web.books_market.models.BooksEntity;
import org.springframework.data.repository.CrudRepository;

public interface BooksEntityRepository extends CrudRepository<BooksEntity, Long> {
}