package salon.ekat.hairStylist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AppointmentDTO {
    Long id;

    @NotNull
    Long masterId;

    @NotNull
    Long clientId;

    @NotNull
    Long procedureId;

    @NotNull
    LocalDateTime startDateTime;

    LocalDateTime endDateTime;
}
