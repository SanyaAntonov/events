package ru.antonov.events.util;

import lombok.experimental.UtilityClass;
import ru.antonov.events.model.Event;
import ru.antonov.events.to.EventTo;
import ru.antonov.events.to.MonthScheduleTo;
import ru.antonov.events.to.ScheduleElementTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class ScheduleUtil {
    public static List<MonthScheduleTo> getMonthScheduleTos(List<Event> allEvents) {

        List<MonthScheduleTo> months = new ArrayList<>();

        if (allEvents.isEmpty()) {
            return months;
        }

        for (Event event : allEvents) {
            if (months.isEmpty()) {
                createNewMonthScheduleTo(months, event);
            }
            List<MonthScheduleTo> months1 = new ArrayList<>(months);
            for (MonthScheduleTo monthScheduleTo : months1) {
                if (Objects.equals(MonthConverter.toMonth(monthScheduleTo.getMonth()),
                        MonthConverter.toMonth(EventUtil.getEventTo(event).getMonth()))) {

                    List<ScheduleElementTo> elements1 = new ArrayList<>(monthScheduleTo.getElements());
                    for (ScheduleElementTo elementTo : elements1) {
                        if (elementTo.getCityName().equals(event.getCity())) {
                            addToEventList(event, elementTo);
                        } else {
                            if (elements1.size() > 1 && elements1.indexOf(elementTo) != elements1.size() - 1)
                                continue;
                            createNewScheduleElementTo(event, monthScheduleTo);
                        }
                    }
                } else {
                    if (months1.size() > 1 && months1.indexOf(monthScheduleTo) != months1.size() - 1)
                        continue;
                    createNewMonthScheduleTo(months, event);
                }
            }
        }
        return months;
    }

    private static void addToEventList(Event event, ScheduleElementTo elementTo) {
        List<EventTo> eventList = elementTo.getEventList();
        eventList.add(EventUtil.getEventTo(event));
        elementTo.setEventList(eventList);
        elementTo.setNumberOfEvents(eventList.size());
    }

    private static void createNewScheduleElementTo(Event event, MonthScheduleTo monthScheduleTo) {
        ScheduleElementTo to = new ScheduleElementTo();
        to.setCityName(event.getCity());
        addToEventList(event, to);
        List<ScheduleElementTo> elements = monthScheduleTo.getElements();
        elements.add(to);
        monthScheduleTo.setElements(elements);
    }

    private static void createNewMonthScheduleTo(List<MonthScheduleTo> months, Event event) {
        MonthScheduleTo scheduleTo = new MonthScheduleTo();
        scheduleTo.setMonth(MonthConverter.toStringInSchedule(event.getDateTime().getMonth()));

        createNewScheduleElementTo(event, scheduleTo);

        months.add(scheduleTo);
    }
}
