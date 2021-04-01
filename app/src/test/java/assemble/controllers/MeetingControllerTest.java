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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeetingController.class)
@AutoConfigureMockMvc
class MeetingControllerTest {
    private final Long givenSavedId = 1L;
    private final String givenName = "사당 iOS개발";
    private final String givenDescription = "iOS 개발 공부해요";
    private final Long givenOwnerId = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private MeetingService meetingService;

    private RequestBuilder requestBuilder;

    private Meeting meeting;

    private String meetingJsonString;
    private String meetingListJsonString;

    @BeforeEach
    void setUp() throws IOException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();

        meeting = Meeting.builder()
                .id(givenSavedId)
                .name(givenName)
                .description(givenDescription)
                .ownerId(givenOwnerId)
                .build();

        OutputStream outputStream = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, List.of(meeting));
        meetingListJsonString = outputStream.toString();

        outputStream = new ByteArrayOutputStream();
        objectMapper = new ObjectMapper();
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
            void it_responds_200_ok_and_not_empty_meeting_list() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json(meetingListJsonString));
            }
        }

        @Nested
        @DisplayName("저장된 모임이 없다면,")
        class Context_without_meeting {
            @Test
            @DisplayName("200 Ok와 비어있는 모임리스트를 응답한다.")
            void it_responds_200_ok_and_empty_meeting_list() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json("[]"));
            }
        }
    }

    @Nested
    @DisplayName("GET /meetings/{id} 요청은")
    class Describe_get_meetings_id_request {
        private Long givenId;

        private void subject() {
            requestBuilder = get("/meetings/{id}", givenId)
                    .accept(MediaType.APPLICATION_JSON);
        }

        @Nested
        @DisplayName("주어진 식별자에 해당하는 모임이 있다면")
        class Context_with_meeting_contain_given_id {
            @BeforeEach
            void setUp() {
                givenId = givenSavedId;

                subject();

                given(meetingService.getMeeting(any(Long.class)))
                        .willReturn(meeting);
            }

            @Test
            @DisplayName("200 OK와 주어진 식별자에 해당하는 모임을 응답한다.")
            void it_returns_200_ok_and_meeting_contain_given_id() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().string(meetingJsonString));
            }
        }
    }
}
