package com.example.Mykarsol_Technologies.service.impl;

import com.example.Mykarsol_Technologies.entity.Author;
import com.example.Mykarsol_Technologies.exception.ResourceNotFoundException;
import com.example.Mykarsol_Technologies.repository.AuthorRepository;
import com.example.Mykarsol_Technologies.service.AuthorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger logger = LogManager.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author createAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        logger.info("calling createAuthor method of AuthorServiceImpl class");
        return savedAuthor;
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authorList = authorRepository.findAll();
        logger.info("calling getAllAuthors method of AuthorServiceImpl class");
        return authorList;
    }

    @Override
    public Author getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        logger.info("calling getAuthorById method of AuthorServiceImpl class");
        return author;
    }

    @Override
    public Author updateAuthor(Author author, Long id) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        existingAuthor.setName(author.getName());
        Author updatedAuthor = authorRepository.save(existingAuthor);
        logger.info("calling updateAuthor method of AuthorServiceImpl class");
        return updatedAuthor;
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
        logger.info("calling deleteAuthor method of AuthorServiceImpl class");
    }
}
