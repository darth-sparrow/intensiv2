package com.example.intensiv2.controllers;

import com.example.intensiv2.dto.TicketInfoDto;
import com.example.intensiv2.services.TicketInfoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Контроллер для работы с информацией о билетах (ticketInfo).
 * Предоставляет API для создания, получения, обновления и удаления информации о билетах.
 * Все операции выполняются через HTTP-запросы и взаимодействуют с {@link TicketInfoService}.
 */
@RestController
@RequestMapping("/ticket-info")
@RequiredArgsConstructor
public class TicketInfoController {
    /**
     * Сервис для работы с информацией о билетах.
     * Содержит бизнес-логику для создания, получения, обновления и удаления информации о билетах.
     */
    private final TicketInfoService ticketInfoService;

    /**
     * Создаёт новую информацию о билете.
     *
     * @param ticketInfoDto DTO с данными для создания информации о билете
     * @return {@link ResponseEntity} с созданной {@link TicketInfoDto}
     */
    @PostMapping("/create")
    @Operation(summary = "Create ticketInfo", description = "Creates a new ticketInfo")
    public ResponseEntity<TicketInfoDto> createTicketInfo(@RequestBody TicketInfoDto ticketInfoDto) {
        return ResponseEntity.ok(ticketInfoService.createTicketInfo(ticketInfoDto));
    }

    /**
     * Получает всю информацию о билетах из базы данных.
     *
     * @return {@link ResponseEntity} с списком всех {@link TicketInfoDto}
     */
    @GetMapping("/get")
    @Operation(summary = "Get all  ticketInfo", description = "Get all ticketInfo from the database")
    public ResponseEntity<List<TicketInfoDto>> getAllTicketInfo() {
        return ResponseEntity.ok(ticketInfoService.getAllTicketInfo());
    }

    /**
     * Получает всю информацию о билетах из базы данных.
     *
     * @return {@link ResponseEntity} с списком всех {@link TicketInfoDto}
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "Get  ticketInfo", description = "Get  ticketInfo by id from the database")
    public ResponseEntity<TicketInfoDto> getTicketInfo(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketInfoService.getTicketInfo(id));
    }

    /**
     * Обновляет информацию о билете по его идентификатору.
     *
     * @param id уникальный идентификатор информации о билете
     * @param ticketInfoDto DTO с новыми данными информации о билете
     * @return {@link ResponseEntity} с обновлённой {@link TicketInfoDto}
     * @throws NoSuchElementException если информация о билете с таким идентификатором не найдена
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update  ticketInfo", description = "Update  ticketInfo by id")
    public ResponseEntity<TicketInfoDto> updateTicketInfo(@PathVariable UUID id, @RequestBody TicketInfoDto ticketInfoDto) {
        return ResponseEntity.ok(ticketInfoService.updateTicketInfo(id, ticketInfoDto));
    }

    /**
     * Удаляет информацию о билете по его идентификатору.
     *
     * @param id уникальный идентификатор информации о билете
     * @return {@link ResponseEntity} с пустым телом и статусом 204 (No Content)
     * @throws NoSuchElementException если информация о билете с таким идентификатором не найдена
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete  ticketInfo", description = "Delete  ticketInfo by id from the database")
    public ResponseEntity<Void> deleteTicketInfo(@PathVariable UUID id) {
        ticketInfoService.deleteTicketInfo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Удаляет всю информацию о билетах из базы данных.
     *
     * @return {@link ResponseEntity} с пустым телом и статусом 204 (No Content)
     */
    @DeleteMapping("/delete")
    @Operation(summary = "Delete all  ticketInfo", description = "Delete all  ticketInfo from the database")
    public ResponseEntity<Void> deleteAllTicketInfo() {
        ticketInfoService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}

