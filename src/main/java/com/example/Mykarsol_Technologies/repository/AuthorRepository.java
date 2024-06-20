package com.example.Mykarsol_Technologies.repository;

import com.example.Mykarsol_Technologies.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
}
