package ru.antonov.events.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.antonov.events.EventTestUtil;
import ru.antonov.events.SubscriberTestUtil;
import ru.antonov.events.model.Subscriber;
import ru.antonov.events.repository.SubscriberRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.antonov.events.EventTestUtil.event;
import static ru.antonov.events.SubscriberTestUtil.*;
import static ru.antonov.events.util.JsonUtil.writeValue;

class SubscriberControllerTest extends AbstractControllerTest {

    @Autowired
    private SubscriberRepository repository;

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void getAllSubs() throws Exception {
        perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void getSub() throws Exception {
        perform(MockMvcRequestBuilders.get("/subscribers/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonMatcher(subscriber, SubscriberTestUtil::assertEquals));
    }

    @Test
    @WithUserDetails(value = "admin@gmail.com")
    void deleteSubscription() throws Exception {
        perform(MockMvcRequestBuilders.delete("/subscribers/1"))
                .andExpect(status().isNoContent());

        Assertions.assertFalse(repository.get(1).isPresent());
        Assertions.assertTrue(repository.get(2).isPresent());
    }

    @Test
    void createSubscription() throws Exception {
        Subscriber subscriber = getNew();
        perform(MockMvcRequestBuilders.post("/events/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(subscriber)))
                .andExpect(status().isCreated())
                .andExpect(jsonMatcher(subscriber, SubscriberTestUtil::assertNoIdEquals));
    }

    @Test
    void createSubscriptionInSchedule() throws Exception {
        Subscriber subscriber = getNew();
        perform(MockMvcRequestBuilders.post("/events/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(subscriber)))
                .andExpect(status().isCreated())
                .andExpect(jsonMatcher(subscriber, SubscriberTestUtil::assertNoIdEquals));
    }
}