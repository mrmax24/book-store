package mate.academy.bookstore.repository.book;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.BookSearchParametersDto;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.SpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    @Autowired
    private final BookSpecificationProviderManager bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> spec = Specification.where(null);
        if (searchParametersDto.titles() != null && searchParametersDto.titles().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("title")
                    .getSpecification(searchParametersDto.titles()));
        }
        if (searchParametersDto.authors() != null && searchParametersDto.authors().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("author")
                    .getSpecification(searchParametersDto.authors()));
        }
        return spec;
    }
}