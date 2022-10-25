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

import com.adikchristian.bookcatalog.dto.AuthorData;
import com.adikchristian.bookcatalog.dto.ResponseData;
import com.adikchristian.bookcatalog.model.entities.Author;
import com.adikchristian.bookcatalog.services.AuthorService;

@RestController
@RequestMapping(value = "/api/author", produces = "application/json")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<ResponseData<Author>> create(@Valid @RequestBody AuthorData authorData, Errors errors){
        ResponseData<Author> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for(ObjectError error: errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Author author = modelMapper.map(authorData, Author.class);

        responseData.setStatus(true);
        responseData.setPayload(authorService.save(author));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Author>>> findAll(){
        ResponseData<Iterable<Author>> responseData = new ResponseData<>();

        responseData.setStatus(true);
        responseData.setPayload(authorService.findAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Author>> findById(@PathVariable("id") Long id){
        ResponseData<Author> responseData = new ResponseData<>();

        Author author = authorService.findById(id);
        if(author==null){
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessage().add("Data Author tidak ditemukan");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(author);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Author>> update(@Valid @RequestBody AuthorData authorData, Errors errors){
        ResponseData<Author> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for(ObjectError error: errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Author authorFind = authorService.findById(authorData.getId());

        if(authorFind==null){
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessage().add("Author Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        Author author = modelMapper.map(authorData, Author.class);

        responseData.setStatus(true);
        responseData.setPayload(authorService.save(author));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Author>> removeById(@PathVariable("id") Long id){
        ResponseData<Author> responseData = new ResponseData<>();

        Author author = authorService.findById(id);
        if(author==null){
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessage().add("Data Author tidak ditemukan");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        authorService.removeById(id);
        responseData.setStatus(true);
        responseData.setPayload(null);
        responseData.getMessage().add("Author Berhasil dihapus");
        return ResponseEntity.ok(responseData);
    }
}
