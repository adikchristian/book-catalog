package com.adikchristian.bookcatalog.model.repos;

import org.springframework.data.repository.CrudRepository;

import com.adikchristian.bookcatalog.model.entities.Book;

public interface BookRepos extends CrudRepository<Book, Long>{
    public Iterable<Book> findAllByOrderByIdDesc();
}
