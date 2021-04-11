package assemble.application;

import assemble.domain.Role;
import assemble.domain.RoleRepository;
import assemble.domain.User;
import assemble.domain.UserRepository;
import assemble.dto.UserModificationData;
import assemble.dto.UserRegistrationData;
import assemble.errors.UserEmailDuplicationException;
import assemble.errors.UserNotFoundException;
import com.github.dozermapper.core.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 사용자 서비스.
 */
@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(Mapper mapper,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 계정을 등록하고, 등록된 계정을 반환한다.
     *
     * @param registrationData 등록할 계정 데이터
     * @return 등록된 계정
     * @throws UserEmailDuplicationException 사용자의 이메일이 이미 존재할 경우
     */
    public User registerUser(UserRegistrationData registrationData) {
        String email = registrationData.getEmail();

        // 이메일 중복 체크
        if (userRepository.existsByEmail(email)) {
            throw new UserEmailDuplicationException(email);
        }

        User user = userRepository.save(
                mapper.map(registrationData, User.class));

        user.changePassword(registrationData.getPassword(), passwordEncoder);

        roleRepository.save(new Role(user.getId(), "USER"));

        return user;
    }

    /**
     * 주어진 식별자에 해당하는 계정을 수정하고, 수정된 계정을 반환한다.
     *
     * @param id 수정할 계정의 식별자
     * @param modificationData 수정할 계정 데이터
     * @param userId 수정을 요청한 계정의 식별자
     * @return 수정된 계정
     */
    public User updateUser(Long id,
                           UserModificationData modificationData,
                           Long userId) {
        User user = findUser(id);

        User source = mapper.map(modificationData, User.class);
        user.changeWith(source);

        return user;
    }

    /**
     * 주어진 식별자에 해당하는 계정을 삭제하고, 삭제된 계정을 반환한다.
     *
     * @param id 삭제할 계정의 식별자
     * @return 삭제된 계정
     */
    public User deleteUser(Long id) {
        return null;
    }

    /**
     * 주어진 식별자로 계정을 찾고, 찾은 계정을 반환한다.
     *
     * @param id 찾을 계정의 식별자
     * @return 찾은 계정
     */
    private User findUser(Long id) {
        return userRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
