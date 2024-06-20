package com.example.Mykarsol_Technologies.service;

import com.example.Mykarsol_Technologies.entity.Article;

import java.util.List;

public interface ArticleService {

    Article createArticle(Long authorId, Article article);
    List<Article> getAllArticle();
    Article getArticleById(Long id);
    Article updateArticle(Long id, Article article);
    void deleteArticle(Long id);

}
