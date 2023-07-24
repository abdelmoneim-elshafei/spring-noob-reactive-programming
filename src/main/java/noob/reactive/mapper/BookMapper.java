package noob.reactive.mapper;

import noob.reactive.domain.Book;
import noob.reactive.model.BookDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    Book bookDTOToBook(BookDTO bookDTO);
    BookDTO bookToBookDTO(Book book);
}
