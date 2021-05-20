package ru.antonov.events.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class EventTo {
    private int id;
    private String title;
    private String city;
    private String month;
    private int dayOfMonth;
    private LocalTime dateTime;
    private int year;
    private String address;
    private String speaker1;
    private String speaker2;
}
