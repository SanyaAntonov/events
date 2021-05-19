package ru.antonov.events.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.antonov.events.model.Event;
import ru.antonov.events.repository.EventRepository;
import ru.antonov.events.to.EventTo;
import ru.antonov.events.to.MonthScheduleTo;
import ru.antonov.events.to.ScheduleElementTo;
import ru.antonov.events.util.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.antonov.events.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository repository;

    public Event get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("event")
    public List<Event> getAll() {
        return repository.getAll();
    }

    @Cacheable("event")
    public List<MonthScheduleTo> getArchive() {
        List<Event> allEvents = repository.getAll();
        return ScheduleUtil.getMonthScheduleTos(allEvents);
    }

    @Cacheable("event")
    public List<MonthScheduleTo> getEventsForTwoMonths(LocalDateTime startOfFirstMonth,
                                                       LocalDateTime endOfNextMonth) {
        List<Event> allEvents = repository.getEventsForTwoMonths(startOfFirstMonth, endOfNextMonth);

        return ScheduleUtil.getMonthScheduleTos(allEvents);
    }

    @CacheEvict(value = "event", allEntries = true)
    public Event create(Event event) {
        ValidationUtil.checkNew(event);
        Assert.notNull(event, "event must not be null");
        return repository.save(event);
    }

    @CacheEvict(value = "event", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @CacheEvict(value = "event", allEntries = true)
    public void update(Event event) {
        Assert.notNull(event, "event must not be null");
        checkNotFoundWithId(repository.save(event), event.id());
    }
}
