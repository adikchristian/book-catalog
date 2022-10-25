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

import com.adikchristian.bookcatalog.dto.PublisherData;
import com.adikchristian.bookcatalog.dto.ResponseData;
import com.adikchristian.bookcatalog.model.entities.Publisher;
import com.adikchristian.bookcatalog.services.PublisherService;

@RestController
@RequestMapping(value = "/api/publisher", produces = "application/json")
public class PublisherController {
    
    @Autowired
    private PublisherService publisherService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Publisher>> create(@Valid @RequestBody PublisherData publisherData, Errors errors){
        ResponseData<Publisher> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for(ObjectError error: errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Publisher publisher = modelMapper.map(publisherData, Publisher.class);

        responseData.setStatus(true);
        responseData.setPayload(publisherService.create(publisher));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<Publisher>>> findAll(){
        ResponseData<Iterable<Publisher>> responseData = new ResponseData<>();

        responseData.setStatus(true);
        responseData.setPayload(publisherService.findAll());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Publisher>> findById(@PathVariable("id") Long id){
        ResponseData<Publisher> responseData = new ResponseData<>();

        Publisher publisher = publisherService.findById(id);
        if(publisher==null){
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessage().add("Data Publisher tidak ditemukan");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(publisher);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Publisher>> update(@Valid @RequestBody PublisherData publisherData, Errors errors){
        ResponseData<Publisher> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for(ObjectError error: errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Publisher publisherFind = publisherService.findById(publisherData.getId());
        if(publisherFind==null){
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessage().add("Data Publisher tidak ditemukan");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        Publisher publisher = modelMapper.map(publisherData, Publisher.class);

        responseData.setStatus(true);
        responseData.setPayload(publisherService.create(publisher));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Publisher>> delete(@PathVariable("id") Long id){
        ResponseData<Publisher> responseData = new ResponseData<>();

        Publisher publisher = publisherService.findById(id);
        if(publisher==null){
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessage().add("Data Publisher tidak ditemukan");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

        publisherService.removeById(id);
        responseData.setStatus(true);
        responseData.setPayload(null);
        responseData.getMessage().add("Publisher Berhasil dihapus");
        return ResponseEntity.ok(responseData);
    }
}
