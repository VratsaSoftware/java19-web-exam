package com.vsc.exam;

import com.vsc.exam.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByAuthority(String authority);

    Optional<Role> findFirstByAuthority(String authority);
}
