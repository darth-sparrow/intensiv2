package com.example.intensiv2.mappers;

import com.example.intensiv2.dto.ServiceDto;
import com.example.intensiv2.models.Service;
import org.springframework.stereotype.Component;

/** Маппер для преобразования между {@link Service} и {@link ServiceDto}. */
@Component
public class ServiceMapper {
    /** Преобразует сущность {@link Service} в DTO {@link ServiceDto}. */
    public ServiceDto toDto(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Service is null in Mapper");
        }
        return ServiceDto.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .serviceType(service.getServiceType())
                .build();
    }
    /** Преобразует DTO {@link ServiceDto} в сущность {@link Service}. */
    public Service toEntity(ServiceDto serviceDto) {
        if (serviceDto == null) {
            throw new IllegalArgumentException("ServiceDto is null in Mapper");
        }
        return Service.builder()
                .name(serviceDto.getName())
                .description(serviceDto.getDescription())
                .serviceType(serviceDto.getServiceType())
                .build();
    }
}