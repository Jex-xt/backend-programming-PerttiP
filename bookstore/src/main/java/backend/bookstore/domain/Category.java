package backend.bookstore.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity 
public class Category {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO) Hibernate generoi id:n
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tietokanta generoi id:n
    private Long id;

    private String name;

    
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="category")
    @JsonIgnore 
    private List<Book> books;

    public Category() {   
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", books=" + books + "]";
    }
    
}
