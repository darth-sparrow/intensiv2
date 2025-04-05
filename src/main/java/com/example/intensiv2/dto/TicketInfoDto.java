package com.example.intensiv2.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

/** DTO-класс, содержащий информацию о билете*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketInfoDto {
    /** ID */
    private UUID id;
    /** Стоимость */
    private BigDecimal price;
    /** Валюта */
    private String currency;
    /** Наличие */
    private Boolean availability;
}
