package com.lpu.book_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository repo;

    @GetMapping
    public List<BookEntity> getAllBooks(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public BookEntity getBookById(@PathVariable Long id){
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public BookEntity createBook(@RequestBody BookEntity book){
        return repo.save(book);
    }

    @PutMapping("/{id}")
    public BookEntity updateBook(@PathVariable Long id, @RequestBody BookEntity book){
        book.setId(id);
        return repo.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        repo.deleteById(id);
    }
}