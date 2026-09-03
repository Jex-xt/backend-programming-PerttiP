package backend.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository repository) {
	return (args) -> {
	  // Your code...add some demo data to db
        Book book1 = new Book("Suuri koodaus kirja", "Kalle Koodari", "978123456789-7", 1999, 20.9);
        Book book2 = new Book("Pieni koodaus kirja", "Niko Nörtti", "97898765432-10", 2004, 15.90);
        Book book3 = new Book("Kuinka lihoa koodaamalla", "Sarja Syömäri", "978111222333-4", 2024, 29.0);
        Book book4 = new Book("Näin laihdut koodaamalla", "Lasse Laihduttaja", "97855566677-78", 2026, 30.4);

        repository.save(book1);
        repository.save(book2);
        repository.save(book3);
        repository.save(book4);
	};
}
}