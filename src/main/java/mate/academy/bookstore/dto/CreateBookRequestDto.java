package mate.academy.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import mate.academy.bookstore.validation.Isbn;

@Data
public class CreateBookRequestDto {
    private static final int MIN_VALUE = 0;
    private static final int MIN_DESCRIPTION_LENGTH = 25;

    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @Isbn
    private String isbn;
    @NotNull
    @Min(value = MIN_VALUE)
    private BigDecimal price;
    @Size(min = MIN_DESCRIPTION_LENGTH)
    private String description;
    private String coverImage;
}
