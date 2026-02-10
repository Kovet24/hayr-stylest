package salon.ekat.hairStylist.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Service {
    Long id;
    String name;
    String description;
    Double price;
    Integer duration;
}
