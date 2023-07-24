package noob.reactive.bootstrap;

import lombok.RequiredArgsConstructor;
import noob.reactive.domain.Book;
import noob.reactive.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataBootstrap implements CommandLineRunner {
    private final BookRepository bookRepository;
    @Override
    public void run(String... args) throws Exception {
        loadData();

        bookRepository.count().subscribe(count ->{
            System.out.println("count is : " + count);
        });
    }
    void loadData(){
        bookRepository.count().subscribe(count ->{
            if (count == 0){
                Book book1 = Book.builder()
                        .bookName("The Great Gatsby")
                        .author("F. Scott Fitzgerald")
                        .isbn("978-3-16-148410-0")
                        .price(BigDecimal.valueOf(19.99))
                        .quantityOnHand(10)
                        .build();

                Book book2 = Book.builder()
                        .bookName("To Kill a Mockingbird")
                        .author("Harper Lee")
                        .isbn("978-3-16-148410-1")
                        .price(BigDecimal.valueOf(14.99))
                        .quantityOnHand(5)
                        .build();

                Book book3 = Book.builder()
                        .bookName("1984")
                        .author("George Orwell")
                        .isbn("978-3-16-148410-2")
                        .price(BigDecimal.valueOf(12.99))
                        .quantityOnHand(8)
                        .build();

                Book book4 = Book.builder()
                        .bookName("Pride and Prejudice")
                        .author("Jane Austen")
                        .isbn("978-3-16-148410-3")
                        .price(BigDecimal.valueOf(9.99))
                        .quantityOnHand(12)
                        .build();

                bookRepository.save(book1).subscribe();
                bookRepository.save(book2).subscribe();
                bookRepository.save(book3).subscribe();
                bookRepository.save(book4).subscribe();
            }
        });

    }
}
