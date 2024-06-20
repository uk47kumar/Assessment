package com.example.Mykarsol_Technologies.repository;

import com.example.Mykarsol_Technologies.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
}
