package com.vsc.exam;

import com.vsc.exam.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String username);

    boolean existsByUsername(String username);
}
