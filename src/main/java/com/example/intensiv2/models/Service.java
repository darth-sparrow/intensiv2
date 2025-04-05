package com.example.intensiv2.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

/** Сущность "Услуги" */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "services")
public class Service {
    /** Поле "ID" */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Поле "Название" */
    @Column(name = "name")
    private String name;

    /** Поле "Описание" */
    @Column(name = "description")
    private String description;

    /** Поле "Тип услуги" */
    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType serviceType;

    /** Поле "Список достопримечательностей" */
    @ManyToMany(mappedBy = "services",cascade = CascadeType.ALL)
    private List<Attraction> attractions;
}
