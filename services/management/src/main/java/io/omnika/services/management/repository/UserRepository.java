package io.omnika.services.management.repository;

import io.omnika.common.security.model.Authority;
import io.omnika.services.management.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByActivationToken(UUID activationToken);

    List<UserEntity> findAllByTenantId(UUID tenantId);

    List<UserEntity> findAllByAuthorityAndTenantId(Authority authority, UUID tenantId);

}
