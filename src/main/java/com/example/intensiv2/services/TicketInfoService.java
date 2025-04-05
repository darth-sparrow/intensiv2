package com.example.intensiv2.services;

import com.example.intensiv2.dto.TicketInfoDto;
import com.example.intensiv2.mappers.TicketInfoMapper;
import com.example.intensiv2.models.TicketInfo;
import com.example.intensiv2.repositories.TicketInfoRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления сущностями {@link TicketInfo}.
 * Предоставляет методы для создания, получения, обновления и удаления информации о билетах.
 */
@Service
@RequiredArgsConstructor
public class TicketInfoService {
    /** Репозиторий для доступа к данным о билетах. */
    private final TicketInfoRepository ticketInfoRepository;

    /** Маппер для преобразования между {@link TicketInfo} и {@link TicketInfoDto}. */
    private final TicketInfoMapper ticketInfoMapper;

    /**
     * Создает новую информацию о билете.
     *
     * @param ticketInfoDto DTO с информацией о билете
     * @return созданный {@link TicketInfoDto}
     * @throws IllegalArgumentException если такой билет уже существует
     */
    public TicketInfoDto createTicketInfo(TicketInfoDto ticketInfoDto) {
        if (ticketInfoRepository.findByPriceAndCurrencyAndAvailability(ticketInfoDto.getPrice(), ticketInfoDto.getCurrency(), ticketInfoDto.getAvailability()).isPresent()) {
            throw new IllegalArgumentException("TicketInfo already exists");
        }
        TicketInfo ticketInfo = ticketInfoMapper.toEntity(ticketInfoDto);
        return ticketInfoMapper.toDto(ticketInfoRepository.save(ticketInfo));
    }

    /**
     * Возвращает список всех билетов.
     *
     * @return список {@link TicketInfoDto}
     */
    public List<TicketInfoDto> getAllTicketInfo() {
        return ticketInfoRepository.findAll().stream().map(ticketInfoMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Возвращает информацию о билете по ID.
     *
     * @param id уникальный идентификатор билета
     * @return {@link TicketInfoDto} с указанным ID
     * @throws NoSuchElementException если билет не найден
     */
    public TicketInfoDto getTicketInfo(UUID id) {
        TicketInfo ticketInfo = ticketInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TicketInfo not found"));
        return ticketInfoMapper.toDto(ticketInfo);
    }

    /**
     * Обновляет информацию о билете по ID.
     *
     * @param id идентификатор обновляемого билета
     * @param ticketInfoDto обновлённые данные
     * @return обновлённый {@link TicketInfoDto}
     * @throws NoSuchElementException если билет не найден
     */
    public TicketInfoDto updateTicketInfo(UUID id, TicketInfoDto ticketInfoDto) {
        TicketInfo ticketInfo = ticketInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TicketInfo not found"));
        ticketInfo.setPrice(ticketInfoDto.getPrice());
        ticketInfo.setCurrency(ticketInfoDto.getCurrency());
        ticketInfo.setAvailability(ticketInfoDto.getAvailability());
        return ticketInfoMapper.toDto(ticketInfoRepository.save(ticketInfo));
    }

    /**
     * Удаляет билет по ID.
     *
     * @param id идентификатор билета
     * @throws NoSuchElementException если билет не найден
     */
    public void deleteTicketInfo(UUID id) {
        if (!ticketInfoRepository.existsById(id)) {
            throw new NoSuchElementException("TicketInfo not found");
        }
        ticketInfoRepository.deleteById(id);
    }

    /** Удаляет всю информацию о билетах. */
    public void deleteAll() {
        ticketInfoRepository.deleteAll();
    }
}