 package backend.bookstore.web;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class BookRestController {

	private final BookRepository bookRepository;

	public BookRestController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
		@GetMapping("/books")
		public Iterable<Book>getBooks() {
			return bookRepository.findAll();
		}
		@GetMapping("/books/{id}")
		public Optional<Book> findById(@PathVariable("id") Long bookId) {
			return bookRepository.findById(bookId);
		}
		@PostMapping("/books")
		public Book addBook(@RequestBody Book book) {
			return bookRepository.save(book);
		}
		@DeleteMapping("/books/{id}")
		public Iterable<Book> deleteById(@PathVariable("id") Long bookId) {
			bookRepository.deleteById(bookId);
		 	return bookRepository.findAll();  //Listataan lopuksi "Muokattu" lista
		}

		@PutMapping("/books/{id}")
		public Book editBook(@RequestBody Book editedBook, @PathVariable Long id) {
    		editedBook.setId(id);
			return bookRepository.save(editedBook);  // Tulostaa lopuksi muokatun kirjan
		}
		
}   

