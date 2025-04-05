package com.example.intensiv2;

import com.example.intensiv2.dto.TicketInfoDto;
import com.example.intensiv2.mappers.TicketInfoMapper;
import com.example.intensiv2.models.TicketInfo;
import com.example.intensiv2.repositories.TicketInfoRepository;
import com.example.intensiv2.services.TicketInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketInfoServiceTest {

    @Mock
    private TicketInfoRepository ticketInfoRepository;

    @Mock
    private TicketInfoMapper ticketInfoMapper;

    @InjectMocks
    private TicketInfoService ticketInfoService;

    private TicketInfoDto ticketInfoDto;
    private TicketInfo ticketInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ticketInfoDto = TicketInfoDto.builder()
                .price(BigDecimal.valueOf(500.00))
                .currency("RUB")
                .availability(true)
                .build();

        ticketInfo = TicketInfo.builder()
                .price(BigDecimal.valueOf(500.00))
                .currency("RUB")
                .availability(true)
                .build();
    }

    @Test
    void createTicketInfo_ThrowException() {
        when(ticketInfoRepository.findByPriceAndCurrencyAndAvailability(ticketInfoDto.getPrice(), ticketInfoDto.getCurrency(), ticketInfoDto.getAvailability()))
                .thenReturn(Optional.of(ticketInfo));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketInfoService.createTicketInfo(ticketInfoDto);
        });
        assertEquals("TicketInfo already exists", exception.getMessage());
    }

    @Test
    void createTicketInfo() {
        when(ticketInfoRepository.findByPriceAndCurrencyAndAvailability(any(), any(), any())).thenReturn(Optional.empty());
        when(ticketInfoMapper.toEntity(ticketInfoDto)).thenReturn(ticketInfo);
        when(ticketInfoRepository.save(ticketInfo)).thenReturn(ticketInfo);
        when(ticketInfoMapper.toDto(ticketInfo)).thenReturn(ticketInfoDto);
        TicketInfoDto createdTicketInfoDto = ticketInfoService.createTicketInfo(ticketInfoDto);
        assertEquals(ticketInfoDto, createdTicketInfoDto);
        verify(ticketInfoRepository).save(ticketInfo);
    }

    @Test
    void getAllTicketInfo() {
        List<TicketInfo> tickets = List.of(ticketInfo);
        when(ticketInfoRepository.findAll()).thenReturn(tickets);
        when(ticketInfoMapper.toDto(ticketInfo)).thenReturn(ticketInfoDto);
        List<TicketInfoDto> ticketInfoDtos = ticketInfoService.getAllTicketInfo();
        assertEquals(1, ticketInfoDtos.size());
        assertEquals(ticketInfoDto, ticketInfoDtos.get(0));
    }

    @Test
    void getTicketInfo() {
        UUID id = ticketInfoDto.getId();
        when(ticketInfoRepository.findById(id)).thenReturn(Optional.of(ticketInfo));
        when(ticketInfoMapper.toDto(ticketInfo)).thenReturn(ticketInfoDto);
        TicketInfoDto foundTicketInfoDto = ticketInfoService.getTicketInfo(id);
        assertEquals(ticketInfoDto, foundTicketInfoDto);
    }

    @Test
    void getTicketInfo_ThrowException() {
        UUID id = ticketInfoDto.getId();
        when(ticketInfoRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            ticketInfoService.getTicketInfo(id);
        });
        assertEquals("TicketInfo not found", exception.getMessage());
    }

    @Test
    void updateTicketInfo() {
        UUID id = ticketInfoDto.getId();
        when(ticketInfoRepository.findById(id)).thenReturn(Optional.of(ticketInfo));
        when(ticketInfoMapper.toDto(any())).thenReturn(ticketInfoDto);
        when(ticketInfoRepository.save(any())).thenReturn(ticketInfo);
        TicketInfoDto updatedTicketInfoDto = ticketInfoService.updateTicketInfo(id, ticketInfoDto);
        assertEquals(ticketInfoDto, updatedTicketInfoDto);
        verify(ticketInfoRepository).save(ticketInfo);
    }

    @Test
    void updateTicketInfo_ThrowException() {
        UUID id = ticketInfoDto.getId();
        when(ticketInfoRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            ticketInfoService.updateTicketInfo(id, ticketInfoDto);
        });
        assertEquals("TicketInfo not found", exception.getMessage());
    }

    @Test
    void deleteTicketInfoThrowException() {
        UUID id = ticketInfoDto.getId();
        when(ticketInfoRepository.existsById(id)).thenReturn(false);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            ticketInfoService.deleteTicketInfo(id);
        });
        assertEquals("TicketInfo not found", exception.getMessage());
    }

    @Test
    void deleteTicketInfo() {
        UUID id = ticketInfoDto.getId();
        when(ticketInfoRepository.existsById(id)).thenReturn(true);
        ticketInfoService.deleteTicketInfo(id);
        verify(ticketInfoRepository).deleteById(id);
    }

    @Test
    void deleteAll() {
        ticketInfoService.deleteAll();
        verify(ticketInfoRepository).deleteAll();
    }
}
