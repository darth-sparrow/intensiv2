package com.example.intensiv2;

import com.example.intensiv2.dto.ServiceDto;
import com.example.intensiv2.mappers.ServiceMapper;
import com.example.intensiv2.models.Service;
import com.example.intensiv2.repositories.ServiceRepository;
import com.example.intensiv2.services.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import static com.example.intensiv2.models.ServiceType.TOUR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServiceMapper serviceMapper;

    @InjectMocks
    private ServiceService serviceService;

    private ServiceDto serviceDto;
    private Service service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceDto = ServiceDto.builder()
                .name("Historical City Tour")
                .description("Explore the rich history and culture of the city with our guided tour.")
                .serviceType(TOUR)
                .build();
        service = Service.builder()
                .name("Historical City Tour")
                .description("Explore the rich history and culture of the city with our guided tour.")
                .serviceType(TOUR)
                .build();
    }

    @Test
    void createService_ThrowException() {
        when(serviceRepository.findByNameAndServiceType(serviceDto.getName(), serviceDto.getServiceType()))
                .thenReturn(Optional.of(service));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            serviceService.createService(serviceDto);
        });
        assertEquals("Service already exists", exception.getMessage());
    }

    @Test
    void createService() {
        when(serviceRepository.findByNameAndServiceType(any(), any())).thenReturn(Optional.empty());
        when(serviceMapper.toEntity(serviceDto)).thenReturn(service);
        when(serviceRepository.save(service)).thenReturn(service);
        when(serviceMapper.toDto(service)).thenReturn(serviceDto);
        ServiceDto createdServiceDto = serviceService.createService(serviceDto);
        assertEquals(serviceDto, createdServiceDto);
        verify(serviceRepository).save(service);
    }

    @Test
    void getAllServices() {
        List<Service> services = List.of(service);
        when(serviceRepository.findAll()).thenReturn(services);
        when(serviceMapper.toDto(service)).thenReturn(serviceDto);
        List<ServiceDto> serviceDtos = serviceService.getAllServices();
        assertEquals(1, serviceDtos.size());
        assertEquals(serviceDto, serviceDtos.get(0));
    }

    @Test
    void getService() {
        UUID id = serviceDto.getId();
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceMapper.toDto(service)).thenReturn(serviceDto);
        ServiceDto foundServiceDto = serviceService.getService(id);
        assertEquals(serviceDto, foundServiceDto);
    }

    @Test
    void getService_ThrowException() {
        UUID id = serviceDto.getId();
        when(serviceRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            serviceService.getService(id);
        });
        assertEquals("Service not found", exception.getMessage());
    }

    @Test
    void updateService() {
        UUID id = serviceDto.getId();
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceMapper.toDto(any())).thenReturn(serviceDto);
        when(serviceRepository.save(any())).thenReturn(service);
        ServiceDto updatedServiceDto = serviceService.updateService(id, serviceDto);
        assertEquals(serviceDto, updatedServiceDto);
        verify(serviceRepository).save(service);
    }

    @Test
    void updateService_ThrowException() {
        UUID id = serviceDto.getId();
        when(serviceRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            serviceService.updateService(id, serviceDto);
        });
        assertEquals("Service not found", exception.getMessage());
    }

    @Test
    void deleteService_ThrowException() {
        UUID id = serviceDto.getId();
        when(serviceRepository.existsById(id)).thenReturn(false);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            serviceService.deleteService(id);
        });
        assertEquals("Service not found", exception.getMessage());
    }

    @Test
    void deleteService() {
        UUID id = serviceDto.getId();
        when(serviceRepository.existsById(id)).thenReturn(true);
        serviceService.deleteService(id);
        verify(serviceRepository).deleteById(id);
    }

    @Test
    void deleteAll() {
        serviceService.deleteAll();
        verify(serviceRepository).deleteAll();
    }
}