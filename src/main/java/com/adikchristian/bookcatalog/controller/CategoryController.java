package com.adikchristian.bookcatalog.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adikchristian.bookcatalog.dto.CategoryData;
import com.adikchristian.bookcatalog.dto.ResponseData;
import com.adikchristian.bookcatalog.model.entities.Category;
import com.adikchristian.bookcatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/api/categories", produces = "application/json")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody CategoryData categoryData, Errors errors){
        ResponseData<Category> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for(ObjectError error: errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Category category = modelMapper.map(categoryData, Category.class);

        responseData.setStatus(true);
        responseData.setMessage(null);
        responseData.setPayload(categoryService.create(category));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Category>>> findAll(){
        ResponseData<Iterable<Category>> responseData = new ResponseData<>();

        responseData.setStatus(true);
        responseData.setPayload(categoryService.findAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Category>> findById(@PathVariable("id") Long id){
        ResponseData<Category> responseData = new ResponseData<>();
        Category category = categoryService.findById(id);

        if(category==null){
            responseData.setStatus(false);
            responseData.getMessage().add("Data Category tidak ditemukan");
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(category);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Category>> update(@Valid @RequestBody CategoryData categoryData, Errors errors){
        ResponseData<Category> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for(ObjectError error: errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Category categoryFind = categoryService.findById(categoryData.getId());

        if(categoryFind==null){
            responseData.setStatus(false);
            responseData.getMessage().add("Data Category tidak ditemukan");
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }else{
            Category category = modelMapper.map(categoryData, Category.class);

        responseData.setStatus(true);
        responseData.setMessage(null);
        responseData.setPayload(categoryService.create(category));
        return ResponseEntity.ok(responseData);
        }

        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Category>> remove(@PathVariable("id") Long id){
        ResponseData<Category> responseData = new ResponseData<>();
        Category category = categoryService.findById(id);

        if(category==null){
            responseData.setStatus(false);
            responseData.getMessage().add("Data Category tidak ditemukan");
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        categoryService.removeById(id);
        responseData.setStatus(true);
        responseData.setPayload(null);
        responseData.getMessage().add("Data Berhasil dihapus");
        return ResponseEntity.ok(responseData);
    }
}
