package assemble.application;

import assemble.domain.Meeting;
import assemble.domain.MeetingRepository;
import assemble.dto.MeetingData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 모임 서비스.
 *
 * @author newoo (newoo4297@naver.com)
 */
@Service
@Transactional
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    /**
     * 전체 모임들을 반환한다.
     *
     * @return 전체 모임들.
     */
    public List<Meeting> getMeetings() {
        return new ArrayList<Meeting>();
    }

    /**
     * 주어진 식별자에 해당하는 모임을 반환한다.
     *
     * @param id 모임 식별자.
     * @return 주어진 식별자에 해당하는 모임.
     */
    public Meeting getMeeting(Long id) {
        return null;
    }

    /**
     * 주어진 데이터로 모임을 생성하고, 생성한 모임을 반환한다.
     *
     * @param meetingData 생성할 모임 데이터.
     * @return 생성된 모임.
     */
    public Meeting createMeeting(MeetingData meetingData) {
        return null;
    }

    /**
     * 주어진 식별자에 해당하는 모임을 주어진 데이터를 이용하여 수정하고,
     * 수정한 모임을 반환하다.
     *
     * @param id 수정할 모임 식별자.
     * @param meetingData 수정할 모임 데이터.
     * @return 수정한 모임.
     */
    public Meeting updateMeeting(Long id, MeetingData meetingData) {
        return null;
    }

    /**
     * 주어진 식별자에 해당하는 모임을 삭제하고, 삭제한 모임을 반환한다.
     *
     * @param id 삭제할 모임 식별자.
     * @return 삭제한 모임.
     */
    public Meeting deleteMeeting(Long id) {
        return null;
    }
}
