package com.example.intensiv2.services;


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
import com.example.intensiv2.models.TicketInfo;
import com.example.intensiv2.repositories.AttractionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для управления достопримечательностями.
 * Предоставляет методы для создания, получения, обновления и удаления достопримечательностей,
 * а также для поиска достопримечательностей по различным критериям.
 */
@Service
@RequiredArgsConstructor
public class AttractionService {
    /** Репозиторий для доступа к данным об достопримечательностях. */
    private final AttractionRepository attractionRepository;
    /** Маппер для преобразования между {@link Attraction} и {@link AttractionDto}. */
    private final AttractionMapper attractionMapper;
    /** Маппер для преобразования между {@link Address} и {@link AddressDto}. */
    private final AddressMapper addressMapper;
    /** Маппер для преобразования между {@link TicketInfo} и {@link TicketInfoDto}. */
    private final TicketInfoMapper ticketInfoMapper;
    /** Маппер для преобразования между {@link Service} и {@link ServiceDto} */
    private final ServiceMapper serviceMapper;

    /**
     * Создаёт новую достопримечательность.
     *
     * @param attractionDto DTO с данными достопримечательности
     * @return созданный {@link AttractionDto}
     */
    public AttractionDto createAttraction(AttractionDto attractionDto) {
        Attraction attraction = attractionMapper.toEntity(attractionDto);
        return attractionMapper.toDto(attractionRepository.save(attraction));
    }

    /**
     * Возвращает список всех достопримечательностей.
     *
     * @return список {@link AttractionDto}
     */
    public List<AttractionDto> getAllAttractions() {
        return attractionRepository.findAll().stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Получает достопримечательность по её идентификатору.
     *
     * @param id уникальный идентификатор достопримечательности
     * @return {@link AttractionDto} для указанного ID
     * @throws NoSuchElementException если достопримечательность с таким ID не найден
     */
    public AttractionDto getAttraction(UUID id) {
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Attraction not found"));
        return attractionMapper.toDto(attraction);
    }

    /**
     * Обновляет достопримечательность по её идентификатору.
     *
     * @param id идентификатор обновляемой достопримечательности
     * @param attractionDto обновлённые данные
     * @return обновлённый {@link AttractionDto}
     * @throws NoSuchElementException если достопримечательность не найдена
     */
    public AttractionDto updateAttraction(UUID id, AttractionDto attractionDto) {
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Attraction not found"));
        attraction.setName(attractionDto.getName());
        attraction.setDescription(attractionDto.getDescription());
        attraction.setAttractionType(attractionDto.getAttractionType());
        attraction.setAddress(addressMapper.toEntity(attractionDto.getAddress()));
        attraction.setTicketInfo(ticketInfoMapper.toEntity(attractionDto.getTicketInfo()));
        attraction.setServices(attractionDto.getServices().stream()
                .map(serviceMapper::toEntity)
                .collect(Collectors.toList()));
        return attractionMapper.toDto(attractionRepository.save(attraction));
    }

    /**
     * Удаляет достопримечательность по её идентификатору.
     *
     * @param id идентификатор достопримечательности
     * @throws NoSuchElementException если достопримечательность не найдена
     */
    public void deleteAttraction(UUID id) {
        if (!attractionRepository.existsById(id)) {
            throw new NoSuchElementException("Attraction not found");
        }
        attractionRepository.deleteById(id);
    }

    /** Удаляет все достопримечательности. */
    public void deleteAll() {
        attractionRepository.deleteAll();
    }

    /**
     * Возвращает список достопримечательностей в указанном городе.
     *
     * @param city город для поиска достопримечательностей
     * @return список {@link AttractionDto} для достопримечательностей в этом городе
     * @throws NoSuchElementException если достопримечательности не найдены
     */
    public List<AttractionDto> getAttractionsByCity(String city) {
        List<Attraction> attractions = attractionRepository.findByAddressCity(city);
        if (attractions.isEmpty()) {
            throw new NoSuchElementException("No attractions found in city: " + city);
        }
        return attractions.stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список достопримечательностей в указанной области.
     *
     * @param region регион для поиска достопримечательностей
     * @return список {@link AttractionDto} для достопримечательностей в этой области
     * @throws NoSuchElementException если достопримечательности не найдены
     */
    public List<AttractionDto> getAttractionsByRegion(String region) {
        List<Attraction> attractions = attractionRepository.findByAddressRegion(region);
        if (attractions.isEmpty()) {
            throw new NoSuchElementException("No attractions found in region: " + region);
        }
        return attractions.stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список достопримечательностей, предоставляющих указанную услугу.
     *
     * @param serviceName имя услуги для поиска достопримечательностей
     * @return список {@link AttractionDto} для достопримечательностей с данной услугой
     * @throws NoSuchElementException если достопримечательности с такой услугой не найдены
     */
    public List<AttractionDto> getAttractionsByServices(String serviceName) {
        List<Attraction> attractions = attractionRepository.findByServicesName(serviceName);
        if (attractions.isEmpty()) {
            throw new NoSuchElementException("No attractions found with service: " + serviceName);
        }
        return attractions.stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());

    }

    /** /**
     * Выполняет поиск достопримечательностей по названию.
     *
     * @param name имя достопримечательностей для поиска
     * @return список {@link AttractionDto} для достопримечательностей, в названии которых содержится переданное значение
     * @throws NoSuchElementException если достопримечательности с таким именем не найдены
     */
    public List<AttractionDto> searchByName(String name) {
        List<Attraction> attractions = attractionRepository.findByNameContaining(name);
            if (attractions.isEmpty()) {
                throw new NoSuchElementException("No attractions found with name containing: " + name);
            }
        return attractions.stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }
}
