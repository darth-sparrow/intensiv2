package com.example.intensiv2;

import com.example.intensiv2.dto.AddressDto;
import com.example.intensiv2.mappers.AddressMapper;
import com.example.intensiv2.models.Address;
import com.example.intensiv2.repositories.AddressRepository;
import com.example.intensiv2.services.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressService addressService;

    private AddressDto addressDto;
    private Address address;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addressDto = AddressDto.builder()
                .building(60)
                .street("Pushkina")
                .city("Minsk")
                .region("Minsk region")
                .build();
        address = Address.builder()
                .building(60)
                .street("Pushkina")
                .city("Minsk")
                .region("Minsk region")
                .build();
    }

    @Test
    void createAddress_ThrowException() {
        when(addressRepository.findByBuildingAndStreetAndCity(any(), any(), any())).thenReturn(Optional.of(address));
        assertThrows(IllegalArgumentException.class, () -> addressService.createAddress(addressDto));
    }

    @Test
    void createAddress() {
        when(addressRepository.findByBuildingAndStreetAndCity(any(), any(), any())).thenReturn(Optional.empty());
        when(addressMapper.toEntity(addressDto)).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(addressMapper.toDto(address)).thenReturn(addressDto);
        AddressDto createdAddress = addressService.createAddress(addressDto);
        assertNotNull(createdAddress);
        assertEquals(addressDto.getBuilding(), createdAddress.getBuilding());
    }

    @Test
    void getAllAddresses() {
        List<Address> addresses = Arrays.asList(address);
        when(addressRepository.findAll()).thenReturn(addresses);
        when(addressMapper.toDto(any(Address.class))).thenReturn(addressDto);
        List<AddressDto> addressDtos = addressService.getAllAddresses();
        assertNotNull(addressDtos);
        assertEquals(1, addressDtos.size());
    }

    @Test
    void getAddress() {
        UUID id = UUID.randomUUID();
        when(addressRepository.findById(id)).thenReturn(Optional.of(address));
        when(addressMapper.toDto(address)).thenReturn(addressDto);
        AddressDto retrievedAddress = addressService.getAddress(id);
        assertNotNull(retrievedAddress);
        assertEquals(addressDto.getBuilding(), retrievedAddress.getBuilding());
    }

    @Test
    void getAddress_ThrowException() {
        UUID id = UUID.randomUUID();
        when(addressRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> addressService.getAddress(id));
    }

    @Test
    void updateAddress() {
        UUID id = UUID.randomUUID();
        when(addressRepository.findById(id)).thenReturn(Optional.of(address));
        when(addressMapper.toDto(any(Address.class))).thenReturn(addressDto);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        AddressDto updatedAddress = addressService.updateAddress(id, addressDto);
        assertNotNull(updatedAddress);
        assertEquals(addressDto.getBuilding(), updatedAddress.getBuilding());
    }

    @Test
    void updateAddress_ThrowException() {
        UUID id = UUID.randomUUID();
        when(addressRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> addressService.updateAddress(id, addressDto));
    }

    @Test
    void deleteAddress() {
        UUID id = UUID.randomUUID();
        when(addressRepository.existsById(id)).thenReturn(true);
        assertDoesNotThrow(() -> addressService.deleteAddress(id));
        verify(addressRepository).deleteById(id);
    }

    @Test
    void deleteAddress_ThrowException() {
        UUID id = UUID.randomUUID();
        when(addressRepository.existsById(id)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> addressService.deleteAddress(id));
    }

    @Test
    void deleteAll_ShouldCallDeleteAll() {
        addressService.deleteAll();
        verify(addressRepository).deleteAll();
    }
}