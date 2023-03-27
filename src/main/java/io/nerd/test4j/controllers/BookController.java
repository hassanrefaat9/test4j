/**
 * @author Hassan Refaat <hassan.refaat.dev@gmail.com>
 * @Created 3/27/2023 2:08 AM
 */
package io.nerd.test4j.controllers;

import io.nerd.test4j.model.Book;
import io.nerd.test4j.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/filter")
    public List<Book> findByName(@RequestParam(name = "name") String name) {
        return bookService.findBookByName(name);
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable int id) {
        return bookService.findById(id);
    }

    @PostMapping("/")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @DeleteMapping("/{id}")
    public Book deleteById(@PathVariable int id) {
        return bookService.deleteById(id);
    }

}
