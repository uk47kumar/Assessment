package com.example.Mykarsol_Technologies.service.impl;

import com.example.Mykarsol_Technologies.entity.Article;
import com.example.Mykarsol_Technologies.entity.Author;
import com.example.Mykarsol_Technologies.exception.ResourceNotFoundException;
import com.example.Mykarsol_Technologies.repository.ArticleRepository;
import com.example.Mykarsol_Technologies.repository.AuthorRepository;
import com.example.Mykarsol_Technologies.service.ArticleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {


    private static final Logger logger = LogManager.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              AuthorRepository authorRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Article createArticle(Long authorId, Article article) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + authorId));
        article.setAuthor(author);
        Article savedArticle = articleRepository.save(article);
        logger.info("calling createArticle method of ArticleServiceImpl class");
        return savedArticle;
    }

    @Override
    public List<Article> getAllArticle() {
        List<Article> articleList = articleRepository.findAll();
        logger.info("calling getAllArticle method of ArticleServiceImpl class");
        return articleList;
    }

    @Override
    public Article getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));
        logger.info("calling getArticleById method of ArticleServiceImpl class");
        return article;
    }

    @Override
    public Article updateArticle(Long id, Article article) {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));
        existingArticle.setTitle(article.getTitle());
        existingArticle.setPublishDate(article.getPublishDate());
        existingArticle.setDescription(article.getDescription());
        existingArticle.setBanner(article.getBanner());
        Article updatedArticle = articleRepository.save(existingArticle);
        logger.info("calling updateArticle method of ArticleServiceImpl class");
        return updatedArticle;
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));
        articleRepository.delete(article);
        logger.info("calling deleteArticle method of ArticleServiceImpl class");
    }
}
