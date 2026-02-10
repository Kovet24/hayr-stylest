package salon.ekat.hairStylist.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ServiceDTO {
    Long id;

    @NotBlank
    String name;

    @NotEmpty
    String description;

    @Min(0)
    Double price;

    @Min(30)
    Integer duration;
}
