package noob.reactive.service;

import lombok.RequiredArgsConstructor;
import noob.reactive.domain.Book;
import noob.reactive.mapper.BookMapper;
import noob.reactive.model.BookDTO;
import noob.reactive.repositories.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;
    @Override
    public Flux<BookDTO> listAllBook() {
        return bookRepository.findAll().map(mapper::bookToBookDTO);
    }

    @Override
    public Mono<BookDTO> getBookById(Integer id) {
        return bookRepository.findById(id).map(mapper::bookToBookDTO);
    }

    @Override
    public Mono<BookDTO> createNewBook(BookDTO bookDTO) {
        return bookRepository.save(mapper.bookDTOToBook(bookDTO)).map(mapper::bookToBookDTO);
    }

    @Override
    public Mono<BookDTO> updateBookById(Integer id, BookDTO bookDTO) {
        return bookRepository.findById(id).map(foundBook ->{
            foundBook.setBookName(bookDTO.getBookName());
            foundBook.setIsbn(bookDTO.getIsbn());
            foundBook.setAuthor(bookDTO.getAuthor());
            foundBook.setPrice(bookDTO.getPrice());
            foundBook.setQuantityOnHand(bookDTO.getQuantityOnHand());
            return foundBook;
        }).flatMap(bookRepository::save)
                .map(mapper::bookToBookDTO);
    }

    @Override
    public Mono<Void> deleteBookById(Integer id) {
        return bookRepository.deleteById(id);
    }
}
