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

import com.adikchristian.bookcatalog.dto.BookData;
import com.adikchristian.bookcatalog.dto.PublisherData;
import com.adikchristian.bookcatalog.dto.ResponseData;
import com.adikchristian.bookcatalog.model.entities.Author;
import com.adikchristian.bookcatalog.model.entities.Book;
import com.adikchristian.bookcatalog.model.entities.Publisher;
import com.adikchristian.bookcatalog.services.BookService;

@RestController
@RequestMapping(value = "/api/books", produces = "application/json")
public class BookController {
    
    @Autowired
    private BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Book>> create(@Valid @RequestBody BookData bookData, Errors errors){
        ResponseData<Book> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for(ObjectError error: errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Book book = modelMapper.map(bookData, Book.class);

        responseData.setStatus(true);
        responseData.setMessage(null);
        responseData.setPayload(bookService.create(book));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Book>>> findAll(){
        ResponseData<Iterable<Book>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(bookService.findAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Book>> findById(@PathVariable("id") Long id){
        ResponseData<Book> responseData = new ResponseData<>();
        Book book = bookService.findById(id);

        if(book==null){
            responseData.setStatus(false);
            responseData.getMessage().add("Data book tidak ditemukan");
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setPayload(book);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Book>> update(@Valid @RequestBody BookData bookData, Errors errors){
        ResponseData<Book> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for(ObjectError error: errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Book bookFind = bookService.findById(bookData.getId());

        if(bookFind==null){
            responseData.setStatus(false);
            responseData.getMessage().add("Data product tidak ditemukan");
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        Book book = modelMapper.map(bookData, Book.class);

        responseData.setStatus(true);
        responseData.setMessage(null);
        responseData.setPayload(bookService.create(book));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Book>> remove(@PathVariable("id") Long id){
        ResponseData<Book> responseData = new ResponseData<>();
        Book book = bookService.findById(id);

        if(book==null){
            responseData.setStatus(false);
            responseData.getMessage().add("Data product tidak ditemukan");
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        bookService.removeById(id);
        responseData.setStatus(true);
        responseData.setPayload(null);
        responseData.getMessage().add("Data Berhasil dihapus");
        return ResponseEntity.ok(responseData);
    }

    @PostMapping(value = "/{id}", consumes={"application/json"})
    public void addAuthor(@RequestBody Author author, @PathVariable("id") Long bookId){
        bookService.addAuthor(author, bookId);
    }

    @PostMapping(value = "/publisher/{id}", consumes={"application/json"})
    public void addPublisher(@RequestBody PublisherData publisherData, @PathVariable("id") Long id){
        Publisher publisher = modelMapper.map(publisherData, Publisher.class);
        bookService.addPublisher(publisher, id);
    }

}
