package ru.antonov.events.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MonthScheduleTo {
    private String month;
    private List<ScheduleElementTo> elements = new ArrayList<>();
}
