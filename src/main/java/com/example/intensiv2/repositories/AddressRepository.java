package com.example.intensiv2.repositories;

import com.example.intensiv2.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/** Репозитарий адресов */
@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
     /** Поиск адреса по городу, улице и дому */
     Optional<Address> findByBuildingAndStreetAndCity(Integer building, String street, String city);
}