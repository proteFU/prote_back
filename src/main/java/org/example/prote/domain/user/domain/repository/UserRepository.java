package org.example.prote.domain.user.domain.repository;

import org.example.prote.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
