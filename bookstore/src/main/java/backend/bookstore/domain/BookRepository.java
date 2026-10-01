package backend.bookstore.domain; 
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
        Book findByTitle(String title);

}

