package com.example.intensiv2.dto;

import lombok.*;
import java.util.UUID;

/** DTO-класс, содержащий информацию о адресе*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    /** ID */
    private UUID id;
    /** Дом */
    private Integer building;
    /** Улица */
    private String street;
    /** Город */
    private String city;
    /** Обасть */
    private String region;
}

