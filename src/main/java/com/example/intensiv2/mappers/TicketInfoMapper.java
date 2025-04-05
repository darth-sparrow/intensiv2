package com.example.intensiv2.mappers;

import com.example.intensiv2.dto.TicketInfoDto;
import com.example.intensiv2.models.TicketInfo;
import org.springframework.stereotype.Component;

/** Маппер для преобразования между {@link TicketInfo} и {@link TicketInfoDto}. */
@Component
public class TicketInfoMapper {
    /** Преобразует сущность {@link TicketInfo} в DTO {@link TicketInfoDto}. */
    public TicketInfoDto toDto(TicketInfo ticketInfo) {
        if (ticketInfo == null) {
            throw new IllegalArgumentException("TicketInfo cannot be null");
        }
        return TicketInfoDto.builder()
                .id(ticketInfo.getId())
                .price(ticketInfo.getPrice())
                .currency(ticketInfo.getCurrency())
                .availability(ticketInfo.getAvailability())
                .build();
    }
    /** Преобразует DTO {@link TicketInfoDto} в сущность {@link TicketInfo}. */
    public TicketInfo toEntity(TicketInfoDto ticketInfoDto) {
        if (ticketInfoDto == null) {
            throw new IllegalArgumentException("TicketInfoDto cannot be null");
        }
        return TicketInfo.builder()
                .price(ticketInfoDto.getPrice())
                .currency(ticketInfoDto.getCurrency())
                .availability(ticketInfoDto.getAvailability())
                .build();
    }
}
