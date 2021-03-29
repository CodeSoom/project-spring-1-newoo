package assemble.application;

import assemble.errors.MeetingNotFoundException;
import assemble.domain.Meeting;
import assemble.domain.MeetingRepository;
import assemble.dto.MeetingData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 모임 서비스.
 */
@Service
@Transactional
public class MeetingService {
    private final Mapper mapper;
    private final MeetingRepository meetingRepository;

    public MeetingService(Mapper dozerMapper,
                          MeetingRepository meetingRepository) {
        this.mapper = dozerMapper;
        this.meetingRepository = meetingRepository;
    }

    /**
     * 전체 모임들을 반환한다.
     *
     * @return 전체 모임들
     */
    public List<Meeting> getMeetings() {
        return meetingRepository.findAll();
    }

    /**
     * 주어진 식별자에 해당하는 모임을 반환한다.
     *
     * @param id 모임 식별자
     * @return 주어진 식별자에 해당하는 모임
     */
    public Meeting getMeeting(Long id) {
        return meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(id));
    }

    /**
     * 주어진 데이터로 모임을 생성하고, 생성한 모임을 반환한다.
     *
     * @param meetingData 생성할 모임 데이터
     * @return 생성된 모임
     */
    public Meeting createMeeting(MeetingData meetingData) {
        Meeting meeting = mapper.map(meetingData, Meeting.class);
        return meetingRepository.save(meeting);
    }

    /**
     * 주어진 식별자에 해당하는 모임을 주어진 데이터를 이용하여 수정하고,
     * 수정한 모임을 반환하다.
     *
     * @param id 수정할 모임 식별자
     * @param meetingData 수정할 모임 데이터
     * @return 수정한 모임
     */
    public Meeting updateMeeting(Long id, MeetingData meetingData) {
        Meeting meeting = getMeeting(id);

        meeting.changeWith(mapper.map(meetingData, Meeting.class));

        return meeting;
    }

    /**
     * 주어진 식별자에 해당하는 모임을 삭제하고, 삭제한 모임을 반환한다.
     *
     * @param id 삭제할 모임 식별자
     * @return 삭제한 모임
     */
    public Meeting deleteMeeting(Long id) {
        Meeting meeting = getMeeting(id);

        meetingRepository.delete(meeting);

        return meeting;
    }
}
