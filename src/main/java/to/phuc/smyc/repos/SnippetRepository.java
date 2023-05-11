package to.phuc.smyc.repos;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import to.phuc.smyc.domain.Snippet;


public interface SnippetRepository extends JpaRepository<Snippet, Long> {

    List<Snippet> findAllById(Long id, Sort sort);

}
