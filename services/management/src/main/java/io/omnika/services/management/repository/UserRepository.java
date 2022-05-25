package io.omnika.services.management.repository;

import io.omnika.services.management.model.Tenant;
import io.omnika.services.management.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByActivationToken(UUID activationToken);

    boolean existsByIdAndTenantsContains(Long userId, Tenant tenant);
}
