package assemble.infra;

import assemble.domain.Meeting;
import assemble.domain.MeetingRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JpaMeetingRepository
        extends MeetingRepository, CrudRepository<Meeting, Long> {
    List<Meeting> findAll();

    Optional<Meeting> findById(Long id);

    Meeting save(Meeting product);

    void delete(Meeting product);
}
