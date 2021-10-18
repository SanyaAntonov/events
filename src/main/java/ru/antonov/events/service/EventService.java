package ru.antonov.events.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.antonov.events.model.Event;
import ru.antonov.events.dto.MonthScheduleTo;
import ru.antonov.events.repository.EventRepository;
import ru.antonov.events.util.ScheduleUtil;
import ru.antonov.events.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event get(int id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found"));
    }

    @Cacheable("event")
    public List<Event> getAll(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return eventRepository.findAllByDateTimeBetweenOrderByDateTime(startDateTime, endDateTime)
                .orElseThrow(() -> new NotFoundException("Events not found"));
    }

    @Cacheable("eventsSchedule")
    public List<MonthScheduleTo> getEventsForTwoMonths(LocalDateTime startOfFirstMonth,
                                                       LocalDateTime endOfNextMonth) {
        List<Event> allEvents = eventRepository.findAllByDateTimeBetweenOrderByDateTime(startOfFirstMonth, endOfNextMonth)
                .orElseThrow(() -> new NotFoundException("Events not found"));

        return ScheduleUtil.getMonthScheduleTos(allEvents);
    }

    @CacheEvict(cacheNames = {"event", "eventsSchedule"}, allEntries = true)
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @CacheEvict(cacheNames = {"event", "eventsSchedule"}, allEntries = true)
    public void delete(int id) {
        eventRepository.deleteById(id);
    }

    //    Для истории проведенных мероприятий
    /*@Cacheable("AllEventsHistory")
    public List<MonthScheduleTo> getArchive() {
        List<Event> allEvents = repository.findAll();
        return ScheduleUtil.getMonthScheduleTos(allEvents);
    }*/
}
