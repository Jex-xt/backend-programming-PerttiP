package backend.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import backend.bookstore.domain.BookRepository;

@Controller
public class BookController {
    
    private BookRepository repository;

    public BookController(BookRepository repository) {
    this.repository = repository;
} 

    @GetMapping("/booklist")
    public String bookstore(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }  
}
