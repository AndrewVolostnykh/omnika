package io.omnika.services.management.repository;

import io.omnika.services.management.model.TenantEntity;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, UUID> {

}
