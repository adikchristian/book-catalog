package com.adikchristian.bookcatalog.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adikchristian.bookcatalog.model.entities.Author;
import com.adikchristian.bookcatalog.model.repos.AuthorRepos;

@Service
@Transactional
public class AuthorService {
    
    @Autowired
    private AuthorRepos authorRepos;

    public Author save(Author author){
        return authorRepos.save(author);
    }

    public boolean checkAvailableAuthorById(Long id){
        Optional<Author> author = authorRepos.findById(id);

        if(!author.isPresent()){
            return false;
        }

        return true;
    }

    public Author findById(Long id){
        boolean author = checkAvailableAuthorById(id);

        if(!author){
            return null;
        }

        return authorRepos.findById(id).get();
    }

    public Iterable<Author> findAll(){
        return authorRepos.findAllByOrderByIdDesc();
    }

    public void removeById(Long id){
        authorRepos.deleteById(id);
    }
}
