package noob.reactive.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BookDTO {

    private Integer id;

    @NotBlank
    @Size(min = 3 , max = 255)
    private String bookName;

    @Size(min = 3 , max = 255)
    private String author;

    @Size(min = 13 , max = 25)
    private String isbn;

    private BigDecimal price;
    private Integer quantityOnHand;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;


}
