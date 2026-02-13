package salon.ekat.hairStylist.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Appointment {
    Long id;
    Long masterId;
    Long clientId;
    Long procedureId;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    // Status status; // на будущее
}
