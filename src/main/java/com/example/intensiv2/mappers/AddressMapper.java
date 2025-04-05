package com.example.intensiv2.mappers;

import com.example.intensiv2.dto.AddressDto;
import com.example.intensiv2.models.Address;
import org.springframework.stereotype.Component;

/** Маппер для преобразования между {@link Address} и {@link AddressDto}. */
@Component
public class AddressMapper {
    /** Преобразует сущность {@link Address} в DTO {@link AddressDto}. */
    public AddressDto toDto(Address address) {
        if (address == null) {
                throw new IllegalArgumentException("Address is null in Mapper");

        }
        return AddressDto.builder()
                .id(address.getId())
                .building(address.getBuilding())
                .street(address.getStreet())
                .city(address.getCity())
                .region(address.getRegion())
                .build();
    }
/** Преобразует DTO {@link AddressDto} в сущность {@link Address}. */
    public Address toEntity(AddressDto addressDto) {
        if (addressDto == null) {
            throw new IllegalArgumentException("AddressDto is null in Mapper");
        }
        return Address.builder()
                .building(addressDto.getBuilding())
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .region(addressDto.getRegion())
                .build();
    }
}
