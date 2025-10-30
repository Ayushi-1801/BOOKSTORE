package com.bookStore.controller;

import com.bookStore.entity.Book;
import com.bookStore.entity.User;
import com.bookStore.repository.BookRepository;
import com.bookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserBookController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public String showUserBooks(Model model, Principal principal) {
        // Get logged-in user
        User user = userRepository.findByEmail(principal.getName());

        // All books
        model.addAttribute("allBooks", bookRepository.findAll());

        // User’s purchased books
        model.addAttribute("purchasedBooks", user.getPurchasedBooks());

        return "user_books";
    }

    @PostMapping("/buy/{bookId}")
    public String buyBook(@PathVariable Integer bookId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        Book book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            user.getPurchasedBooks().add(book);
            userRepository.save(user);
        }

        return "redirect:/user/books";
    }
}
