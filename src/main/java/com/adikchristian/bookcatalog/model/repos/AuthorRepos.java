package com.adikchristian.bookcatalog.model.repos;

import org.springframework.data.repository.CrudRepository;

import com.adikchristian.bookcatalog.model.entities.Author;

public interface AuthorRepos extends CrudRepository<Author, Long> {
    public Iterable<Author> findAllByOrderByIdDesc();
}
