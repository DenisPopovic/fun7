package com.outfit7.fun7.controller.user;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfit7.fun7.model.ActivisionStatus;
import com.outfit7.fun7.model.dto.request.AvailableServicesRequestDto;
import com.outfit7.fun7.model.dto.response.AvailableServicesResponseDto;
import com.outfit7.fun7.persistence.Fun7UserPersistenceService;
import com.outfit7.fun7.service.user.AvailableServicesService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AvailableServicesController.class)
@Import(AvailableServicesController.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {Fun7UserPersistenceService.class})
class AvailableServicesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AvailableServicesService availableServicesService;

    private String token;
    private AvailableServicesRequestDto availableServicesRequestDto;
    private AvailableServicesResponseDto availableServicesResponseDto;

    @BeforeEach
    public void init() {
        token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW5pcy5wb3BvdmljQGh5Y3UuY29tIiwiZXhwIjoxNjk0OTA5OTIzLCJpYXQiOjE2OTQ4NzM5MjMsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dfQ._W1wVt700XSQbIKZUu6uCk56-gFofgKjPLfsxyIEidQ";

        availableServicesRequestDto = new AvailableServicesRequestDto();
        availableServicesRequestDto.setCc("US");
        availableServicesRequestDto.setTimezone("GMT-6");
        availableServicesRequestDto.setUserId(UUID.randomUUID().toString());

        availableServicesResponseDto = new AvailableServicesResponseDto();
        availableServicesResponseDto.setAds(ActivisionStatus.DISABLED.getState());
        availableServicesResponseDto.setMultiplayer(ActivisionStatus.DISABLED.getState());
        availableServicesResponseDto.setUserSupport(ActivisionStatus.DISABLED.getState());
    }

    @Test
    void getAvailableServicesStatuses() throws  Exception{
        // given
        given(availableServicesService.getAvailableServices(token, availableServicesRequestDto)).willReturn(availableServicesResponseDto);

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/check-services")
                        .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(availableServicesRequestDto)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}