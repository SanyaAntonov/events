package ru.antonov.events.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ScheduleElementTo {
    private String cityName;
    private int numberOfEvents;
    private List<EventTo> eventList = new ArrayList<>();
}
