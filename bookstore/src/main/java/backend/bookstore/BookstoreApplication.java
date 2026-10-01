package backend.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;
import backend.bookstore.domain.Category;
import backend.bookstore.domain.CategoryRepository;
import backend.bookstore.domain.AppUser;
import backend.bookstore.domain.AppUserRepository;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    /* @Bean
    public CommandLineRunner demo(BookRepository repository, CategoryRepository categoryRepository, UserRepository userRepository) {
	return (args) -> {
	  // Your code...add some demo data to db
        Category category1 = new Category("Programming");
        Category category2 = new Category("Health");
        Category category3 = new Category("Humor");
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        
        Book book1 = new Book("Suuri koodaus kirja", "Kalle Koodari", "978123456789-7", 1999, 20.9,category1);
        Book book2 = new Book("Pieni koodaus kirja", "Niko Nörtti", "97898765432-10", 2004, 15.90, category1);
        Book book3 = new Book("Kuinka lihoa koodaamalla", "Sarja Syömäri", "978111222333-4", 2024, 29.0, category3);
        Book book4 = new Book("Näin laihdut koodaamalla", "Lasse Laihduttaja", "97855566677-78", 2026, 30.4, category2);
        repository.save(book1);
        repository.save(book2);
        repository.save(book3);
        repository.save(book4);

        User user1 =new User("Kalle", "$2a$10$WcWpFoNI3GSUIcq/8V/gg.vC.uUO6I5fc5/SUujMQ/rVqbyGhMty2", "kalle.j@kallela.com", "ADMIN");
        User user2 =new User("Aapo",  "$2a$10$koxVJcS.nmNz7KyYCs4WNO57SAL/nsC85Cpv/VnELxdMYpdVv.leC", "aapo.hh@aapola.com", "USER");
        User user3 =new User("Heli", "$2a$10$koxVJcS.nmNz7KyYCs4WNO57SAL/nsC85Cpv/VnELxdMYpdVv.leC", "heli.o@helila.com", "USER");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        };

    } */
}