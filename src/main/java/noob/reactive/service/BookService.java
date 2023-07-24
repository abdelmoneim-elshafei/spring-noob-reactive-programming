package noob.reactive.service;

import noob.reactive.model.BookDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Flux<BookDTO> listAllBook();
    Mono<BookDTO> getBookById(Integer id);
    Mono<BookDTO> createNewBook(BookDTO bookDTO);

    Mono<BookDTO> updateBookById(Integer id, BookDTO bookDTO);

    Mono<Void> deleteBookById(Integer id);
}
