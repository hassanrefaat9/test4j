package io.nerd.test4j.repository;

import io.nerd.test4j.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {
    /*
     * @DataJpaTest Annotation
     * Using @DataJpaTest only loads @Repository spring components,
     * and will greatly improve performance by not loading @Service, @Controller, etc.
     * */

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findByNameContaining() {
        //given
        var book = new Book("Spring boot 2th", 200.0);
        var name = "Spring boot 2th";
        bookRepository.save(book);
        //when
        var containing = bookRepository.findByNameContaining(name);
        //then
        assertThat(containing).hasSize(1);
        assertThat(containing).contains(book);

    }
}