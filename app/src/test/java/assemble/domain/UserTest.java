package assemble.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 객체의")
class UserTest {
    private User user;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        user = User.builder().build();
    }

    @Nested
    @DisplayName("changeWith 메서드는")
    class Describe_changeWith {
        @Test
        @DisplayName("사용자 정보를 변경한다.")
        void it_change_user() {
            user.changeWith(User.builder()
                    .name("TEST")
                    .build());

            assertThat(user.getName()).isEqualTo("TEST");
        }
    }

    @Nested
    @DisplayName("destroy 메서드는")
    class Describe_destroy {
        @Test
        @DisplayName("사용자 정보를 탈퇴 처리된 것으로 만든다.")
        void it_make_user_state_be_withdrew() {
            assertThat(user.isDeleted()).isFalse();

            user.destroy();

            assertThat(user.isDeleted()).isTrue();
        }
    }

    @Nested
    @DisplayName("changePassword 메서드는")
    class Describe_changePassword {
        @Test
        @DisplayName("사용자의 비밀번호를 변경한다.")
        void it_change_password_of_user() {
            user.changePassword("TEST", passwordEncoder);

            assertThat(user.getPassword()).isNotEmpty();
            assertThat(user.getPassword()).isNotEqualTo("TEST");
        }
    }
}
