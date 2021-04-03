package assemble.application;

import assemble.domain.User;
import assemble.domain.UserRepository;
import assemble.dto.UserRegistrationData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService의")
class UserServiceTest {
    private final Long givenNewUserId = 77L;
    private final String givenNotExistedEmail = "newoo@codesoom.com";
    private final String givenExistedEmail = "existed@codesoom.com";
    private final String givenName = "newoo";
    private final String givenPassword = "password";

    private UserService userService;

    private final UserRepository userRepository = mock(UserRepository.class);

    private User user;

    @Nested
    @DisplayName("registerUser 메서드는")
    class Describe_registerUser {
        private UserRegistrationData registrationData;
        private String givenEmail;

        @BeforeEach
        void setUp() {
            Mapper mapper = DozerBeanMapperBuilder.buildDefault();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            userService = new UserService(
                    mapper, userRepository, passwordEncoder);

            given(userRepository.save(any(User.class))).will(invocation -> {
                User source = invocation.getArgument(0);
                return User.builder()
                        .id(givenNewUserId)
                        .email(source.getEmail())
                        .name(source.getName())
                        .build();
            });
        }

        private void subject() throws Exception {
            registrationData = UserRegistrationData.builder()
                    .email(givenEmail)
                    .name(givenName)
                    .password(givenPassword)
                    .build();

            user = userService.registerUser(registrationData);
        }

        @Nested
        @DisplayName("중복 이메일이 없다면")
        class Context_without_duplicated_email {
            @BeforeEach
            void setUp() throws Exception {
                givenEmail = givenNotExistedEmail;

                subject();
            }

            @Test
            @DisplayName("새로운 사용자 정보를 생성하고, 생성된 사용자 정보를 반환한다.")
            void it_create_new_user_and_returns_created_user() {
                assertThat(user.getId()).isEqualTo(givenNewUserId);
                assertThat(user.getEmail()).isEqualTo(givenNotExistedEmail);
                assertThat(user.getName()).isEqualTo(givenName);

                verify(userRepository).save(any(User.class));
            }
        }
    }
}
