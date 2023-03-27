/**
 * @author Hassan Refaat <hassan.refaat.dev@gmail.com>
 * @Created 3/27/2023 2:38 AM
 */
package io.nerd.test4j.Config;

import io.nerd.test4j.model.Book;
import io.nerd.test4j.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            bookRepository.save(new Book("Spring boot edition"+i,100+i*10));
        }
    }
}
