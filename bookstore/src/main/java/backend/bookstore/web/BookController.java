package backend.bookstore.web;

import backend.bookstore.domain.CategoryRepository;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class BookController {
    
    private BookRepository repository;
    private CategoryRepository categoryRepository;

    public BookController(BookRepository repository, CategoryRepository categoryRepository) {
    this.repository = repository;
    this.categoryRepository = categoryRepository;
} 

    @GetMapping("/booklist")
    public String bookstore(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }
    @GetMapping("/addbook")
    public String addbook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addbook";
    }
    /* @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        repository.save(book);
        return "redirect:/booklist";
    } */

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute Book book, //@Valid annotaatio validointia varten
        BindingResult bindingresult, Model model) { //BindingResult sisältää validoinnin tulokset

        if (bindingresult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll()); //Lisää kategoriat uudellen valikkoon
            return "addbook";
        }
    repository.save(book);
    return "redirect:/booklist";    
    }
        
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId) {
	repository.deleteById(bookId);
	return "redirect:../booklist";
    }
    @GetMapping("/edit/{id}")
    public String editbook(@PathVariable ("id") Long bookID, Model model) {
        model.addAttribute("book",repository.findById(bookID).get());
        return "editbook";
    }
   /*  @PostMapping("/edit")
    public String SaveEditBook(@ModelAttribute Book book) {
        repository.save(book);
        return "redirect:/booklist";
    } */
   @PostMapping("/edit")
   public String SaveEditBook(@Valid @ModelAttribute Book book,
        BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return "editbook";   
        }
    repository.save(book);
        return "redirect:/booklist";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }    

}