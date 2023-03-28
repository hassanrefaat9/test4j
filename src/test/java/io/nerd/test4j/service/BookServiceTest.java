package io.nerd.test4j.service;

import io.nerd.test4j.model.Book;
import io.nerd.test4j.repository.BookRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService underTest;

    @Test
    void canFindAllBooks() {
        //when
        underTest.findAllBooks();
        //then
        verify(bookRepository).findAll();
    }

    @Test
    void testFindById() {
        var book = new Book("Spring boot", 200.0);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        var result = underTest.findById(1);
        verify(bookRepository).findById(1);
        assertEquals(book, result);
    }

    @Test
    void CanAddBook() {
        var book = new Book("Spring boot", 200.0);
        underTest.addBook(book);
        verify(bookRepository).save(book);
    }

    @Test
    void testDeleteById_BookFound() {
        var id = 1;
        var book = new Book("Spring boot", 200.0);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        var result = underTest.deleteById(id);

        verify(bookRepository, times(1)).delete(book);

        assertEquals(book, result);
    }


    @Test
    void testDeleteById_BookNotFound() {
        var id = 1;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> underTest.deleteById(id));
        assertEquals("Can not delete this entity " + id + " because it is not present", exception.getMessage());
        verify(bookRepository, never()).delete(any());
    }

    @Test
    void findBookByName() {
        // arrange
        String name = "Book";
        var books = List.of(
                new Book("Book Title 1", 100.0),
                new Book("Title 2 with Book", 100.0),
                new Book("Another Title", 100.0),
                new Book("Book Name with Book", 100.0)
        );
        when(bookRepository.findByNameContaining(name)).thenReturn(books);

        // act
        var foundBooks = underTest.findBookByName(name);

        // assert
        assertEquals(4, foundBooks.size());
        assertTrue(foundBooks.contains(books.get(0)));
        assertTrue(foundBooks.contains(books.get(3)));

    }
}