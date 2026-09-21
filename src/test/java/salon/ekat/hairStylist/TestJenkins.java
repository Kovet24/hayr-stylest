package salon.ekat.hairStylist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestJenkins {
    @Test
    void intentionalFailure() {
        Assertions.assertEquals(10, 20);
    }
}
