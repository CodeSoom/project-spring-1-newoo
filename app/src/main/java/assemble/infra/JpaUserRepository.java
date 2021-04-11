package assemble.infra;

import assemble.domain.User;
import assemble.domain.UserRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * 사용자 정보 객체 JPA CRUD 인터페이스.
 */
public interface JpaUserRepository
        extends UserRepository, CrudRepository<User, Long> {
    User save(User user);

    boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByIdAndDeletedIsFalse(Long id);

    Optional<User> findByEmail(String email);
}
