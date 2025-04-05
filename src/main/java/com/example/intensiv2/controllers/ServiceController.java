package com.example.intensiv2.controllers;

import com.example.intensiv2.dto.ServiceDto;
import com.example.intensiv2.services.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Контроллер для работы с услугами.
 * Предоставляет API для создания, получения, обновления и удаления услуг.
 * Все операции выполняются через HTTP-запросы и взаимодействуют с {@link ServiceService}.
 */
@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {
    /**
     * Сервис для работы с услугами.
     * Содержит бизнес-логику для создания, получения, обновления и удаления услуг.
     */
    private final ServiceService serviceService;

    /**
     * Создаёт новую услугу.
     *
     * @param serviceDto DTO с данными услуги
     * @return {@link ResponseEntity} с созданной {@link ServiceDto}
     */
    @PostMapping("/create")
    @Operation(summary = "Create service", description = "Creates a new service")
    public ResponseEntity<ServiceDto> createService(@RequestBody ServiceDto serviceDto) {
        return ResponseEntity.ok(serviceService.createService(serviceDto));
    }

    /**
     * Получает все услуги из базы данных.
     *
     * @return {@link ResponseEntity} с списком всех услуг в виде {@link List<ServiceDto>}
     */
    @GetMapping("/get")
    @Operation(summary = "Get all services", description = "Get all services from the database")
    public ResponseEntity<List<ServiceDto>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    /**
     * Получает услугу по её идентификатору.
     *
     * @param id уникальный идентификатор услуги
     * @return {@link ResponseEntity} с найденной {@link ServiceDto}
     * @throws NoSuchElementException если услуга с таким идентификатором не найдена
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "Get service", description = "Get services by id from the database")
    public ResponseEntity<ServiceDto> getService(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceService.getService(id));
    }

    /**
     * Обновляет данные услуги по её идентификатору.
     *
     * @param id уникальный идентификатор услуги
     * @param serviceDto DTO с новыми данными услуги
     * @return {@link ResponseEntity} с обновлённой {@link ServiceDto}
     * @throws NoSuchElementException если услуга с таким идентификатором не найдена
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update service", description = "Update service by id")
    public ResponseEntity<ServiceDto> updateService(@PathVariable UUID id, @RequestBody ServiceDto serviceDto) {
        return ResponseEntity.ok(serviceService.updateService(id, serviceDto));
    }

    /**
     * Удаляет услугу по её идентификатору.
     *
     * @param id уникальный идентификатор услуги
     * @return {@link ResponseEntity} с пустым телом и статусом 204 (No Content)
     * @throws NoSuchElementException если услуга с таким идентификатором не найдена
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete service", description = "Delete service by id from the database")
    public ResponseEntity<Void> deleteService(@PathVariable UUID id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Удаляет все услуги из базы данных.
     *
     * @return {@link ResponseEntity} с пустым телом и статусом 204 (No Content)
     */
    @DeleteMapping("/delete")
    @Operation(summary = "Delete all services", description = "Delete all services from the database")
    public ResponseEntity<Void> deleteAllServices() {
        serviceService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
