package ru.antonov.events.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.antonov.events.model.Event;
import ru.antonov.events.to.EventTo;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventUtil {

    public static EventTo getEventTo(Event event) {
        EventTo eventTo = new EventTo();
        LocalDateTime dateTime = event.getDateTime();

        eventTo.setId(event.id());
        eventTo.setCity(event.getCity());
        eventTo.setMonth(MonthConverter.toStringInTo(dateTime.getMonth()));
        eventTo.setDayOfMonth(dateTime.getDayOfMonth());
        eventTo.setDateTime(dateTime.toLocalTime());
        eventTo.setYear(dateTime.getYear());
        eventTo.setAddress(event.getAddress());
        eventTo.setSpeaker1(event.getSpeaker1());
        eventTo.setSpeaker2(event.getSpeaker2());

        return eventTo;
    }
}
