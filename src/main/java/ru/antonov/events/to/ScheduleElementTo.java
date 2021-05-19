package ru.antonov.events.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ScheduleElementTo {

    private String cityName;
    private int numberOfEvents;
    private List<EventTo> eventList = new ArrayList<>();
}
