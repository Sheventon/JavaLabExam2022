package ru.itis.pdfclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.pdfclient.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
