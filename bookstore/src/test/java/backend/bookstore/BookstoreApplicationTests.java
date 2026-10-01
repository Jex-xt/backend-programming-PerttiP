package backend.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import backend.bookstore.domain.AppUserRepository;
import backend.bookstore.domain.BookRepository;
import backend.bookstore.domain.CategoryRepository;
import backend.bookstore.web.BookController;
import backend.bookstore.web.BookRestController;

import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest

class BookstoreApplicationTests {

	@Autowired
  	private BookController controller;

	@Autowired
	private BookRestController restController;

	 @Autowired
    private BookRepository bookRepository;

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void contextLoads() throws Exception{
	}

	@Test
    public void bookControllerLoads() throws Exception {
      assertThat(controller).isNotNull();
	}

	@Test
	 public void bookRestControllerLoads() throws Exception {
		assertThat(restController).isNotNull();
	 }

	 @Test
	  public void bookRepositoryLoads() throws Exception {
		assertThat(bookRepository).isNotNull();
	  }

	 @Test
	 public void appUserRepositoryLoads() throws Exception {
		assertThat(appUserRepository).isNotNull();
	 }

	 @Test
	 public void categoryRepositoryLoads() throws Exception {
			assertThat(categoryRepository).isNotNull();
	 }

}
