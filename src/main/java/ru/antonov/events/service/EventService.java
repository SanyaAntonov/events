package ru.antonov.events.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.antonov.events.model.Event;
import ru.antonov.events.repository.EventRepository;
import ru.antonov.events.to.MonthScheduleTo;
import ru.antonov.events.util.ScheduleUtil;
import ru.antonov.events.util.ValidationUtil;
import ru.antonov.events.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.antonov.events.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository repository;

    public Event get(int id) {
        return checkNotFoundWithId(repository.get(id)
                .orElseThrow(() -> new NotFoundException("Event not found")), id);
    }

    @Cacheable("event")
    public List<Event> getAll(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return repository.getEventsForTwoMonths(startDateTime, endDateTime)
                .orElseThrow(() -> new NotFoundException("Events not found"));
    }

    @Cacheable("eventsSchedule")
    public List<MonthScheduleTo> getEventsForTwoMonths(LocalDateTime startOfFirstMonth,
                                                       LocalDateTime endOfNextMonth) {
        List<Event> allEvents = repository.getEventsForTwoMonths(startOfFirstMonth, endOfNextMonth)
                .orElseThrow(() -> new NotFoundException("Events not found"));

        return ScheduleUtil.getMonthScheduleTos(allEvents);
    }

    @CacheEvict(cacheNames = {"event", "eventsSchedule"}, allEntries = true)
    public Event create(Event event) {
        ValidationUtil.checkNew(event);
        Assert.notNull(event, "event must not be null");
        return repository.save(event);
    }

    @CacheEvict(cacheNames = {"event", "eventsSchedule"}, allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @CacheEvict(cacheNames = {"event", "eventsSchedule"}, allEntries = true)
    public void update(Event event) {
        Assert.notNull(event, "event must not be null");
        checkNotFoundWithId(repository.save(event), event.id());
    }

    //    Для истории проведенных мероприятий
    /*@Cacheable("AllEventsHistory")
    public List<MonthScheduleTo> getArchive() {
        List<Event> allEvents = repository.getAll();
        return ScheduleUtil.getMonthScheduleTos(allEvents);
    }*/
}
