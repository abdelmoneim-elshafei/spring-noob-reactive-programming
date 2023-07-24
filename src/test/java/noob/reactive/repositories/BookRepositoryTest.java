package noob.reactive.repositories;

import noob.reactive.config.DataBaseConfig;
import noob.reactive.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@Import(DataBaseConfig.class)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testSaveNewBook() {
        bookRepository.save(book())
                .subscribe(book -> {
                    System.out.println(book.toString());
                });

    }
    Book book(){
        return Book.builder()
                .bookName("Java")
                .author("abdo")
                .price(new BigDecimal("30.3"))
                .quantityOnHand(200)
                .isbn("324-34838483")
                .build();
    }
}