package org.dukdns.kuma04.springrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dukdns.kuma04.springrestapi.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @TestDescription("정상적으로 이벤트를 생성하는 테스트")
    public void createEvent() throws Exception{
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API")
                .beginEnrollmentDateTime(LocalDateTime.of(2020,11,24,00,28))
                .closeEnrollmentDateTime(LocalDateTime.of(2020,11,25,00,28))
                .beginEventDateTime(LocalDateTime.of(2020,11,25,21,28))
                .endEventDateTime(LocalDateTime.of(2020,11,26,21,28))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("패스트파이브")
                .build();

        mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));

    }

    @Test
    @TestDescription("입력 받을 수 없는 값이 사용한 경우에 에러가 발생")
    public void createEvent_Bad_Requst() throws Exception{
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API")
                .beginEnrollmentDateTime(LocalDateTime.of(2020,11,24,00,28))
                .closeEnrollmentDateTime(LocalDateTime.of(2020,11,25,00,28))
                .beginEventDateTime(LocalDateTime.of(2020,11,25,21,28))
                .endEventDateTime(LocalDateTime.of(2020,11,26,21,28))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("패스트파이브")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @TestDescription("입력 값이 비어가 있는 경우에 에러가 발생")
    public void createEvent_Bad_Request_Empty_Input() throws Exception{
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(eventDto))
                    )
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("입력 값이 잘못된 경우에 에러가 발생")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception{
        EventDto eventDto = EventDto.builder()
                .name("kimkuma")
                .description("REST API Develop")
                .beginEnrollmentDateTime(LocalDateTime.of(2020,11,26,00,28))
                .closeEnrollmentDateTime(LocalDateTime.of(2020,11,25,00,28))
                .beginEventDateTime(LocalDateTime.of(2020,11,24,21,28))
                .endEventDateTime(LocalDateTime.of(2020,11,23,21,28))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("패스트파이브")
                .build();

        this.mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(eventDto))
                    )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
                .andExpect(jsonPath("$[0].rejectedValue").exists());
    }
}
