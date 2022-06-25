package io.omnika.services.management.repository;

import io.omnika.services.management.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByActivationToken(UUID activationToken);

    List<User> findAllByTenantId(UUID tenantId);

}
