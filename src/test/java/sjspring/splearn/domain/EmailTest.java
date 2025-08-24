package sjspring.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void equality() throws Exception {
        // given
        Email email1 = new Email("tmdwhd@splearn.app");
        Email email2 = new Email("tmdwhd@splearn.app");
        // when

        // then
        Assertions.assertThat(email1).isEqualTo(email2);
    }

}