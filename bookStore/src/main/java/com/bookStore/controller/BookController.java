
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
import java.util.List;

@Controller
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	// ✅ Homepage
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/explore")
	public String exploreBooks(Model model) {
		List<Book> books = bookRepository.findAll();
		model.addAttribute("books", books);
		return "explore";
	}

	// ✅ View all available books
	@GetMapping("/books")
	public String viewBooks(Model model) {
		List<Book> books = bookRepository.findAll();
		model.addAttribute("books", books);
		return "books";
	}

	// ✅ Handle Buy/Read button click (POST)
	@PostMapping("/buy/{bookId}")
	public String buyBook(@PathVariable int bookId, Principal principal) {
		// Get the current logged-in user's email
		if(principal==null){
			return "redirect:/login";
		}
		String userEmail = principal.getName();
		User user = userRepository.findByEmail(userEmail);
//		if (user == null) {
//			return "redirect:/login";
//		}

		// Get book by ID
		Book book = bookRepository.findById(bookId).orElse(null);
		if (book != null && user!=null) {
			// Add to user's purchased books set
			user.getPurchasedBooks().add(book);
			userRepository.save(user);
		}

		// Redirect to MyBooks page
		return "redirect:/myBooks";
	}
	@GetMapping("/books/search")
	public String searchBooks(@RequestParam("query") String query, Model model) {
		List<Book> searchResults = bookRepository.findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
		model.addAttribute("books", searchResults);
		model.addAttribute("query", query);
		return "books";
	}
	@GetMapping("/search-suggestions")
	@ResponseBody
	public List<Book> searchSuggestions(@RequestParam String query) {
		return bookRepository.findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
	}


	// ✅ My Books Page
	@GetMapping("/myBooks")
	public String myBooks(Model model, Principal principal) {
		String userEmail = principal.getName();
		User user = userRepository.findByEmail(userEmail);
		model.addAttribute("purchasedBooks", user.getPurchasedBooks());
		return "myBooks";
	}

	// ✅ Open PDF (read the book)


}
