package assemble.controllers;

import assemble.application.UserService;
import assemble.domain.User;
import assemble.dto.UserRegistrationData;
import assemble.dto.UserResultData;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {
    private final Long givenNewUserId = 77L;
    private final String givenNotExistedEmail = "newoo@codesoom.com";
    private final String givenName = "newoo";
    private final String givenPassword = "password";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    private RequestBuilder requestBuilder;

    private User user;
    private UserResultData resultData;
    private UserRegistrationData registrationData;

    private String resultDataJsonString;
    private String registrationDataJsonString;
    private OutputStream outputStream;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_post_users_request {
        @BeforeEach
        void setUp() throws IOException {
            registrationData = UserRegistrationData.builder()
                    .name(givenName)
                    .email(givenNotExistedEmail)
                    .password(givenPassword)
                    .build();

            outputStream = new ByteArrayOutputStream();
            objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, registrationData);
            registrationDataJsonString = outputStream.toString();

            requestBuilder = post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(registrationDataJsonString);

            user = User.builder()
                    .id(givenNewUserId)
                    .email(givenNotExistedEmail)
                    .name(givenName)
                    .password(givenPassword)
                    .build();

            resultData = UserResultData.builder()
                    .id(givenNewUserId)
                    .email(givenNotExistedEmail)
                    .name(givenName)
                    .build();

            outputStream = new ByteArrayOutputStream();
            objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, resultData);
            resultDataJsonString = outputStream.toString();
        }

        @Test
        @DisplayName("사용자를 등록시키고, 동록된 사용자 정보를 반환한다.")
        void it_registers_user_and_returns_registered_user() throws Exception {
            given(userService.registerUser(any(UserRegistrationData.class)))
                    .willReturn(user);

            mockMvc.perform(requestBuilder)
                    .andExpect(status().isCreated())
                    .andExpect(content().json(resultDataJsonString));
        }
    }
}
