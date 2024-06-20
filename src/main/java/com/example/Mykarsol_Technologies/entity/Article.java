package com.example.Mykarsol_Technologies.entity;

import jakarta.persistence.*;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "title is required !")
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "description is required !")
    private String description;

    @Column(name = "publish_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    @Column
    private String status;

    @Column
    private String banner;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull(message = "author is required !")
    private Author author;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishDate=" + publishDate +
                ", status='" + status + '\'' +
                ", banner='" + banner + '\'' +
                ", author=" + author +
                '}';
    }
}
