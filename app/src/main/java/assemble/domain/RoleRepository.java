package assemble.domain;

import java.util.List;

/**
 * 사용자 권한 생성, 불러오기 인터페이스.
 */
public interface RoleRepository {
    List<Role> findAllByUserId(Long userId);

    Role save(Role role);
}
