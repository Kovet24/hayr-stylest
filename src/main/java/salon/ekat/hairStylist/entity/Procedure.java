package salon.ekat.hairStylist.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Procedure {
    String name;
    String description;
    Integer price;
    Integer duration;
}
