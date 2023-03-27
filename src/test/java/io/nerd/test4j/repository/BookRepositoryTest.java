package io.nerd.test4j.repository;

import io.nerd.test4j.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {
    /*
     * @DataJpaTest Annotation
     * Using @DataJpaTest only loads @Repository spring components,
     * and will greatly improve performance by not loading @Service, @Controller, etc.
     * */

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findByNameContaining() {
        //given
       var book = new Book("Spring boot 2th",200.0);
       var name = "Spring boot 2th";
       bookRepository.save(book);

       var containing = "Spring boot 2th";

       assertEquals(containing,"Spring boot 2th");

    }
}