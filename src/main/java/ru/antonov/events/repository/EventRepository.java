package ru.antonov.events.repository;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antonov.events.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class EventRepository {
    @PersistenceContext
    private EntityManager em;

    @Secured("ROLE_ADMIN")
    @Transactional
    public Event save(Event event) {
        if (event.isNew()) {
            em.persist(event);
            return event;
        } else {
            return em.merge(event);
        }
    }

    @Secured("ROLE_ADMIN")
    @Transactional
    public boolean delete(int id) {
        return em.createQuery("DELETE FROM Event e where e.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    public Optional<Event> get(int id) {
        return Optional.ofNullable(em.find(Event.class, id));
    }

    public Optional<List<Event>> getEventsForTwoMonths(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Optional.ofNullable(em.createQuery("""
                    SELECT e FROM Event e
                    WHERE e.dateTime >= :startDateTime AND e.dateTime < :endDateTime ORDER BY e.dateTime
                """, Event.class)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList());
    }

/*    public List<Event> getAll() {
        return em.createQuery("SELECT e FROM Event e ORDER BY e.dateTime", Event.class)
                .getResultList();
    }*/
}
