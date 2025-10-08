package org.buildozers.mvnjava21;

import static org.assertj.core.api.Assertions.assertThat;

import org.buildozers.dojo.MainProg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple MainProg.
 */
@DisplayName("Given the MainProg class")
class MainProgTest {

    @Test
    @DisplayName("when getMessage() is called, then the message should start with 'Hello from Java'")
    void testMessage() {
        // given / when
        String message = MainProg.getMessage();

        // then
        assertThat(message).asString().startsWith("Hello from Java");
    }

}
