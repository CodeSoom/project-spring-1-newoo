package assemble.controllers;

import assemble.application.MeetingService;
import assemble.domain.Meeting;
import assemble.dto.MeetingData;
import assemble.errors.MeetingNotFoundException;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeetingController.class)
@AutoConfigureMockMvc
class MeetingControllerTest {
    private final Long givenSavedId = 1L;
    private final Long givenUnsavedId = 100L;
    private final String givenName = "?????? iOS??????";
    private final String givenDescription = "iOS ?????? ????????????";
    private final Long givenOwnerId = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private MeetingService meetingService;

    private RequestBuilder requestBuilder;

    private Meeting meeting;
    private MeetingData meetingData;

    private String meetingJsonString;
    private String meetingListJsonString;
    private String meetingDataJsonString;

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

        meetingData = MeetingData.builder()
                .name(givenName + "+")
                .description(givenDescription + "+")
                .build();

        OutputStream outputStream = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, List.of(meeting));
        meetingListJsonString = outputStream.toString();

        outputStream = new ByteArrayOutputStream();
        objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, meeting);
        meetingJsonString = outputStream.toString();

        outputStream = new ByteArrayOutputStream();
        objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, meetingData);
        meetingDataJsonString = outputStream.toString();
    }

    @Nested
    @DisplayName("GET /meetings ?????????")
    class Describe_get_meetings_request {
        @BeforeEach
        void setUp() {
            requestBuilder = get("/meetings");
        }

        @Nested
        @DisplayName("????????? ????????? ?????????,")
        class Context_with_meeting {
            @BeforeEach
            void setUp() {
                given(meetingService.getMeetings()).willReturn(List.of(meeting));
            }

            @Test
            @DisplayName("200 Ok??? ???????????? ?????? ?????????????????? ????????????.")
            void it_responds_200_ok_and_not_empty_meeting_list() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json(meetingListJsonString));
            }
        }

        @Nested
        @DisplayName("????????? ????????? ?????????,")
        class Context_without_meeting {
            @Test
            @DisplayName("200 Ok??? ???????????? ?????????????????? ????????????.")
            void it_responds_200_ok_and_empty_meeting_list() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json("[]"));
            }
        }
    }

    @Nested
    @DisplayName("GET /meetings/{id} ?????????")
    class Describe_get_meetings_id_request {
        private Long givenId;

        private void subject() {
            requestBuilder = get("/meetings/{id}", givenId)
                    .accept(MediaType.APPLICATION_JSON);
        }

        @Nested
        @DisplayName("????????? ???????????? ???????????? ????????? ?????????")
        class Context_with_meeting_contain_given_id {
            @BeforeEach
            void setUp() {
                givenId = givenSavedId;

                subject();

                given(meetingService.getMeeting(givenId))
                        .willReturn(meeting);
            }

            @Test
            @DisplayName("200 OK??? ????????? ???????????? ???????????? ????????? ????????????.")
            void it_returns_200_ok_and_meeting_contain_given_id() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json(meetingJsonString));
            }
        }

        @Nested
        @DisplayName("????????? ???????????? ???????????? ????????? ?????????")
        class Context_without_meeting_contain_given_id {
            @BeforeEach
            void setUp() {
                givenId = givenUnsavedId;

                subject();

                given(meetingService.getMeeting(givenId))
                        .willThrow(new MeetingNotFoundException(givenId));
            }

            @Test
            @DisplayName("404 Not Found??? ????????????.")
            void it_returns_404_not_found() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("POST /meetings ?????????")
    class Describe_post_meetings_request {
        @BeforeEach
        void setUp() {
            requestBuilder = post("/meetings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(meetingDataJsonString);
        }

        @Test
        @DisplayName("????????? ????????????, 201 Created??? ????????? ????????? ????????????.")
        void it_add_meeting_and_respond_201_created_and_added_meeting() throws Exception {
            given(meetingService.createMeeting(any(MeetingData.class)))
                    .willReturn(meeting);

            mockMvc.perform(requestBuilder)
                    .andExpect(status().isCreated())
                    .andExpect(content().json(meetingJsonString));
        }
    }

    @Nested
    @DisplayName("PATCH /meetings/{id} ?????????")
    class Describe_patch_meetings_id_request {
        private Long givenId;

        private void subject() {
            requestBuilder = patch("/meetings/{id}", givenId)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(meetingJsonString);
        }

        @Nested
        @DisplayName("????????? ???????????? ???????????? ????????? ?????????")
        class Context_with_meeting_contain_given_id {
            @BeforeEach
            void setUp() {
                givenId = givenSavedId;

                subject();

                given(meetingService.updateMeeting(eq(givenId), any(MeetingData.class)))
                        .will(invocation -> {
                            Long id = invocation.getArgument(0);
                            MeetingData meetingData = invocation.getArgument(1);
                            return Meeting.builder()
                                    .id(id)
                                    .name(meetingData.getName())
                                    .description(meetingData.getDescription())
                                    .ownerId(givenOwnerId)
                                    .build();
                        });
            }

            @Test
            @DisplayName("????????? ????????????, 200 Ok??? ????????? ????????? ????????????.")
            void it_updates_meeting_and_responds_200_ok_and_updated_meeting() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json(meetingJsonString));
            }
        }

        @Nested
        @DisplayName("????????? ???????????? ???????????? ????????? ?????????")
        class Context_without_meeting_contain_given_id {
            @BeforeEach
            void setUp() {
                givenId = givenUnsavedId;

                subject();

                given(meetingService.updateMeeting(eq(givenId), any(MeetingData.class)))
                        .willThrow(new MeetingNotFoundException(givenId));
            }

            @Test
            @DisplayName("404 Not Found??? ????????????.")
            void it_respond_404_not_found() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /meetings/{id} ?????????")
    class Describe_delete_meetings_id_request {
        private Long givenId;

        private void subject() {
            requestBuilder = delete("/meetings/{id}", givenId);
        }

        @Nested
        @DisplayName("????????? ???????????? ???????????? ????????? ?????????")
        class Context_with_meeting_contain_given_id {
            @BeforeEach
            void setUp() {
                givenId = givenSavedId;

                subject();

                given(meetingService.deleteMeeting(givenId))
                        .willReturn(meeting);
            }

            @Test
            @DisplayName("????????? ??????????????? ????????? 204 No Content??? ????????????.")
            void it_responds_204_no_content() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isNoContent());

                verify(meetingService).deleteMeeting(givenId);
            }
        }

        @Nested
        @DisplayName("????????? ???????????? ???????????? ????????? ?????????")
        class Context_without_meeting_contain_given_id {
            @BeforeEach
            void setUp() {
                givenId = givenUnsavedId;

                subject();

                given(meetingService.deleteMeeting(givenId.longValue()))
                        .willThrow(new MeetingNotFoundException(givenId));
            }

            @Test
            @DisplayName("404 Not Found??? ????????????.")
            void it_respond_404_not_found() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isNotFound());
            }
        }
    }
}
