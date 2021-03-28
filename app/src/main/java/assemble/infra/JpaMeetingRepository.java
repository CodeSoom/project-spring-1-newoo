package assemble.infra;

import assemble.domain.Meeting;
import assemble.domain.MeetingRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * 모임 객체 JPA CRUD 인터페이스.
 */
public interface JpaMeetingRepository
        extends MeetingRepository, CrudRepository<Meeting, Long> {
    List<Meeting> findAll();

    Optional<Meeting> findById(Long id);

    Meeting save(Meeting product);

    void delete(Meeting product);
}
