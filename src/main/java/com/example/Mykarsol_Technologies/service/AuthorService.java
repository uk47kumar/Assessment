package com.example.Mykarsol_Technologies.service;

import com.example.Mykarsol_Technologies.entity.Author;

import java.util.List;

public interface AuthorService {
    Author createAuthor(Author author);
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    Author updateAuthor(Author author, Long id);
    void deleteAuthor(Long id);
}
