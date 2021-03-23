package assemble.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingTest {
    private final Long givenId = 1L;
    private final String givenName = "사당 iOS개발";
    private final String givenDescription = "iOS 개발 공부해요";
    private final Long givenOwnerId = 1L;
    private final String postfixText = "++";

    private Meeting meeting;

    @BeforeEach
    void setUp() {
        meeting = Meeting.builder()
                .id(givenId)
                .name(givenName)
                .description(givenDescription)
                .ownerId(givenOwnerId)
                .build();
    }

    @Test
    void creationWithBuilder() {
        assertThat(meeting.getId()).isEqualTo(givenId);
        assertThat(meeting.getName()).isEqualTo(givenName);
        assertThat(meeting.getDescription()).isEqualTo(givenDescription);
        assertThat(meeting.getImageUrl()).isNull();
        assertThat(meeting.getOwnerId()).isEqualTo(givenOwnerId);
    }

    @Test
    void changeWith() {
        meeting.changeWith(Meeting.builder()
                .name(givenName + postfixText)
                .description(givenDescription + postfixText)
                .build());

        assertThat(meeting.getName()).isEqualTo(givenName + postfixText);
        assertThat(meeting.getDescription())
                .isEqualTo(givenDescription + postfixText);
    }
}
