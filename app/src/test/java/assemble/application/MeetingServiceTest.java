package assemble.application;

import assemble.domain.Meeting;
import assemble.domain.MeetingRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("MeetingService의")
class MeetingServiceTest {
    private final Long givenId = 1L;
    private final String givenName = "사당 iOS개발";
    private final String givenDescription = "iOS 개발 공부해요";
    private final Long givenOwnerId = 1L;

    private MeetingService meetingService;
    private MeetingRepository meetingRepository;
    private Meeting meeting;

    @BeforeEach
    void setUp() {
        meeting = Meeting.builder()
                .id(givenId)
                .name(givenName)
                .description(givenDescription)
                .ownerId(givenOwnerId)
                .build();

        meetingRepository = mock(MeetingRepository.class);
        meetingService = new MeetingService(meetingRepository);
    }

    @Nested
    @DisplayName("getMeetings 메서드는")
    class Describe_getMeetings {
        @Nested
        @DisplayName("저장된 모임이 없다면")
        class Context_without_any_meeting {
            @BeforeEach
            void setUp() {
                given(meetingRepository.findAll()).willReturn(List.of());
            }

            @Test
            @DisplayName("비어있는 리스트를 반환한다.")
            void it_returns_empty_list() {
                assertThat(meetingService.getMeetings()).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 모임이 있다면")
        class Context_with_meeting {
            private List<Meeting> givenMeetings;

            @BeforeEach
            void setUp() {
                givenMeetings = List.of(meeting);

                given(meetingRepository.findAll()).willReturn(givenMeetings);
            }

            @Test
            @DisplayName("모임 리스트를 반환한다.")
            void it_returns_meeting_list() {
                assertThat(meetingService.getMeetings()).isEqualTo(givenMeetings);

                verify(meetingRepository).findAll();
            }
        }
    }
}
