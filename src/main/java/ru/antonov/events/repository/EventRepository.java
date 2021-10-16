package ru.antonov.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import ru.antonov.events.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("select e from Event e where e.dateTime between :startDateTime and :endDateTime order by e.dateTime")
    Optional<List<Event>> findAllByDateTimeBetweenOrderByDateTime(
        @Param("startDateTime") LocalDateTime startDateTime,
        @Param("endDateTime") LocalDateTime endDateTime);

    @Override
    @Secured("ROLE_ADMIN")
    <S extends Event> S save(S s);

    @Override
    @Secured("ROLE_ADMIN")
    void deleteById(Integer integer);
}
