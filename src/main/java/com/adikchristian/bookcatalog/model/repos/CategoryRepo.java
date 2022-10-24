package com.adikchristian.bookcatalog.model.repos;

import org.springframework.data.repository.CrudRepository;

import com.adikchristian.bookcatalog.model.entities.Category;

public interface CategoryRepo extends CrudRepository<Category, Long>{
    public Iterable<Category> findAllByOrderByIdDesc();
}
