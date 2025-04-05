package com.example.intensiv2.dto;

import com.example.intensiv2.models.AttractionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import java.util.List;
import java.util.UUID;

/** DTO-класс, содержащий информацию о достопримечательности*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDto {
    /** ID */
    private UUID id;
    /** Название */
    private String name;
    /** Описание */
    private String description;

    /** Тип достопримечательности */
    @Enumerated(EnumType.STRING)
    private AttractionType attractionType;

    /** Адрес */
    private AddressDto address;
    /** Информация о билете */
    private TicketInfoDto ticketInfo;
    /** Список услуг */
    private List<ServiceDto> services;
}

