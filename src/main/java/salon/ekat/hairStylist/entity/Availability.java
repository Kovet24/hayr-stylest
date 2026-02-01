package salon.ekat.hairStylist.entity;

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
public class Availability {
    LocalDate dayOfWork;
    LocalTime startTime;
    LocalTime endTime;
    LocalTime breakStart;
    LocalTime breakEnd;
}
