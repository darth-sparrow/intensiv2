package com.example.intensiv2.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

/** Сущность "Достопримечательность" */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attractions")
public class Attraction {
    /** Поле "ID */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Поле "Название" */
    @Column(name = "name")
    private String name;

    /** Поле "Описание" */
    @Column(name = "description")
    private String description;

    /** Поле "Тип" */
    @Enumerated(EnumType.STRING)
    @Column(name = "attraction_type")
    private AttractionType attractionType;

    /** Поле "Адрес" */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    /** Поле "Информация о билетах" */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_info_id")
    private TicketInfo ticketInfo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "attraction_service",
            joinColumns = @JoinColumn(name = "attraction_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    /** Поле "Список услуг" */
    private List<Service> services;
}
