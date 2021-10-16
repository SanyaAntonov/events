package ru.antonov.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.antonov.events.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailIgnoreCase(@Email @NotEmpty @Size(max = 128) String email);
}
