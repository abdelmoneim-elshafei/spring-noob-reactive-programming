package noob.reactive.controller;

import noob.reactive.domain.Book;
import noob.reactive.model.BookDTO;
import noob.reactive.repositories.BookRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BookControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @Test
    @Order(999)
    void deleteBookById() {
        webTestClient.delete().uri(BookController.BOOK_PATH_ID,2)
                .exchange()
                .expectStatus().isNoContent();
    }
    @Test
    void deleteBookByIdNotFound() {
        webTestClient.delete().uri(BookController.BOOK_PATH_ID,200)
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    void updateBookById() {
        webTestClient.put().uri(BookController.BOOK_PATH_ID,2)
                .body(Mono.just(BookRepositoryTest.book()),BookDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isNoContent();
    }
    @Test
    void updateBookByIdNotFound() {
        webTestClient.put().uri(BookController.BOOK_PATH_ID,200)
                .body(Mono.just(BookRepositoryTest.book()),BookDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    void updateBookByIdBad() {
        Book book = BookRepositoryTest.book();
        book.setBookName("");
        webTestClient.put().uri(BookController.BOOK_PATH_ID,2)
                .body(Mono.just(book),BookDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    void createNewBookBad() {
        Book book = BookRepositoryTest.book();
        book.setBookName("");
        webTestClient.post().uri(BookController.BOOK_PATH)
                .body(Mono.just(book),BookDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    void createNewBook() {
        webTestClient.post().uri(BookController.BOOK_PATH)
                .body(Mono.just(BookRepositoryTest.book()),BookDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/book/5");
    }
    @Test
    void getBookByIdNotFound() {
        webTestClient.get().uri(BookController.BOOK_PATH_ID,200)
                .exchange()
                .expectStatus().isNotFound();

    }
    @Test
    @Order(1)
    void getBookById() {
        webTestClient.get().uri(BookController.BOOK_PATH_ID,2)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody(BookDTO.class);
    }
    @Test
    void listAllBook() {
        webTestClient.get().uri(BookController.BOOK_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(4);
    }
}