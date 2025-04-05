package com.example.intensiv2.mappers;

import com.example.intensiv2.dto.AddressDto;
import com.example.intensiv2.dto.AttractionDto;
import com.example.intensiv2.dto.ServiceDto;
import com.example.intensiv2.dto.TicketInfoDto;
import com.example.intensiv2.models.Address;
import com.example.intensiv2.models.Attraction;
import com.example.intensiv2.models.Service;
import com.example.intensiv2.models.TicketInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/** Маппер для преобразования между {@link Attraction} и {@link AttractionDto}. */
@Component
@RequiredArgsConstructor
public class AttractionMapper {
    /** Маппер для преобразования между {@link Address} и {@link AddressDto}. */
    private final AddressMapper addressMapper;
    /** Маппер для преобразования между {@link TicketInfo} и {@link TicketInfoDto}. */
    private final TicketInfoMapper ticketInfoMapper;
    /** Маппер для преобразования между {@link Service} и {@link ServiceDto}. */
    private final ServiceMapper serviceMapper;

    /** Преобразует сущность {@link Attraction} в DTO {@link AttractionDto}. */
    public AttractionDto toDto(Attraction attraction) {
        if (attraction == null) {
            throw new IllegalArgumentException("Attraction is null in Mapper");
        }
        return AttractionDto.builder()
                .id(attraction.getId())
                .name(attraction.getName())
                .description(attraction.getDescription())
                .attractionType(attraction.getAttractionType())
                .address(attraction.getAddress() != null ? addressMapper.toDto(attraction.getAddress()) : null)
                .ticketInfo(attraction.getTicketInfo() != null ? ticketInfoMapper.toDto(attraction.getTicketInfo()) : null)
                .services(attraction.getServices().stream()
                        .map(serviceMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    /** Преобразует DTO {@link AttractionDto} в сущность {@link Attraction}. */
    public Attraction toEntity(AttractionDto attractionDto) {
        if (attractionDto == null) {
            throw new IllegalArgumentException("AttractionDto is null in Mapper");
        }
        return Attraction.builder()
                .name(attractionDto.getName())
                .description(attractionDto.getDescription())
                .attractionType(attractionDto.getAttractionType())
                .address(addressMapper.toEntity(attractionDto.getAddress())) // Преобразование адреса
                .ticketInfo(ticketInfoMapper.toEntity(attractionDto.getTicketInfo())) // Преобразование билетной информации
                .services(attractionDto.getServices().stream()
                        .map(serviceMapper::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
