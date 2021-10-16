package ru.antonov.events.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MonthScheduleTo {
    private String month;
    private List<ScheduleElementTo> elements = new ArrayList<>();
}
