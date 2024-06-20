package com.example.Mykarsol_Technologies.exception;

import com.example.Mykarsol_Technologies.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    private final EmailService emailService;

    @Autowired
    public GlobalExceptionHandler(EmailService emailService){
        this.emailService = emailService;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFound(NoHandlerFoundException ex, Model model) {
        model.addAttribute("error", "Page not found");
        emailService.sendSimpleMessage(emailService.getNotificationEmail(), "404 Error", "No handler found for request: " + ex.getRequestURL());
        logger.error("No handler found for request: {}", ex.getRequestURL(), ex);
        return new ModelAndView("error/404", model.asMap());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        emailService.sendSimpleMessage(emailService.getNotificationEmail(), "Resource Not Found", "Resource not found: " + ex.getMessage());
        logger.error("Resource Not found for request: {}", ex.getMessage(), ex);
        return new ModelAndView("error/resourceNotFound", model.asMap());
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ModelAndView handleGenericException(Exception ex, Model model) {
//        logger.error("An unexpected error occurred", ex);
//        model.addAttribute("error", "An unexpected error occurred. Please try again later.");
//        return new ModelAndView("error/500", model.asMap());
//    }

}
