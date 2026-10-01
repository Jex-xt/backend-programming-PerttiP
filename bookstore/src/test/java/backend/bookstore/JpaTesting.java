package backend.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import backend.bookstore.domain.AppUser;
import backend.bookstore.domain.AppUserRepository;
import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;
import backend.bookstore.domain.Category;
import backend.bookstore.domain.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaTesting {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void createCategory() {
        Category category = new Category("Sport");
        Category savedCategory = categoryRepository.save(category);
        assertThat(savedCategory.getId()).isNotNull();
    }
    @Test
    public void deleteCategory() {
        Category category = new Category("Sport");
        Category savedCategory = categoryRepository.save(category);
        categoryRepository.deleteById(savedCategory.getId());
        assertThat(categoryRepository.findById(savedCategory.getId())).isEmpty();
    }
    @Test
    public void searchCategory() {
        Category category = new Category("Sport");
        categoryRepository.save(category);
        Category createdCategory = categoryRepository.findById(category.getId()).orElse(null); //
        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory.getName()).isEqualTo("Sport");
    }
    @Test
    public void createAppUser() {
        AppUser appUser = new AppUser("Testaaja", "pass12345", "test@testaaja.com", "USER");
        AppUser SavedAppuser = appUserRepository.save(appUser);
        assertThat(SavedAppuser.getId()).isNotNull();
    }
    @Test
    public void deleteAppUser () {
            AppUser appUser = new AppUser("Testaaja", "pass12345", "test@testaaja.com", "USER");
            AppUser savedAppuser = appUserRepository.save(appUser);
            appUserRepository.deleteById(savedAppuser.getId());
            assertThat(appUserRepository.findById(savedAppuser.getId())).isEmpty();
    }
    @Test
    public void searchAppUse() {
            AppUser appUser = new AppUser("Testaaja", "pass12345", "test@testaaja.com", "USER");
            appUserRepository.save(appUser);
            AppUser createdAppUser = appUserRepository.findByUsername(appUser.getUsername());
            assertThat(createdAppUser).isNotNull();
            assertThat(createdAppUser.getUsername()).isEqualTo("Testaaja");
    }
    @Test
    public void createBook() {
        Category category1 = new Category("Programming");
        categoryRepository.save(category1);

        Book book = new Book("Testaajan opas", "Tarvo Testaaja", "868123454799-1", 2005, category1);
        Book savedBook = bookRepository.save(book);
        assertThat(savedBook.getId()).isNotNull();
    }
    @Test
    public void deleteBook() {
        Category category1 = new Category("Programming");
        categoryRepository.save(category1);

        Book book = new Book("Testaajan opas", "Tarvo Testaaja", "868123454799-1", 2005, category1);
        Book savedBook = bookRepository.save(book);
        bookRepository.deleteById(savedBook.getId());
        assertThat(bookRepository.findById(savedBook.getId())).isEmpty();
    }
    @Test
    public void searchBook() {
        Category category1 = new Category("Programming");
        categoryRepository.save(category1);

        Book book = new Book("Testaajan opas", "Tarvo Testaaja", "868123454799-1", 2005, category1);
        bookRepository.save(book);
        Book createdBook = bookRepository.findByTitle(book.getTitle());
        assertThat(createdBook).isNotNull();
        assertThat(createdBook.getTitle()).isEqualTo("Testaajan opas");
        assertThat(createdBook.getIsbn()).isEqualTo("868123454799-1");
        assertThat(createdBook.getAuthor()).isEqualTo("Tarvo Testaaja");
        assertThat(createdBook.getPublicationYear()).isEqualTo(2005);
        assertThat(createdBook.getCategory()).isEqualTo(category1);
        assertThat(createdBook.getIsbn()).hasSize(14); //Ei tarvetta välttämättä koska merkkijonon vastaavuus tarkistetaan jos isbn tarkistuksessa
    }
}
