package ru.antonov.events.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.antonov.events.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public User findByEmailIgnoreCase(String email) {
        User user = em.createQuery("SELECT u FROM User u WHERE u.email = LOWER(:email)", User.class)
                .setParameter("email", email)
                .getSingleResult();
        Assert.notNull(user, "user must not be null");
        return user;
    }
}
