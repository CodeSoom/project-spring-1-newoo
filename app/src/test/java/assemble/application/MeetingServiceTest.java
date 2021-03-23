package assemble.application;

import assemble.domain.MeetingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DisplayName("MeetingService의")
class MeetingServiceTest {
    private MeetingService meetingService;
    private MeetingRepository meetingRepository;

    @BeforeEach
    void setUp() {
        meetingRepository = mock(MeetingRepository.class);
        meetingService = new MeetingService(meetingRepository);
    }

    @Nested
    @DisplayName("getMeetings 메서드는")
    class Describe_getMeetings {
        @Nested
        @DisplayName("저장된 모임이 없다면")
        class Context_without_any_meeting {
            @Test
            @DisplayName("비어있는 리스트를 반환한다.")
            void it_returns_empty_list() {
                assertThat(meetingService.getMeetings()).isEmpty();
            }
        }
    }
}
