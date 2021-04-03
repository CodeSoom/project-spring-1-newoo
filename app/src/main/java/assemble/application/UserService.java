package assemble.application;

import assemble.domain.User;
import assemble.dto.UserRegistrationData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 사용자 서비스.
 */
@Service
@Transactional
public class UserService {
    /**
     * 계정을 등록하고, 등록된 계정을 반환한다.
     *
     * @param registrationData 등록할 계정 데이터
     * @return 등록된 계정
     */
    public User registerUser(UserRegistrationData registrationData) {
        return null;
    }

    /**
     * 주어진 식별자에 해당하는 계정을 수정하고, 수정된 계정을 반환한다.
     *
     * @param id 수정할 계정의 식별자
     * @param modificationData 수정할 계정 데이터
     * @param userId 수정을 요청한 계정의 식별자
     * @return 수정된 계정
     */
    public User updateUser(Long id, UserModificationData modificationData,
                           Long userId) {
        return null;
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
        return null;
    }
}
