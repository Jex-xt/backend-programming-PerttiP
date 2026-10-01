package backend.bookstore;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTesting {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHeader() throws Exception {
        this.mockMvc.perform(
            get("/booklist")
                .with(user("Kalle").authorities(() -> "ADMIN")))
        .andDo(print()) //Tulostaa vastaukset konsoliin
        .andExpect(status().isOk()) // HTTP status tarkastus 200
        .andExpect(content().string(containsString("Books"))); 
    }
    @Test
    void getBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andDo(print())
                .andExpect(status().isOk());
    }
 
    @Test
    void getApiCategories() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAppUsers() throws Exception {
        mockMvc.perform(get("/api/appUsers"))
                .andDo(print()) 
                .andExpect(status().isOk());
    }
    @Test
    void getBookById() throws Exception {
        mockMvc.perform(get("/api/books/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Suuri koodaus kirja")));        
    }
    @Test
    void getAppUserById() throws Exception {
        mockMvc.perform(get("/api/appUsers/2"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("USER")));        
    }
     
}
