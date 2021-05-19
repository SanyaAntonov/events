package ru.antonov.events.web;

//import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.antonov.events.service.EventService;
import ru.antonov.events.to.MonthScheduleTo;
import ru.antonov.events.util.DateTimeUtil;

import java.util.List;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
@Slf4j
//@Tag(name = "Event Controller")
public class EventController {

    private final EventService service;

    @GetMapping("/schedule")
    public List<MonthScheduleTo> getSchedule() {
        return service.getEventsForTwoMonths(DateTimeUtil.startDate(), DateTimeUtil.endDate());
    }

}
