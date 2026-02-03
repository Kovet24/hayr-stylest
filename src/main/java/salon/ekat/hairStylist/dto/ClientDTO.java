package salon.ekat.hairStylist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientDTO {
    @NotNull
    Long id;

    @NotBlank
    String name;

    @NotNull
    Integer number;
}
