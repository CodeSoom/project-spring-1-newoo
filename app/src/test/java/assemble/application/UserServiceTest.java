package assemble.application;

import assemble.domain.Role;
import assemble.domain.RoleRepository;
import assemble.domain.User;
import assemble.domain.UserRepository;
import assemble.dto.UserRegistrationData;
import assemble.errors.UserEmailDuplicationException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    private final RoleRepository roleRepository = mock(RoleRepository.class);

    private User user;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        userService = new UserService(
                mapper, userRepository, roleRepository, passwordEncoder);
    }

    @Nested
    @DisplayName("registerUser 메서드는")
    class Describe_registerUser {
        private UserRegistrationData registrationData;
        private String givenEmail;

        private void subject() {
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
            void setUp() {
                givenEmail = givenNotExistedEmail;

                given(userRepository.save(any(User.class))).will(invocation -> {
                    User source = invocation.getArgument(0);
                    return User.builder()
                            .id(givenNewUserId)
                            .email(source.getEmail())
                            .name(source.getName())
                            .build();
                });
            }

            @Test
            @DisplayName("새로운 사용자 정보를 생성하고, 생성된 사용자 정보를 반환한다.")
            void it_create_new_user_and_returns_created_user() throws Exception {
                subject();

                assertThat(user.getId()).isEqualTo(givenNewUserId);
                assertThat(user.getEmail()).isEqualTo(givenNotExistedEmail);
                assertThat(user.getName()).isEqualTo(givenName);

                verify(userRepository).save(any(User.class));
                verify(roleRepository).save(any(Role.class));
            }
        }

        @Nested
        @DisplayName("중복 이메일이 있다면")
        class Context_with_duplicated_email {
            @BeforeEach
            void setUp() throws Exception {
                givenEmail = givenExistedEmail;

                given(userRepository.existsByEmail(givenExistedEmail))
                        .willThrow(new UserEmailDuplicationException(
                                givenExistedEmail));
            }

            @Test
            @DisplayName("새로운 사용자 정보를 생성하고, 생성된 사용자 정보를 반환한다.")
            void it_create_new_user_and_returns_created_user() {
                assertThatThrownBy(() -> subject())
                        .isInstanceOf(UserEmailDuplicationException.class);

                verify(userRepository).existsByEmail(givenExistedEmail);
            }
        }
    }
}
