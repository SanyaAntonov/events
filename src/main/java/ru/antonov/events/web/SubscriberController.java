package ru.antonov.events.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.antonov.events.model.Subscriber;
import ru.antonov.events.repository.SubscriberRepository;
import ru.antonov.events.util.ValidationUtil;

import java.util.List;

@RestController
@RequestMapping("/subscribers")
@AllArgsConstructor
@Slf4j
//@Tag(name = "Subscriber Controller")
public class SubscriberController {

    private final SubscriberRepository repository;

    @GetMapping
    public ResponseEntity<List<Subscriber>> getAllSubs() {
        log.info("get All Subscribers");
        List<Subscriber> all = repository.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Subscriber> getSub(@PathVariable int id) {
        Subscriber subscriber = repository.get(id);
        ValidationUtil.assureIdConsistent(subscriber, id);
        return new ResponseEntity<>(subscriber, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Subscriber> createSubscription(@RequestBody Subscriber subscriber) {
        log.info("create Subscription {}", subscriber);

        Subscriber save = repository.save(subscriber);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable int id) {
        log.info("delete Subscription with id = {}", id);
        repository.delete(id);
    }

}
