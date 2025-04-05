package com.example.intensiv2.repositories;

import com.example.intensiv2.models.TicketInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/** Репозитарий информации о билетах */
@Repository
public interface TicketInfoRepository extends JpaRepository<TicketInfo, UUID> {
    /** Поиск информации о билетах по стоимости, валюте и наличию */
    Optional<TicketInfo> findByPriceAndCurrencyAndAvailability(BigDecimal price, String currency, Boolean availability);
}