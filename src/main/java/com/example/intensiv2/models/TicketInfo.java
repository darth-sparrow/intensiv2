package com.example.intensiv2.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

/** Сущность "Информация о билетах" */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket_info")
public class TicketInfo {
    /** Поле "ID" */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Поле "Цена" */
    @Column(name = "price")
    private BigDecimal price;

    /** Поле "Валюта" */
    @Column(name = "currency")
    private String currency;

    /** Поле "Наличие" */
    @Column(name = "availability")
    private Boolean availability;

    /** Поле "Достопримечательность" */
    @OneToOne(mappedBy = "ticketInfo", cascade = CascadeType.ALL)
    private Attraction attraction;
}
