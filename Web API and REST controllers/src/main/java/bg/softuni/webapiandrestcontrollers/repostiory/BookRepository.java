package bg.softuni.webapiandrestcontrollers.repostiory;

import bg.softuni.webapiandrestcontrollers.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
