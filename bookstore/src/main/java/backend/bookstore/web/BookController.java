package backend.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import backend.bookstore.domain.BookRepository;

@Controller
public class BookController {
    
    private BookRepository repository;

    public BookController(BookRepository repository) {
    this.repository = repository;
} 

    @GetMapping("/index")
    @ResponseBody
    public String bookstore() {
    return "Bookstore";
    }  
}
