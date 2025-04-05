package com.example.intensiv2.dto;

import com.example.intensiv2.models.ServiceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import java.util.UUID;

/** DTO-класс, содержащий информацию о услугах*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {
    /** ID */
    private UUID id;
    /** Название */
    private String name;
    /** Описание */
    private String description;
    /** Тип услуги */
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;
}