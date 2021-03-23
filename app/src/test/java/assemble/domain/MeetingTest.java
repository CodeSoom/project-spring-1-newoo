package assemble.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingTest {
    private final Long givenId = 1L;
    private final String givenName = "사당 iOS개발";
    private final String givenDescription = "iOS 개발 공부해요";
    private final Long givenOwnerId = 1L;

    @Test
    void creationWithBuilder() {
        Meeting meeting = Meeting.builder()
                .id(givenId)
                .name(givenName)
                .description(givenDescription)
                .ownerId(givenOwnerId)
                .build();

        assertThat(meeting.getId()).isEqualTo(givenId);
        assertThat(meeting.getName()).isEqualTo(givenName);
        assertThat(meeting.getDescription()).isEqualTo(givenDescription);
        assertThat(meeting.getImageUrl()).isNull();
        assertThat(meeting.getOwnerId()).isEqualTo(givenOwnerId);
    }
}
