package assemble.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 객체의")
class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().build();
    }

    @Nested
    @DisplayName("changeWith 메서드는")
    class Describe_changeWith {
        @Test
        @DisplayName("사용자 정보를 변경한다.")
        void changeWith() {
            user.changeWith(User.builder()
                    .name("TEST")
                    .build());

            assertThat(user.getName()).isEqualTo("TEST");
        }
    }
}
