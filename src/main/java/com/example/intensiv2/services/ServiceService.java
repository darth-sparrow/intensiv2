package com.example.intensiv2.services;

import com.example.intensiv2.dto.ServiceDto;
import com.example.intensiv2.mappers.ServiceMapper;
import com.example.intensiv2.repositories.ServiceRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления услугами.
 * Предоставляет CRUD-операции над сущностью {@link com.example.intensiv2.models.Service}.
 */
@Service
@RequiredArgsConstructor
public class ServiceService {
    /** Репозиторий для работы с сущностями услуг. */
    private final ServiceRepository serviceRepository;
    /** * Маппер для преобразования между {@link com.example.intensiv2.models.Service} и {@link ServiceDto}. */
    private final ServiceMapper serviceMapper;

    /**
     * Создаёт новую услугу, если она ещё не существует.
     *
     * @param serviceDto DTO с данными услуги
     * @return созданный {@link ServiceDto}
     * @throws IllegalArgumentException если услуга с таким именем и типом уже существует
     */
    public ServiceDto createService(ServiceDto serviceDto) {

        if (serviceRepository.findByNameAndServiceType(serviceDto.getName(), serviceDto.getServiceType()).isPresent()) {
            throw new IllegalArgumentException("Service already exists");
        }
        com.example.intensiv2.models.Service service = serviceMapper.toEntity(serviceDto);
        return serviceMapper.toDto(serviceRepository.save(service));
    }

    /**
     * Возвращает список всех услуг.
     *
     * @return список {@link ServiceDto}
     */
    public List<ServiceDto> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Получает услугу по её идентификатору.
     *
     * @param id уникальный идентификатор услуги
     * @return {@link ServiceDto}, соответствующий переданному ID
     * @throws NoSuchElementException если услуга не найдена
     */
    public ServiceDto getService(UUID id) {
        com.example.intensiv2.models.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Service not found"));
        return serviceMapper.toDto(service);
    }

    /**
     * Обновляет информацию об услуге.
     *
     * @param id         идентификатор обновляемой услуги
     * @param serviceDto DTO с новыми данными
     * @return обновлённый {@link ServiceDto}
     * @throws NoSuchElementException если услуга не найдена
     */
    public ServiceDto updateService(UUID id, ServiceDto serviceDto) {
        com.example.intensiv2.models.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Service not found"));
        service.setName(serviceDto.getName());
        service.setDescription(serviceDto.getDescription());
        service.setServiceType(serviceDto.getServiceType());
        return serviceMapper.toDto(serviceRepository.save(service));
    }

    /**
     * Удаляет услугу по её идентификатору.
     *
     * @param id идентификатор услуги
     * @throws NoSuchElementException если услуга не найдена
     */
    public void deleteService(UUID id) {
        if (!serviceRepository.existsById(id)) {
            throw new NoSuchElementException("Service not found");
        }
        serviceRepository.deleteById(id);
    }

    /** Удаляет все услуги из базы данных. */
    public void deleteAll() {
        serviceRepository.deleteAll();
    }
}