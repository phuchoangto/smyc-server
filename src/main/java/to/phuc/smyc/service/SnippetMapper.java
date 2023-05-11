package to.phuc.smyc.service;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import to.phuc.smyc.domain.Snippet;
import to.phuc.smyc.domain.User;
import to.phuc.smyc.model.SnippetDTO;
import to.phuc.smyc.repos.UserRepository;
import to.phuc.smyc.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SnippetMapper {

    @Mapping(target = "user", ignore = true)
    SnippetDTO updateSnippetDTO(Snippet snippet, @MappingTarget SnippetDTO snippetDTO);

    @AfterMapping
    default void afterUpdateSnippetDTO(Snippet snippet, @MappingTarget SnippetDTO snippetDTO) {
        snippetDTO.setUser(snippet.getUser() == null ? null : snippet.getUser().getId());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Snippet updateSnippet(SnippetDTO snippetDTO, @MappingTarget Snippet snippet,
            @Context UserRepository userRepository);

    @AfterMapping
    default void afterUpdateSnippet(SnippetDTO snippetDTO, @MappingTarget Snippet snippet,
            @Context UserRepository userRepository) {
        final User user = snippetDTO.getUser() == null ? null : userRepository.findById(snippetDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        snippet.setUser(user);
    }

}
