package ru.antonov.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.antonov.events.model.Subscriber;
import ru.antonov.events.util.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriberTestUtil {
    public static final Subscriber subscriber = new Subscriber(1, "user@gmail.com");

    public static Subscriber getNew() {
        return new Subscriber(null, "email123@mail.ru");
    }

    public static void assertEquals(Subscriber actual, Subscriber expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertNoIdEquals(Subscriber actual, Subscriber expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    public static Subscriber asSubscriber(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, Subscriber.class);
    }

    public static ResultMatcher jsonMatcher(Subscriber expected, BiConsumer<Subscriber, Subscriber> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asSubscriber(mvcResult), expected);
    }
}
