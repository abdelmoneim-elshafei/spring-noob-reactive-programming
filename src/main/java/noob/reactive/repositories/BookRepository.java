package noob.reactive.repositories;

import noob.reactive.domain.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookRepository extends ReactiveCrudRepository<Book,Integer> {

}
