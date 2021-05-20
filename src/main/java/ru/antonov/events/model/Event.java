package ru.antonov.events.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.antonov.events.util.DateTimeUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Event extends BaseEntity {

    @Column(name = "title", nullable = false)
    @NotBlank
    @Size(min = 5, max = 128)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    @NotBlank
    @Size(min = 50)
    private String description;

    @Column(name = "date_time", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @Column(name = "city", nullable = false)
    @NotBlank
    @Size(min = 2, max = 64)
    private String city;

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(min = 2, max = 64)
    private String address;

    @Column(name = "speaker_1", nullable = false)
    @NotBlank
    @Size(min = 2, max = 128)
    private String speaker1;

    @Column(name = "speaker_2", nullable = false)
    @NotBlank
    @Size(min = 2, max = 128)
    private String speaker2;

    public Event(Integer id,
                 @NotBlank @Size(min = 5, max = 128) String title,
                 @NotBlank @Size(min = 50) String description,
                 @NotNull LocalDateTime dateTime,
                 @NotBlank @Size(min = 2, max = 64) String city,
                 @NotBlank @Size(min = 2, max = 64) String address,
                 @NotBlank @Size(min = 2, max = 128) String speaker1,
                 @NotBlank @Size(min = 2, max = 128) String speaker2) {
        super(id);
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.city = city;
        this.address = address;
        this.speaker1 = speaker1;
        this.speaker2 = speaker2;
    }
}
