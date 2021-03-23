package assemble.domain;

import java.util.List;
import java.util.Optional;

/**
 * 모임 객체 CRUD 인터페이스.
 */
public interface MeetingRepository {
    List<Meeting> findAll();

    Optional<Meeting> findById(Long id);

    Meeting save(Meeting product);

    void delete(Meeting product);
}
