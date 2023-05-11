package to.phuc.smyc.service;

import java.util.List;
import to.phuc.smyc.model.SnippetDTO;


public interface SnippetService {

    List<SnippetDTO> findAll(final String filter);

    SnippetDTO get(final Long id);

    Long create(final SnippetDTO snippetDTO);

    void update(final Long id, final SnippetDTO snippetDTO);

    void delete(final Long id);

}
