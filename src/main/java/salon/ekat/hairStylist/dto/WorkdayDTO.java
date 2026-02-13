package salon.ekat.hairStylist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WorkdayDTO {
    @NotNull
    Long masterId;

    @NotNull
    LocalDate dayOfWork;

    @NotNull
    LocalTime shiftStart;

    @NotNull
    LocalTime shiftEnd;

    LocalTime breakStart;
    LocalTime breakEnd;
}
