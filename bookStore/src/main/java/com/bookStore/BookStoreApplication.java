package com.bookStore;

import org.springframework.boot.CommandLineRunner;
import com.bookStore.repository.BookRepository;
import com.bookStore.entity.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}
	@Bean
	CommandLineRunner initDatabase(BookRepository bookRepository) {
		return args -> {
			if (bookRepository.count() == 0) {
				bookRepository.save(new Book(
						"Java Programming",
						"James Gosling",
						499.0,
						"https://images.unsplash.com/photo-1522202176988-66273c2fd55f"
				));
				bookRepository.save(new Book(
						"Spring Boot in Action",
						"Craig Walls",
						599.0,
						"https://images.unsplash.com/photo-1507842217343-583bb7270b66"
				));
				bookRepository.save(new Book(
						"Effective Java",
						"Joshua Bloch",
						699.0,
						"https://images.unsplash.com/photo-1532012197267-da84d127e765"
				));
			}
		};
	}
}
