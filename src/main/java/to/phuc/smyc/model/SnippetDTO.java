package to.phuc.smyc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SnippetDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    private String content;

    @Size(max = 255)
    private String password;

    @NotNull
    @JsonProperty("isPublic")
    private Boolean isPublic;

    private OffsetDateTime expiredAt;

    private UUID user;

}
