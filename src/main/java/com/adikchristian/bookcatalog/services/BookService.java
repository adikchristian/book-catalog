package com.adikchristian.bookcatalog.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adikchristian.bookcatalog.model.entities.Author;
import com.adikchristian.bookcatalog.model.entities.Book;
import com.adikchristian.bookcatalog.model.repos.BookRepos;

@Service
@Transactional
public class BookService {
    
    @Autowired
    private BookRepos bookRepos;

    public Book create(Book book){
        return bookRepos.save(book);
    }

    public boolean checkAvailableBookById(Long id){
        Optional<Book> book = bookRepos.findById(id);

        if(!book.isPresent()){
            return false;
        }

        return true;
    }

    public Book findById(Long id){
        boolean book = checkAvailableBookById(id);

        if(!book){
            return null;
        }

        return bookRepos.findById(id).get();
    }

    public Iterable<Book> findAll(){
        return bookRepos.findAllByOrderByIdDesc();
    }

    public void removeById(Long id){
        bookRepos.deleteById(id);
    }

    public void addAuthor(Author author, Long bookId){
        Book book = findById(bookId);

        if(book==null){
            throw new RuntimeException("Product with ID"+bookId+"No Found");
        }

        book.getAuthor().add(author);
        create(book);
    }

}
