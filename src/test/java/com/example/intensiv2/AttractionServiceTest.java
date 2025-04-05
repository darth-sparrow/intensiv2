package com.example.intensiv2;

import com.example.intensiv2.dto.AddressDto;
import com.example.intensiv2.dto.AttractionDto;
import com.example.intensiv2.dto.ServiceDto;
import com.example.intensiv2.dto.TicketInfoDto;
import com.example.intensiv2.mappers.AddressMapper;
import com.example.intensiv2.mappers.AttractionMapper;
import com.example.intensiv2.mappers.ServiceMapper;
import com.example.intensiv2.mappers.TicketInfoMapper;
import com.example.intensiv2.models.Address;
import com.example.intensiv2.models.Attraction;
import com.example.intensiv2.models.Service;
import com.example.intensiv2.models.TicketInfo;
import com.example.intensiv2.repositories.AttractionRepository;
import com.example.intensiv2.services.AttractionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.*;
import static com.example.intensiv2.models.AttractionType.MUSEUM;
import static com.example.intensiv2.models.ServiceType.TOUR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AttractionServiceTest {

    @Mock
    private AttractionRepository attractionRepository;

    @Mock
    private AttractionMapper attractionMapper;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private TicketInfoMapper ticketInfoMapper;

    @Mock
    private ServiceMapper serviceMapper;

    @InjectMocks
    private AttractionService attractionService;

    private AttractionDto attractionDto;
    private Attraction attraction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attractionDto = AttractionDto.builder()
                .name("Art Museum")
                .description("The largest art museum in Belarus.")
                .attractionType(MUSEUM)
                .address(AddressDto.builder()
                        .building(60)
                        .street("Pushkina")
                        .city("Minsk")
                        .region("Minsk region")
                        .build())
                .services(List.of(ServiceDto.builder()
                .name("Historical City Tour")
                .description("Explore the rich history and culture of the city with our guided tour.")
                .serviceType(TOUR)
                .build()))
                .ticketInfo(TicketInfoDto.builder()
                        .price(BigDecimal.valueOf(500.00))
                        .currency("RUB")
                        .availability(true)
                        .build())
                .build();

        attraction = Attraction.builder()
                .name("Art Museum")
                .description("The largest art museum in Belarus.")
                .attractionType(MUSEUM)
                .address(Address.builder()
                        .building(60)
                        .street("Pushkina")
                        .city("Minsk")
                        .region("Minsk region")
                        .build())
                .services(List.of(Service.builder()
                        .name("Historical City Tour")
                        .description("Explore the rich history and culture of the city with our guided tour.")
                        .serviceType(TOUR)
                        .build()))
                .ticketInfo(TicketInfo.builder()
                        .price(BigDecimal.valueOf(500.00))
                        .currency("RUB")
                        .availability(true)
                        .build())
                .build();
    }

    @Test
    void createAttraction() {
        when(attractionMapper.toEntity(attractionDto)).thenReturn(attraction);
        when(attractionRepository.save(attraction)).thenReturn(attraction);
        when(attractionMapper.toDto(attraction)).thenReturn(attractionDto);
        AttractionDto createdAttraction = attractionService.createAttraction(attractionDto);
        assertNotNull(createdAttraction);
        assertEquals(attractionDto.getName(), createdAttraction.getName());
        verify(attractionRepository).save(any());
    }

    @Test
    void getAllAttractions() {
        when(attractionRepository.findAll()).thenReturn(Collections.singletonList(attraction));
        when(attractionMapper.toDto(attraction)).thenReturn(attractionDto);
        List<AttractionDto> attractions = attractionService.getAllAttractions();
        assertEquals(1, attractions.size());
        assertEquals(attractionDto.getName(), attractions.get(0).getName());
    }

    @Test
    void getAttraction() {
        UUID id = UUID.randomUUID();
        when(attractionRepository.findById(id)).thenReturn(Optional.of(attraction));
        when(attractionMapper.toDto(attraction)).thenReturn(attractionDto);
        AttractionDto foundAttraction = attractionService.getAttraction(id);
        assertNotNull(foundAttraction);
        assertEquals(attractionDto.getName(), foundAttraction.getName());
    }

    @Test
    void getAttractionThrowException() {
        UUID id = UUID.randomUUID();
        when(attractionRepository.findById(id)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            attractionService.getAttraction(id);
        });
        assertEquals("Attraction not found", exception.getMessage());
    }

    @Test
    void updateAttraction() {
        UUID id = UUID.randomUUID();
        when(attractionRepository.findById(id)).thenReturn(Optional.of(attraction));
        when(attractionRepository.save(any())).thenReturn(attraction);
        when(attractionMapper.toDto(attraction)).thenReturn(attractionDto);
        AttractionDto updatedAttraction = attractionService.updateAttraction(id, attractionDto);
        assertNotNull(updatedAttraction);
        assertEquals(attractionDto.getName(), updatedAttraction.getName());
        verify(attractionRepository).save(any());
    }

    @Test
    void updateAttraction_ThrowException() {
        UUID id = UUID.randomUUID();
        when(attractionRepository.findById(id)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            attractionService.updateAttraction(id, attractionDto);
        });
        assertEquals("Attraction not found", exception.getMessage());
    }

    @Test
    void deleteAttraction() {
        UUID id = UUID.randomUUID();
        when(attractionRepository.existsById(id)).thenReturn(true);
        attractionService.deleteAttraction(id);
        verify(attractionRepository).deleteById(id);
    }

    @Test
    void deleteAttraction_ThrowException() {
        UUID id = UUID.randomUUID();
        when(attractionRepository.existsById(id)).thenReturn(false);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            attractionService.deleteAttraction(id);
        });
        assertEquals("Attraction not found", exception.getMessage());
    }

    @Test
    void deleteAll() {
        attractionService.deleteAll();
        verify(attractionRepository).deleteAll();
    }
}