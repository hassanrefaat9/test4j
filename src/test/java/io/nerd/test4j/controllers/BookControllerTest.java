package io.nerd.test4j.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nerd.test4j.model.Book;
import io.nerd.test4j.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@Slf4j
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private final static Book BOOK = new Book("Spring boot 1th", 300d);

    @Test
    void testFindAllBooks() throws Exception {
        var books = List.of(
                new Book("Spring boot 1th", 300d),
                new Book("Spring boot 2th", 200d)
        );

        when(bookService.findAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/v1/books/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(books.get(0).getName()))
                .andExpect(jsonPath("$[0].price").value(books.get(0).getPrice()))
                .andExpect(jsonPath("$[1].name").value(books.get(1).getName()))
                .andExpect(jsonPath("$[1].price").value(books.get(1).getPrice()));

    }

    @Test
    void testFindByName() throws Exception {
        var name = "Spring";
        var books = List.of(
                new Book("Spring boot 1th", 300d),
                new Book("Spring boot 2th", 200d)
        );

        when(bookService.findBookByName(name)).thenReturn(books);

        mockMvc.perform(get("/api/v1/books/filter")
                        .param("name", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(books.get(0).getName()))
                .andExpect(jsonPath("$[0].price").value(books.get(0).getPrice()))
                .andExpect(jsonPath("$[1].name").value(books.get(1).getName()))
                .andExpect(jsonPath("$[1].price").value(books.get(1).getPrice()));

    }


    @Test
    void testFindById() throws Exception {
        var id = 1;

        when(bookService.findById(id)).thenReturn(BOOK);

        mockMvc.perform(get("/api/v1/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(BOOK.getName()));

    }

    @Test
    void canAddBook() throws Exception {

        when(bookService.addBook(BOOK)).thenReturn(BOOK);

        mockMvc.perform(post("/api/v1/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(BOOK)))
                .andExpect(status().isCreated());

    }

    @Test
    void canDeleteById() throws Exception {
        var id = 1;
        when(bookService.deleteById(id)).thenReturn(BOOK);

        mockMvc.perform(delete("/api/v1/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(BOOK.getName()));
    }

    @Test
    public void testDeleteById_WhenBookNotFound() throws Exception {
        var id = 1;
        when(bookService.deleteById(id)).thenThrow(new RuntimeException("Can not delete this entity " + id + " because it is not present"));

        var exception = assertThrows(RuntimeException.class, () -> bookService.deleteById(id));
        assertEquals("Can not delete this entity " + id + " because it is not present", exception.getMessage());

    }
}