package bg.softuni.webapiandrestcontrollers.service;

import bg.softuni.webapiandrestcontrollers.model.dto.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDTO> getAllBooks();
    Optional<BookDTO> getBookById(Long id);
    void deleteBookById(Long id);

    Long createBook(BookDTO bookDTO);
}
