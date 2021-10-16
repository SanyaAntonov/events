package ru.antonov.events.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.antonov.events.model.Event;
import ru.antonov.events.repository.EventRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventControllerTest extends AbstractControllerTest {

    @Autowired
    private EventRepository eventRepository;
    private final int EVENT_ID = 1;
    private final Event event = new Event(
            EVENT_ID,
            "Как жить в новом ужасном мире (Со взглядом в прошлое)",
            "Огромный текст",
            LocalDateTime.of(2021, Month.MAY, 28, 14, 0),
            "Москва" ,
            "Какая-то улица 25",
            "Вадим Иванов",
            "Сергей Зырянов");

    @Test
    void getAllEvents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void getEvent() throws Exception {
        byte[] contentAsByteArray = mockMvc.perform(MockMvcRequestBuilders.get("/events/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        Event response = objectMapper.readValue(contentAsByteArray, Event.class);
        assertEquals(response.getId(), event.getId());
    }

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void createEvent() throws Exception {
        Event aNew = new Event(null,
                "Новое Мероприятие",
                "Описание",
                LocalDateTime.now().plusMonths(1).toLocalDate().atStartOfDay().withHour(15),
                "Екатеринбург" ,
                "Улица Пушкана, дом колотушкина",
                "Вася Пупкин",
                "Алексей Навальный");
        byte[] contentAsByteArray = mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(aNew)))
                        .andExpect(status().isCreated()).andReturn()
                        .getResponse()
                        .getContentAsByteArray();

        Event response = objectMapper.readValue(contentAsByteArray, Event.class);
        assertEquals(aNew.getDescription(), response.getDescription());
    }

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void updateEvent() throws Exception {
        Event updated = new Event(
                EVENT_ID,
                "Новое Мероприятие",
                "Описание",
                LocalDateTime.now().plusMonths(1).toLocalDate().atStartOfDay().withHour(15),
                "Екатеринбург" ,
                "Улица Пушкана, дом колотушкина",
                "Вася Пупкин",
                "Алексей Навальный");
        byte[] contentAsByteArray = mockMvc.perform(MockMvcRequestBuilders.put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updated)))
                        .andExpect(status().isNoContent())
                        .andReturn()
                        .getResponse()
                        .getContentAsByteArray();

        Event response = objectMapper.readValue(contentAsByteArray, Event.class);
        assertEquals(updated.getDescription(), response.getDescription());
    }

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void deleteEvent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/1"))
                .andExpect(status().isNoContent());
        assertFalse(eventRepository.findById(1).isPresent());
        assertTrue(eventRepository.findById(2).isPresent());
    }

    @Test
    void getSchedule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/events/schedule"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
    }
}