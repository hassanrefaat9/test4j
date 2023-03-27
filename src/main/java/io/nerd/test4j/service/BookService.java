/**
 * @author Hassan Refaat <hassan.refaat.dev@gmail.com>
 * @Created 3/27/2023 2:07 AM
 */
package io.nerd.test4j.service;

import io.nerd.test4j.model.Book;
import io.nerd.test4j.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findById(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("value not found"));
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book deleteById(int id) {
        var optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            var book = optionalBook.get();
            bookRepository.delete(book);
            return book;
        } else {
            throw new RuntimeException("Can not delete this entity " + id + " because it is not present");
        }
    }

    public List<Book> findBookByName(String name) {
        return bookRepository.findByNameContaining(name);
    }
}
