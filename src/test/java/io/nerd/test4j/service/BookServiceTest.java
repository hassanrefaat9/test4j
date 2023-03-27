package io.nerd.test4j.service;

import io.nerd.test4j.model.Book;
import io.nerd.test4j.repository.BookRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void addBook() {
        var book = new Book("Spring boot", 200.0);
        underTest.addBook(book);
        verify(bookRepository).save(book);
    }

    @Test
    @Disabled
    void deleteById() {
    }

    @Test
    @Disabled
    void findBookByName() {
    }
}