package com.adikchristian.bookcatalog.model.repos;

import org.springframework.data.repository.CrudRepository;

import com.adikchristian.bookcatalog.model.entities.Publisher;

public interface PublisherRepos extends CrudRepository<Publisher, Long> {
    public Iterable<Publisher> findAllByOrderByIdDesc();
}
