/**
 * @author Hassan Refaat <hassan.refaat.dev@gmail.com>
 * @Created 3/27/2023 2:07 AM
 */
package io.nerd.test4j.repository;

import io.nerd.test4j.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

  List<Book> findByNameContaining(String name);

}
