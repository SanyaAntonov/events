package ru.antonov.events.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antonov.events.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class EventRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Event save(Event event) {
        if (event.isNew()) {
            em.persist(event);
            return event;
        } else {
            return em.merge(event);
        }
    }

    @Transactional
    public boolean delete(int id) {
        return em.createQuery("DELETE FROM Event e where e.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    public Event get(int id) {
        return em.find(Event.class, id);
    }

    public List<Event> getAll() {
        return em.createQuery("SELECT e FROM Event e ORDER BY e.dateTime DESC", Event.class)
                .getResultList();
    }

    public List<Event> getEventsForTwoMonths(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return em.createQuery("""
                    SELECT e FROM Event e
                    WHERE e.dateTime >= :startDateTime AND e.dateTime < :endDateTime ORDER BY e.dateTime DESC
                """, Event.class)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}
