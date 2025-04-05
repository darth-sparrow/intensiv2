package com.example.intensiv2.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/** *Сущность "Адрес" */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    /** Поле "ID" */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Поле "Здание" */
    @Column(name = "building")
    private Integer building;

    /** Поле "Улица" */
    @Column(name = "street")
    private String street;

    /** Поле "Город" */
    @Column(name = "city")
    private String city;

    /** Поле "Область" */
    @Column(name = "region")
    private String region;

    /** Поле "Список достопримечательностей" */
    @OneToMany(mappedBy = "address",cascade = CascadeType.ALL)
    private List<Attraction> attractions;
}
