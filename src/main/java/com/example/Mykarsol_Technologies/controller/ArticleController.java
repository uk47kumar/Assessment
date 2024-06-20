package com.example.Mykarsol_Technologies.controller;

import com.example.Mykarsol_Technologies.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.Mykarsol_Technologies.entity.Article;
import com.example.Mykarsol_Technologies.entity.Author;
import com.example.Mykarsol_Technologies.exception.ResourceNotFoundException;
import com.example.Mykarsol_Technologies.service.ArticleService;
import com.example.Mykarsol_Technologies.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/author")
public class ArticleController {

    private static final Logger logger = LogManager.getLogger(ArticleController.class);


    private final ArticleService articleService;
    private final AuthorService authorService;
    private final EmailService emailService;

    @Autowired
    public ArticleController(ArticleService articleService,
                             AuthorService authorService,
                             EmailService emailService) {
        this.articleService = articleService;
        this.authorService = authorService;
        this.emailService = emailService;
    }

    @GetMapping("/article")
    public String indexArticle(Model model) {
        List<Article> articleList = articleService.getAllArticle();
        model.addAttribute("articleList", articleList);
        model.addAttribute("title", "Articles");
        logger.info("Accessed index article page");
        return "/article/index";
    }

    @GetMapping("/article/create")
    public String createArticlePage(Model model) {
        Article article = new Article();
        List<Author> authorList = authorService.getAllAuthors();
        model.addAttribute("article", article);
        model.addAttribute("authorList", authorList);
        model.addAttribute("title", "Create Article");
        logger.info("Accessed create article page");
        return "/article/create";
    }

    @PostMapping("/article/create")
    public String createArticle(@Valid @ModelAttribute("article") Article article,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                System.out.println(bindingResult.toString());
                model.addAttribute("article", article);
                model.addAttribute("authorList", authorService.getAllAuthors());
                model.addAttribute("title", "Create Article");
                logger.error("Error creating article: {}", bindingResult.getAllErrors());
                return "/article/create";
            }
            Long authorId = article.getAuthor().getId();
            articleService.createArticle(authorId, article);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully Added !");
            emailService.sendSimpleMessage(emailService.getNotificationEmail(), "New Article Created", "A new article titled '" + article.getTitle() + "' has been created.");
            logger.info("Article created successfully: {}", article.toString());
            return "redirect:/author/article";
        } catch (ResourceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("article", article);
            model.addAttribute("authorList", authorService.getAllAuthors());
            model.addAttribute("title", "Create Article");
            logger.error("Resource not found: {}", e.getMessage());
            return "/article/create";
        }
    }

    @GetMapping("/article/edit/{id}")
    public String getEditArticlePage(@PathVariable("id") Long id,
                                     Model model) {
        Article articleById = articleService.getArticleById(id);
        model.addAttribute("article", articleById);
        model.addAttribute("title", "Edit Article");
        logger.info("Accessed edit article page with articleId: {}", id);
        return "/article/edit";
    }

    @PostMapping("/article/edit/{id}")
    public String updateAuthor(@Valid @ModelAttribute("article") Article article,
                               BindingResult bindingResult,
                               @PathVariable("id") Long id,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.toString());
            model.addAttribute("article", article);
            logger.error("Error updating article: {}", bindingResult.getAllErrors());
            return "/article/edit";
        }
        articleService.updateArticle(id, article);
        redirectAttributes.addFlashAttribute("successMessage", "Successfully Updated !");
        emailService.sendSimpleMessage(emailService.getNotificationEmail(), "Article Updated", "A article titled '" + article.getTitle() + "' has been updated.");
        logger.info("Article updated successfully: {}", article.toString());
        return "redirect:/author/article";
    }

    @GetMapping("/article/delete/{id}")
    public String getDeleteArticlePage(@PathVariable("id") Long id, Model model) {
        Article articleById = articleService.getArticleById(id);
        model.addAttribute("article", articleById);
        model.addAttribute("title", "Delete Article");
        logger.info("Accessed delete article page with articleId: {}", id);
        return "/article/delete";
    }

    @PostMapping("/article/delete/{id}")
    public String deleteArticlePage(@PathVariable("id") Long id,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        articleService.deleteArticle(id);
        redirectAttributes.addFlashAttribute("successMessage", "Successfully Deleted !");
        emailService.sendSimpleMessage(emailService.getNotificationEmail(), "Article Deleted", "A article with id: '" + id + "' has been deleted.");
        logger.info("Article deleted successfully with articleId: {}", id);
        return "redirect:/author/article";
    }

}
