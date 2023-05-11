package to.phuc.smyc.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import to.phuc.smyc.model.SnippetDTO;
import to.phuc.smyc.service.SnippetService;
import to.phuc.smyc.util.UserRoles;


@RestController
@RequestMapping(value = "/api/snippets", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('" + UserRoles.ROLE_USER + "')")
@SecurityRequirement(name = "bearer-jwt")
public class SnippetResource {

    private final SnippetService snippetService;

    public SnippetResource(final SnippetService snippetService) {
        this.snippetService = snippetService;
    }

    @GetMapping
    public ResponseEntity<List<SnippetDTO>> getAllSnippets(
            @RequestParam(required = false, name = "filter") final String filter) {
        return ResponseEntity.ok(snippetService.findAll(filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SnippetDTO> getSnippet(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(snippetService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSnippet(@RequestBody @Valid final SnippetDTO snippetDTO) {
        final Long createdId = snippetService.create(snippetDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSnippet(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SnippetDTO snippetDTO) {
        snippetService.update(id, snippetDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSnippet(@PathVariable(name = "id") final Long id) {
        snippetService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
