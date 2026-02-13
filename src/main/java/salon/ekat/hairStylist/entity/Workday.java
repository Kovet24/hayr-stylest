package salon.ekat.hairStylist.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Класс представляет из себя один рабочий день какого-либо объекта {@link Master}, это ограничитель для брони.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Workday {
    Long masterId;
    LocalDate dayOfWork;
    LocalTime shiftStart;
    LocalTime shiftEnd;
    LocalTime breakStart;
    LocalTime breakEnd;
}
