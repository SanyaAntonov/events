package ru.antonov.events.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.antonov.events.model.Event;
import ru.antonov.events.service.EventService;
import ru.antonov.events.dto.MonthScheduleTo;
import ru.antonov.events.util.DateTimeUtil;

import java.util.List;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
@Slf4j
@Tag(name = "Event Controller")
public class EventController {

    private final EventService service;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        log.info("get All Events");
        List<Event> all = service.getAll(DateTimeUtil.startDate(), DateTimeUtil.endDate());
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Event> getEvent(@PathVariable int id) {
        log.info("get Event with id = {}", id);
        Event event = service.get(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        log.info("create Event {}", event);
        Event eventCreated = service.save(event);
        return new ResponseEntity<>(eventCreated, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Event updateEvent(@PathVariable int id, @RequestBody Event event) {
        log.info("update Event with id = {}", id);
        event.setId(id);
        return service.save(event);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable int id) {
        log.info("delete Event with id = {}", id);
        service.delete(id);
    }


    @GetMapping("/schedule")
    public List<MonthScheduleTo> getSchedule() {
        log.info("get Schedule");
        return service.getEventsForTwoMonths(DateTimeUtil.startDate(), DateTimeUtil.endDate());
    }


}
