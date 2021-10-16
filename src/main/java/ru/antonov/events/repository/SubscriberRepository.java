package ru.antonov.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.antonov.events.model.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
}
