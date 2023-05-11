package to.phuc.smyc.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import to.phuc.smyc.domain.Snippet;
import to.phuc.smyc.model.SnippetDTO;
import to.phuc.smyc.repos.SnippetRepository;
import to.phuc.smyc.repos.UserRepository;
import to.phuc.smyc.util.NotFoundException;


@Service
public class SnippetServiceImpl implements SnippetService {

    private final SnippetRepository snippetRepository;
    private final UserRepository userRepository;
    private final SnippetMapper snippetMapper;

    public SnippetServiceImpl(final SnippetRepository snippetRepository,
            final UserRepository userRepository, final SnippetMapper snippetMapper) {
        this.snippetRepository = snippetRepository;
        this.userRepository = userRepository;
        this.snippetMapper = snippetMapper;
    }

    @Override
    public List<SnippetDTO> findAll(final String filter) {
        List<Snippet> snippets;
        final Sort sort = Sort.by("id");
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            snippets = snippetRepository.findAllById(longFilter, sort);
        } else {
            snippets = snippetRepository.findAll(sort);
        }
        return snippets.stream()
                .map((snippet) -> snippetMapper.updateSnippetDTO(snippet, new SnippetDTO()))
                .toList();
    }

    @Override
    public SnippetDTO get(final Long id) {
        return snippetRepository.findById(id)
                .map(snippet -> snippetMapper.updateSnippetDTO(snippet, new SnippetDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final SnippetDTO snippetDTO) {
        final Snippet snippet = new Snippet();
        snippetMapper.updateSnippet(snippetDTO, snippet, userRepository);
        return snippetRepository.save(snippet).getId();
    }

    @Override
    public void update(final Long id, final SnippetDTO snippetDTO) {
        final Snippet snippet = snippetRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        snippetMapper.updateSnippet(snippetDTO, snippet, userRepository);
        snippetRepository.save(snippet);
    }

    @Override
    public void delete(final Long id) {
        snippetRepository.deleteById(id);
    }

}
