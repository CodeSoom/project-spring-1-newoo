package assemble.application;

import assemble.dto.MeetingData;
import assemble.errors.MeetingNotFoundException;
import assemble.domain.Meeting;
import assemble.domain.MeetingRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("MeetingService의")
class MeetingServiceTest {
    private final Long givenSavedId = 1L;
    private final Long givenUnsavedId = 1L;
    private final String givenName = "사당 iOS개발";
    private final String givenDescription = "iOS 개발 공부해요";
    private final Long givenOwnerId = 1L;

    private MeetingService meetingService;
    private MeetingRepository meetingRepository;
    private Meeting meeting;
    private MeetingData meetingData;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        meeting = Meeting.builder()
                .id(givenSavedId)
                .name(givenName)
                .description(givenDescription)
                .ownerId(givenOwnerId)
                .build();

        meetingData = MeetingData.builder()
                .id(givenSavedId)
                .name(givenName)
                .description(givenDescription)
                .ownerId(givenOwnerId)
                .build();

        meetingRepository = mock(MeetingRepository.class);
        meetingService = new MeetingService(mapper, meetingRepository);
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

    @Nested
    @DisplayName("getMeeting 메서드는")
    class Describe_getMeeting {
        private Long givenId;

        @Nested
        @DisplayName("저장되지 않은 모임의 식별자를 가지고 있다면")
        class Context_with_unsaved_meeting_identifier {
            @BeforeEach
            void setUp() {
                givenId = givenUnsavedId;
            }

            @Test
            @DisplayName("모임을 찾을 수 없다는 예외를 던진다.")
            void it_throws_meeting_not_found_exception() {
                assertThatThrownBy(
                        () -> meetingService.getMeeting(givenId),
                        "모임을 찾을 수 없다는 예외를 던져야 합니다."
                ).isInstanceOf(MeetingNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("저장된 모임의 식별자를 가지고 있다면")
        class Context_with_saved_meeting_identifier {
            @BeforeEach
            void setUp() {
                givenId = givenSavedId;

                given(meetingRepository.findById(givenSavedId)).willReturn(Optional.of(meeting));
            }

            @Test
            @DisplayName("모임을 반환한다.")
            void it_returns_a_meeting() {
                final Meeting found = meetingService.getMeeting(givenSavedId);
                assertThat(found.getName()).isEqualTo(givenName);
                assertThat(found.getDescription()).isEqualTo(givenDescription);
            }
        }
    }

    @Nested
    @DisplayName("createMeeting 메서드는")
    class Describe_createMeeting {
        @BeforeEach
        void setUp() {
            given(meetingRepository.save(any(Meeting.class))).will(invocation -> {
                Meeting source = invocation.getArgument(0);

                return Meeting.builder()
                        .id(givenSavedId)
                        .name(givenName)
                        .description(givenDescription)
                        .ownerId(givenOwnerId)
                        .build();
            });
        }

        @Test
        @DisplayName("모임을 생성하고, 생성된 모임을 반환한다.")
        void it_create_meeting_and_returns_created_meeting() {
            final Meeting created = meetingService.createMeeting(meetingData);
            assertThat(created.getName()).isEqualTo(givenName);
            assertThat(created.getDescription()).isEqualTo(givenDescription);

            verify(meetingRepository).save(any(Meeting.class));
        }
    }

    @Nested
    @DisplayName("updateMeeting 메서드는")
    class Describe_updateMeeting {
        private Long givenId;

        @Nested
        @DisplayName("저장되지 않은 모임의 식별자를 가지고 있다면")
        class Context_with_unsaved_meeting_identifier {
            @BeforeEach
            void setUp() {
                givenId = givenUnsavedId;
            }

            @Test
            @DisplayName("모임을 찾을 수 없다는 예외를 던진다.")
            void it_throws_meeting_not_found_exception() {
                assertThatThrownBy(
                        () -> meetingService.updateMeeting(givenId, meetingData),
                        "모임을 찾을 수 없다는 예외를 던져야 합니다."
                ).isInstanceOf(MeetingNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("저장된 모임의 식별자를 가지고 있다면")
        class Context_with_saved_meeting_identifier {
            @BeforeEach
            void setUp() {
                givenId = givenSavedId;

                given(meetingRepository.findById(givenSavedId)).willReturn(Optional.of(meeting));
            }

            @Test
            @DisplayName("모임을 수정하고, 수정된 모임을 반환한다.")
            void it_update_meeting_and_returns_updated_meeting() {
                final Meeting updated = meetingService.updateMeeting(givenSavedId, meetingData);
                assertThat(updated.getName()).isEqualTo(givenName);
                assertThat(updated.getDescription()).isEqualTo(givenDescription);
            }
        }
    }

    @Nested
    @DisplayName("deleteMeeting 메서드는")
    class Describe_deleteMeeting {
        private Long givenId;

        private Meeting subject() {
            return meetingService.deleteMeeting(givenId);
        }

        @Nested
        @DisplayName("저장된 모임의 식별자를 가지고 있다면")
        class Context_with_saved_meeting_identifier {
            @BeforeEach
            void setUp() {
                givenId = givenSavedId;

                given(meetingRepository.findById(givenId)).willReturn(Optional.of(meeting));
            }

            @Test
            @DisplayName("모임을 삭제하고, 삭제한 모임을 반환한다.")
            void it_delete_meeting_and_returns_deleted_meeting() {
                final Meeting deleted = subject();
                assertThat(deleted.getName()).isEqualTo(givenName);
                assertThat(deleted.getDescription()).isEqualTo(givenDescription);

                verify(meetingRepository).delete(any(Meeting.class));
            }
        }
    }
}
