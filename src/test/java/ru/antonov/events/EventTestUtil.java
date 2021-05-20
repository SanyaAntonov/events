package ru.antonov.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.antonov.events.model.Event;
import ru.antonov.events.util.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTestUtil {
    public static final int EVENT_ID = 1;
    public static final Event event = new Event(
            EVENT_ID,
            "Как жить в новом ужасном мире (Со взглядом в прошлое)",
            "Огромный текст",
            LocalDateTime.of(2021, Month.MAY, 28, 14, 0),
            "Москва" ,
            "Какая-то улица 25",
            "Вадим Иванов",
            "Сергей Зырянов");

    public static Event getNew() {
        return new Event(null,
                "Новое Мероприятие",
                "Описание",
                LocalDateTime.now().plusMonths(1).toLocalDate().atStartOfDay().withHour(15),
                "Екатеринбург" ,
                "Улица Пушкана, дом колотушкина",
                "Вася Пупкин",
                "Алексей Навальный");
    }

    public static Event getUpdated() {
        return new Event(
                EVENT_ID,
                "Новое Мероприятие",
                "Описание",
                LocalDateTime.now().plusMonths(1).toLocalDate().atStartOfDay().withHour(15),
                "Екатеринбург" ,
                "Улица Пушкана, дом колотушкина",
                "Вася Пупкин",
                "Алексей Навальный");
    }

    public static void assertEquals(Event actual, Event expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertNoIdEquals(Event actual, Event expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    public static Event asEvent(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, Event.class);
    }

    public static ResultMatcher jsonMatcher(Event expected, BiConsumer<Event, Event> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asEvent(mvcResult), expected);
    }
}
