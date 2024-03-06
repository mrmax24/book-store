package mate.academy.bookstore.repository.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.exception.SpecificationNotFoundException;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.SpecificationProvider;
import mate.academy.bookstore.repository.SpecificationProviderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    @Autowired
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new SpecificationNotFoundException("Can't find"
                        + " correct specification provider for key " + key));

    }
}
