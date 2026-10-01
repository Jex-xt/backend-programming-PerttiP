package backend.bookstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Positive;

@Entity
public class Book {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @NotBlank(message = "Syötä otsikko")
    private String title;

    @NotBlank(message = "Syötä tekijä")
    private String author;

    @NotBlank (message = "Syötä isbn")
    private String isbn;

    @Min(value = 1500, message = "Vuosiluku täytyy olla vähintään 1500")
    @Max(value = 2026, message = "Vuosiluku täytyy olla 2026 tai pienempi")
    private int publicationYear;

    @ManyToOne
    private Category category;

    public Book() {
    }

    public Book(String title, String author, String isbn, int publicationYear, Category category) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.category = category;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    
}
