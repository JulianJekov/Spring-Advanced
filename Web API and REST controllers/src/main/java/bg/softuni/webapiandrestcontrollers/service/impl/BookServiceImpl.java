package bg.softuni.webapiandrestcontrollers.service.impl;

import bg.softuni.webapiandrestcontrollers.model.dto.AuthorDTO;
import bg.softuni.webapiandrestcontrollers.model.dto.BookDTO;
import bg.softuni.webapiandrestcontrollers.model.entity.Author;
import bg.softuni.webapiandrestcontrollers.model.entity.Book;
import bg.softuni.webapiandrestcontrollers.repostiory.AuthorRepository;
import bg.softuni.webapiandrestcontrollers.repostiory.BookRepository;
import bg.softuni.webapiandrestcontrollers.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void deleteBookById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Long createBook(BookDTO bookDTO) {
        String authorName = bookDTO.getAuthor().getName();
        Author author = this.authorRepository.findByName(authorName).orElse(null);

        if (author == null) {
            author = new Author().setName(authorName);
            authorRepository.save(author);
        }

        Book book = new Book().setAuthor(author).setIsbn(bookDTO.getIsbn()).setTitle(bookDTO.getTitle());

        this.bookRepository.save(book);

        return book.getId();
    }

    @Override
    public List<BookDTO> getAllBooks() {

        return this.bookRepository.findAll()
                .stream()
                .map(BookServiceImpl::mapToDTO)
                .toList();
    }

    public Optional<BookDTO> getBookById(Long id) {
        return this.bookRepository.findById(id).map(BookServiceImpl::mapToDTO);
    }

    private static BookDTO mapToDTO(Book book) {
        return new BookDTO().setId(book.getId())
                .setIsbn(book.getIsbn())
                .setAuthor(new AuthorDTO().setName(book.getAuthor().getName()))
                .setTitle(book.getTitle());

    }
}
