package com.adikchristian.bookcatalog.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adikchristian.bookcatalog.model.entities.Category;
import com.adikchristian.bookcatalog.model.repos.CategoryRepo;

@Service
@Transactional
public class CategoryService {
    
    @Autowired
    private CategoryRepo categoryRepo;

    public Category create(Category category){
        return categoryRepo.save(category);
    }

    public boolean checkAvailableBookById(Long id){
        Optional<Category> category = categoryRepo.findById(id);

        if(!category.isPresent()){
            return false;
        }

        return true;
    }

    public Category findById(Long id){
        boolean category = checkAvailableBookById(id);

        if(!category){
            return null;
        }

        return categoryRepo.findById(id).get();
    }

    public Iterable<Category> findAll(){
        return categoryRepo.findAllByOrderByIdDesc();
    }

    public void removeById(Long id){
        categoryRepo.deleteById(id);
    }
}
