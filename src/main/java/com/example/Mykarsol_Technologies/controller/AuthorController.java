package com.example.Mykarsol_Technologies.controller;

import com.example.Mykarsol_Technologies.entity.Author;
import com.example.Mykarsol_Technologies.service.AuthorService;
import com.example.Mykarsol_Technologies.service.EmailService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private static final Logger logger = LogManager.getLogger(AuthorController.class);


    private final AuthorService authorService;
    private final EmailService emailService;

    public AuthorController(AuthorService authorService,
                            EmailService emailService) {
        this.authorService = authorService;
        this.emailService = emailService;
    }

    @GetMapping()
    public String indexAuthor(Model model) {
        List<Author> authorList = authorService.getAllAuthors();
        model.addAttribute("authorList", authorList);
        model.addAttribute("title", "Authors");
        logger.info("Accessed index author page");
        return "/author/index";
    }

    @GetMapping("/create")
    public String createAuthorPage(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        model.addAttribute("title", "Create Author");
        logger.info("Accessed create author page");
        return "/author/create";
    }

    @PostMapping("/create")
    public String createAuthor(@Valid @ModelAttribute("author") Author author,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.toString());
            model.addAttribute("author", author);
            logger.error("Error creating author: {}", bindingResult.getAllErrors());
            return "/author/create";
        }
        authorService.createAuthor(author);
        redirectAttributes.addFlashAttribute("successMessage", "Successfully Registered !");
        emailService.sendSimpleMessage(emailService.getNotificationEmail(), "New Author Created", "A new author '" + author.getName() + "' has been created.");
        logger.info("Author created successfully: {}", author.toString());
        return "redirect:/author";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") Long id, Model model) {
        Author authorById = authorService.getAuthorById(id);
        model.addAttribute("author", authorById);
        model.addAttribute("title", "Edit Author");
        logger.info("Accessed edit author page with authorId: {}", id);
        return "/author/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateAuthor(@Valid @ModelAttribute("author")Author author,
                               BindingResult bindingResult,
                               @PathVariable("id")Long id,
                               RedirectAttributes redirectAttributes,
                               Model model){
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.toString());
            model.addAttribute("author", author);
            logger.error("Error updating author: {}", bindingResult.getAllErrors());
            return "/author/edit";
        }
        authorService.updateAuthor(author,id);
        redirectAttributes.addFlashAttribute("successMessage", "Successfully Updated !");
        emailService.sendSimpleMessage(emailService.getNotificationEmail(), "Author Updated", "A author '" + author.toString() + "' has been updated.");
        logger.info("Author updated successfully: {}", author.toString());
        return "redirect:/author";
    }

    @GetMapping("/delete/{id}")
    public String getDeletePage(@PathVariable("id") Long id, Model model) {
        Author authorById = authorService.getAuthorById(id);
        model.addAttribute("author", authorById);
        model.addAttribute("title", "Delete Author");
        logger.info("Accessed delete author page with authorId: {}", id);
        return "author/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        authorService.deleteAuthor(id);
        redirectAttributes.addFlashAttribute("successMessage", "Successfully Deleted !");
        emailService.sendSimpleMessage(emailService.getNotificationEmail(), "Author Deleted", "A author with id: '" + id + "' has been deleted.");
        logger.info("Author deleted successfully with authorId: {}", id);
        return "redirect:/author";
    }
}
