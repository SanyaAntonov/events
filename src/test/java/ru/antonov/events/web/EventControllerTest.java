package ru.antonov.events.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.antonov.events.EventTestUtil;
import ru.antonov.events.model.Event;
import ru.antonov.events.repository.EventRepository;
import ru.antonov.events.util.exception.NotFoundException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.antonov.events.EventTestUtil.*;
import static ru.antonov.events.util.JsonUtil.writeValue;

class EventControllerTest extends AbstractControllerTest {

    @Autowired
    private EventRepository repository;

    @Test
    void getAllEvents() throws Exception {
        perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void getEvent() throws Exception {
        perform(MockMvcRequestBuilders.get("/events/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonMatcher(event, EventTestUtil::assertEquals));
    }

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void createEvent() throws Exception {
        Event aNew = getNew();
        perform(MockMvcRequestBuilders.post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(aNew)))
                .andExpect(status().isCreated())
                .andExpect(jsonMatcher(aNew, EventTestUtil::assertNoIdEquals));
    }

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void updateEvent() throws Exception {
        Event updated = getUpdated();
        perform(MockMvcRequestBuilders.put("/events/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andExpect(status().isNoContent());

        EventTestUtil.assertEquals(updated, repository.get(1).orElseThrow(() -> new NotFoundException("Event not found")));
    }

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void deleteEvent() throws Exception {
        perform(MockMvcRequestBuilders.delete("/events/1"))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(repository.get(1).isPresent());
        Assertions.assertTrue(repository.get(2).isPresent());
    }

    @Test
    void getSchedule() throws Exception {
        perform(MockMvcRequestBuilders.get("/events/schedule"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
    }
}