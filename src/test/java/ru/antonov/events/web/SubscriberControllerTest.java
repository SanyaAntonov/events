package ru.antonov.events.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.antonov.events.model.Event;
import ru.antonov.events.model.Subscriber;
import ru.antonov.events.repository.SubscriberRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SubscriberControllerTest extends AbstractControllerTest {

    @Autowired
    private SubscriberRepository subscriberRepository;

    private final Subscriber subscriber = new Subscriber(1, "user@gmail.com");

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void getAllSubs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/subscribers"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void getSub() throws Exception {
        byte[] contentAsByteArray = mockMvc.perform(MockMvcRequestBuilders.get("/subscribers/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        Subscriber response = objectMapper.readValue(contentAsByteArray, Subscriber.class);
        assertEquals(response.getEmail(), subscriber.getEmail());
    }

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void deleteSubscription() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/subscribers/1"))
                .andExpect(status().isNoContent());

        Assertions.assertFalse(subscriberRepository.findById(1).isPresent());
        Assertions.assertTrue(subscriberRepository.findById(2).isPresent());
    }

    @Test
    void createSubscription() throws Exception {
        Subscriber subscriber = new Subscriber(null, "email123@mail.ru");
        byte[] contentAsByteArray = mockMvc.perform(MockMvcRequestBuilders.post("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(subscriber)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        Subscriber response = objectMapper.readValue(contentAsByteArray, Subscriber.class);
        assertEquals(response.getEmail(), subscriber.getEmail());
    }

    @Test
    void createSubscriptionInSchedule() throws Exception {
        Subscriber subscriber = new Subscriber(null, "email123@mail.ru");
        byte[] contentAsByteArray = mockMvc.perform(MockMvcRequestBuilders.post("/events/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(subscriber)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        Subscriber response = objectMapper.readValue(contentAsByteArray, Subscriber.class);
        assertEquals(response.getEmail(), subscriber.getEmail());
    }
}