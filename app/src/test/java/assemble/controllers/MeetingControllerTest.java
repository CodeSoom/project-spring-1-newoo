package assemble.controllers;

import assemble.application.MeetingService;
import assemble.domain.Meeting;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MeetingControllerTest {
    private final Long givenSavedId = 1L;
    private final String givenName = "사당 iOS개발";
    private final String givenDescription = "iOS 개발 공부해요";
    private final Long givenOwnerId = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeetingService meetingService;

    private RequestBuilder requestBuilder;

    private Meeting meeting;

    private String meetingJsonString;

    @BeforeEach
    void setUp() throws IOException {
        meeting = Meeting.builder()
                .id(givenSavedId)
                .name(givenName)
                .description(givenDescription)
                .ownerId(givenOwnerId)
                .build();

        OutputStream outputStream = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, meeting);
        meetingJsonString = outputStream.toString();
    }

    @Nested
    @DisplayName("GET /meetings 요청은")
    class Describe_get_meetings_request {
        @BeforeEach
        void setUp() {
            requestBuilder = get("/meetings");
        }

        @Nested
        @DisplayName("저장된 모임이 있다면,")
        class Context_with_meeting {
            @BeforeEach
            void setUp() {
                given(meetingService.getMeetings()).willReturn(List.of(meeting));
            }

            @Test
            @DisplayName("200 Ok와 비어있지 않은 모임리스트를 응답한다.")
            void it_responds_200_ok_and_not_empty_list() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json(meetingJsonString));
            }
        }
    }
}
