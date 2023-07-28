package noob.reactive.controller;

import lombok.RequiredArgsConstructor;
import noob.reactive.model.BookDTO;
import noob.reactive.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class BookController {
    public static final String BOOK_PATH = "/api/v2/book";
    public static final String BOOK_PATH_ID = BOOK_PATH + "/{bookId}";

    private final BookService bookService;

    @GetMapping(BOOK_PATH_ID)
    Mono<BookDTO> getBookById(@PathVariable("bookId") Integer id){
        return bookService.getBookById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping(BOOK_PATH)
    Flux<BookDTO> listBook(){
        return bookService.listAllBook();
    }

    @PostMapping(BOOK_PATH)
    Mono<ResponseEntity<?>> createNewBook(@Validated @RequestBody BookDTO bookDTO)   {

         return bookService.createNewBook(bookDTO).map(savedBook -> ResponseEntity.created(UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080" + BOOK_PATH + "/" + savedBook.getId()).build().toUri()).build());


     //   return bookService.createNewBook(bookDTO).handle((savedBook, sink) -> {
     //       try {
     //           sink.next(ResponseEntity.created(new URI(BOOK_PATH + "/" + savedBook.getId())).build());
     //       } catch (URISyntaxException e) {
     //           sink.error(new RuntimeException(e));
     //       }
     //   });
    }

    @PutMapping(BOOK_PATH_ID)
    Mono<ResponseEntity<?>> updateBookById(@PathVariable("bookId") Integer id,@Validated @RequestBody BookDTO bookDTO ){
       return bookService.updateBookById(id,bookDTO)
               .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
               .map(savedBook -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(BOOK_PATH_ID)
    Mono<ResponseEntity<?>> deleteBookById(@PathVariable("bookId") Integer id){
        return bookService
                .getBookById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(bookDTO -> bookService.deleteBookById(bookDTO.getId()))
                .then(Mono.fromCallable(() -> ResponseEntity.noContent().build()));
    }

}
