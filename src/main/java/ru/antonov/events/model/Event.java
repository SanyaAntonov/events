package ru.antonov.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.util.Date;

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

    @Column(name = "description", nullable = false, columnDefinition="TEXT")
    @NotBlank
    @Size(min = 50)
    private String description;

    @Column(name = "date_time", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date created = new Date();

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
}
