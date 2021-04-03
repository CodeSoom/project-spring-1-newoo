package assemble.application;

import assemble.domain.Role;
import assemble.domain.RoleRepository;
import assemble.domain.User;
import assemble.domain.UserRepository;
import assemble.dto.UserModificationData;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService의")
class UserServiceTest {
    private final Long givenNewUserId = 77L;
    private final Long givenExistedUserId = 7L;
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

    @Nested
    @DisplayName("updateUser 메서드는")
    class Describe_updateUser {
        private UserModificationData modificationData;
        private Long givenId;
        private String givenUpdatedName = givenName + "+";

        @Nested
        @DisplayName("저장된 사용자의 식별자를 가지고 있다면")
        class Context_with_saved_user_identifier {
            @BeforeEach
            void setUp() {
                givenId = givenExistedUserId;

                given(userRepository.findByIdAndDeletedIsFalse(givenExistedUserId))
                        .willReturn(Optional.of(
                                User.builder()
                                        .id(givenExistedUserId)
                                        .email(givenExistedEmail)
                                        .name(givenName)
                                        .password(givenPassword)
                                        .build()));
            }

            @Test
            @DisplayName("사용자 정보를 수정하고, 수정된 사용자 정보를 반환한다.")
            void it_update_user_and_returns_updated_user() {
                modificationData = UserModificationData.builder()
                        .name(givenUpdatedName)
                        .password(givenPassword)
                        .build();

                User user = userService.updateUser(givenId, modificationData, givenId);

                assertThat(user.getId()).isEqualTo(givenId);
                assertThat(user.getEmail()).isEqualTo(givenExistedEmail);
                assertThat(user.getName()).isEqualTo(givenUpdatedName);

                verify(userRepository).findByIdAndDeletedIsFalse(givenId);
            }
        }
    }
}
