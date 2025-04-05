package com.example.intensiv2.controllers;

import com.example.intensiv2.dto.AddressDto;
import com.example.intensiv2.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Контроллер для работы с адресами.
 * Предоставляет API для создания, получения, обновления и удаления адресов.
 * Все операции выполняются через HTTP-запросы и взаимодействуют с {@link AddressService}.
 */
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    /**
     * Сервис для работы с адресами.
     * Содержит бизнес-логику для создания, получения, обновления и удаления адресов.
     */
    private final AddressService addressService;

    /**
     * Создаёт новый адрес.
     *
     * @param addressDto DTO с данными адреса
     * @return {@link ResponseEntity} с созданным {@link AddressDto}
     */
    @PostMapping("/create")
    @Operation(summary = "Create address", description = "Creates a new address")
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.createAddress(addressDto));
    }

    /**
     * Получает все адреса из базы данных.
     *
     * @return {@link ResponseEntity} с списком всех адресов в виде {@link List<AddressDto>}
     */
    @GetMapping("/get")
    @Operation(summary = "Get all addresses", description = "Get all addresses from the database")
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    /**
     * Получает все адреса из базы данных.
     *
     * @return {@link ResponseEntity} с списком всех адресов в виде {@link List<AddressDto>}
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "Get address", description = "Get address by id from the database")
    public ResponseEntity<AddressDto> getAddress(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.getAddress(id));
    }

    /**
     * Обновляет данные адреса по его идентификатору.
     *
     * @param id         уникальный идентификатор адреса
     * @param addressDto DTO с новыми данными адреса
     * @return {@link ResponseEntity} с обновлённым {@link AddressDto}
     * @throws NoSuchElementException если адрес с таким идентификатором не найден
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update address", description = "Update address by id")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable UUID id, @RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.updateAddress(id, addressDto));
    }

    /**
     * Удаляет адрес по его идентификатору.
     *
     * @param id уникальный идентификатор адреса
     * @return {@link ResponseEntity} с пустым телом и статусом 204 (No Content)
     * @throws NoSuchElementException если адрес с таким идентификатором не найден
     */
    @DeleteMapping("delete/{id}")
    @Operation(summary = "Delete address", description = "Delete address by id from the database")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Удаляет все адреса из базы данных.
     *
     * @return {@link ResponseEntity} с пустым телом и статусом 204 (No Content)
     */

    @DeleteMapping("/delete/all")
    @Operation(summary = "Delete all addresses", description = "Delete all addresses from the database")
    public ResponseEntity<Void> deleteAll() {
        addressService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
