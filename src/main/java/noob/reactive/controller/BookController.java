package noob.reactive.controller;

import noob.reactive.model.BookDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class BookController {
    public static final String BOOK_PATH = "/api/v2/book";

    @GetMapping(BOOK_PATH)
    Flux<BookDTO> listBook(){
        return Flux.just(BookDTO.builder().bookName("Hello").id(1).build(),
                BookDTO.builder().id(2).bookName("C++").build());
    }

}
