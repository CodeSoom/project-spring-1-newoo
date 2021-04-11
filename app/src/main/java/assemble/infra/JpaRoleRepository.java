package assemble.infra;

import assemble.domain.Role;
import assemble.domain.RoleRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 사용자 권한 JPA 생성, 불러오기 인터페이스.
 */
public interface JpaRoleRepository
        extends RoleRepository, CrudRepository<Role, Long> {
    List<Role> findAllByUserId(Long userId);

    Role save(Role role);
}
