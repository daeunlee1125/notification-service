package kr.co.daeun.notification.controller;

import kr.co.daeun.notification.controller.NotificationApiController;
import kr.co.daeun.notification.exception.GlobalExceptionHandler;
import kr.co.daeun.notification.service.NotificationApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationApiController.class)
@Import(GlobalExceptionHandler.class)
class NotificationApiControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationApiService notificationApiService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNotification_invalidBody_returns400() throws Exception {
        String body = """
                {
                  "eventType": "",
                  "recipientKey": "",
                  "body": ""
                }
                """;

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors").isArray());
    }

    @Test
    void createNotification_invalidJson_returns400() throws Exception {
        String body = """
                {
                  "eventType": "ORDER_COMPLETED",
                  "channelType": "INVALID_ENUM"
                }
                """;

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"));
    }
}