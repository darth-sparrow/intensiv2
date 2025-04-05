package com.example.intensiv2.repositories;

import com.example.intensiv2.models.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/** Репозиторий достопримечательностей */
@Repository
public interface AttractionRepository extends JpaRepository<Attraction, UUID> {
    /** Поиск достопримечательностей по городу */
    List<Attraction> findByAddressCity(String city);

    /** Поиск достопримечательностей по области */
    List<Attraction> findByAddressRegion(String region);

    /** Поиск достопримечательностей по услугам */
    List<Attraction> findByServicesName(String serviceName);

    /** Поиск достопримечательностей по названию */
    List<Attraction> findByNameContaining(String name);
}