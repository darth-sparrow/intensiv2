package com.example.intensiv2.repositories;

import com.example.intensiv2.models.Service;
import com.example.intensiv2.models.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/** Репозитарий услуг */
@Repository
public interface ServiceRepository extends JpaRepository<Service, UUID> {
    /** Поиск услуги по названию и типу */
    Optional<Service> findByNameAndServiceType(String name, ServiceType serviceType);
}