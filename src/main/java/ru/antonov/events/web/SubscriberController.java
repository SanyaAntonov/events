package ru.antonov.events.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.antonov.events.model.Event;
import ru.antonov.events.model.Subscriber;
import ru.antonov.events.repository.EventRepository;
import ru.antonov.events.repository.SubscriberRepository;
import ru.antonov.events.util.exception.NotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Subscriber Controller")
public class SubscriberController {

    private final SubscriberRepository subscriberRepository;
    private final EventRepository eventRepository;

    @GetMapping("/subscribers")
    public ResponseEntity<List<Subscriber>> getAllSubs() {
        log.info("get All Subscribers");
        List<Subscriber> all = subscriberRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/subscribers/{id}")
    public ResponseEntity<Subscriber> getSub(@PathVariable int id) {
        log.info("get Sub with id = {}", id);
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sub not found"));
        return new ResponseEntity<>(subscriber, HttpStatus.OK);
    }

    @PostMapping("/events/{id}")
    public ResponseEntity<Subscriber> createSubscription(@RequestBody @Valid Subscriber subscriber,
                                                         @PathVariable int id) {
        log.info("create Subscription {}", subscriber);
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event not found"));
        Subscriber save = subscriberRepository.save(subscriber);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @PostMapping("/events/schedule")
    public ResponseEntity<Subscriber> createSubscriptionInSchedule(@RequestBody @Valid Subscriber subscriber) {
        log.info("create Subscription {}", subscriber);
        return new ResponseEntity<>(subscriberRepository.save(subscriber), HttpStatus.CREATED);
    }

    @DeleteMapping("/subscribers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable int id) {
        log.info("delete Subscription with id = {}", id);
        subscriberRepository.deleteById(id);
    }

}
