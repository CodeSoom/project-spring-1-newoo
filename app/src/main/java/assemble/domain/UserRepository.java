package assemble.domain;

import java.util.Optional;

/**
 * 사용자 정보 객체 CRUD 인터페이스.
 */
public interface UserRepository {
    User save(User user);

    boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByIdAndDeletedIsFalse(Long id);

    Optional<User> findByEmail(String email);
}
