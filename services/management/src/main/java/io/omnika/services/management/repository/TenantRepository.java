package io.omnika.services.management.repository;

import io.omnika.services.management.model.Tenant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    List<Tenant> findByUserId(Long userId);

    boolean existsByNameAndUserId(String name, Long userId);

    boolean existsByUserId(Long userId);
}
