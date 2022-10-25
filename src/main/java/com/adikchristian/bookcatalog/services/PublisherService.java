package com.adikchristian.bookcatalog.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adikchristian.bookcatalog.model.entities.Publisher;
import com.adikchristian.bookcatalog.model.repos.PublisherRepos;

@Service
@Transactional
public class PublisherService {
    
    @Autowired
    private PublisherRepos publisherRepos;

    public Publisher create(Publisher publisher){
        return publisherRepos.save(publisher);
    }

    public Iterable<Publisher> findAll(){
        return publisherRepos.findAllByOrderByIdDesc();
    }

    public boolean checkAvailablePublisherById(Long id){
        Optional<Publisher> publisher = publisherRepos.findById(id);

        if(!publisher.isPresent()){
            return false;
        }

        return true;
    }

    public Publisher findById(Long id){
        boolean publisher = checkAvailablePublisherById(id);

        if(!publisher){
            return null;
        }

        return publisherRepos.findById(id).get();
    }

    public void removeById(Long id){
        publisherRepos.deleteById(id);
    }
}
