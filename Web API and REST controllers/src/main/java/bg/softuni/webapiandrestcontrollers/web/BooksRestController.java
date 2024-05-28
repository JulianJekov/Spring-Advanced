package bg.softuni.webapiandrestcontrollers.web;

import bg.softuni.webapiandrestcontrollers.model.dto.BookDTO;
import bg.softuni.webapiandrestcontrollers.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books/")
public class BooksRestController {

    private final BookService bookService;

    public BooksRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(this.bookService.getAllBooks());
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
        Optional<BookDTO> optionalBookDTO = this.bookService.getBookById(id);
        return optionalBookDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable("id") Long id) {
        this.bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO, UriComponentsBuilder uriComponentsBuilder) {
        long newBookId = this.bookService.createBook(bookDTO);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/books/{id}").build(newBookId))
                .build();
    }

}
