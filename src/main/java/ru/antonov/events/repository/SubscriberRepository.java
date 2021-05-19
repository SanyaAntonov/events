package ru.antonov.events.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antonov.events.model.Subscriber;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SubscriberRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Subscriber save(Subscriber subscriber) {
        if (subscriber.isNew()) {
            em.persist(subscriber);
            return subscriber;
        } else {
            return em.merge(subscriber);
        }
    }

    @Transactional
    public boolean delete(int id) {
        return em.createQuery("DELETE FROM Subscriber s where s.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    public Subscriber get(int id) {
        return em.find(Subscriber.class, id);
    }

    public List<Subscriber> getAll() {
        return em.createQuery("SELECT s FROM Subscriber s ORDER BY s.email", Subscriber.class)
                .getResultList();
    }
}