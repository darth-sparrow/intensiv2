package com.example.intensiv2.controllers;

import com.example.intensiv2.dto.AttractionDto;
import com.example.intensiv2.services.AttractionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с достопримечательностями.
 * Обрабатывает запросы для создания, получения, обновления и удаления достопримечательностей, а также поиска и фильтрации по различным критериям.
 * Каждый метод контроллера предоставляет соответствующий HTTP-метод для взаимодействия с достопримечательностями.
 */
@RestController
@RequestMapping("/attraction")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AttractionController {
    private final AttractionService attractionService;
    /**
     * Контроллер для работы с достопримечательностями.
     * Обрабатывает запросы для создания, получения, обновления и удаления достопримечательностей, а также поиска и фильтрации по различным критериям.
     * Каждый метод контроллера предоставляет соответствующий HTTP-метод для взаимодействия с достопримечательностями.
     */
    @PostMapping("/create")
    @Operation(summary = "Create attraction", description = "Creates a new attraction")
    public ResponseEntity<AttractionDto> createAttraction(@RequestBody AttractionDto attractionDto) {

        return ResponseEntity.ok(attractionService.createAttraction(attractionDto));

    }

    /**
     * Получает все достопримечательности.
     *
     * @return {@link ResponseEntity} с списком {@link AttractionDto} и статусом 200 OK
     */
    @GetMapping("/get")
    @Operation(summary = "Get all attractions", description = "Get all attractions from the database")
    public ResponseEntity<List<AttractionDto>> getAllAttractions() {
        return ResponseEntity.ok(attractionService.getAllAttractions());
    }

    /**
     * Получает достопримечательность по ID.
     *
     * @param id идентификатор достопримечательности
     * @return {@link ResponseEntity} с объектом {@link AttractionDto} и статусом 200 OK
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "Get attraction", description = "Get attraction by id from the database")
    public ResponseEntity<AttractionDto> getAttraction(@PathVariable UUID id) {
        return ResponseEntity.ok(attractionService.getAttraction(id));
    }

    /**
     * Обновляет достопримечательность по ID.
     *
     * @param id идентификатор достопримечательности
     * @param attractionDto объект, содержащий обновленную информацию о достопримечательности
     * @return {@link ResponseEntity} с объектом {@link AttractionDto} и статусом 200 OK
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update attraction", description = "Update attraction by id")
    public ResponseEntity<AttractionDto> updateAttraction(@PathVariable UUID id, @RequestBody AttractionDto attractionDto) {
        return ResponseEntity.ok(attractionService.updateAttraction(id, attractionDto));
    }

    /**
     * Удаляет достопримечательность по ID.
     *
     * @param id идентификатор достопримечательности
     * @return {@link ResponseEntity} с пустым телом и статусом 204 No Content
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete attraction", description = "Delete attraction by id from the database")
    public ResponseEntity<Void> deleteAttraction(@PathVariable UUID id) {
        attractionService.deleteAttraction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Удаляет все достопримечательности.
     *
     * @return {@link ResponseEntity} с пустым телом и статусом 204 No Content
     */
    @DeleteMapping("/delete")
    @Operation(summary = "Delete all attractions", description = "Delete all attractions from the database")
    public ResponseEntity<Void> deleteAllAttractions() {
        attractionService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Получает все достопримечательности по городу.
     *
     * @param city название города
     * @return {@link ResponseEntity} с списком {@link AttractionDto} и статусом 200 OK
     */
    @GetMapping("/city/{city}")
    @Operation(summary = "Get all attractions", description = "Get all attractions by city from the database")
    public ResponseEntity<List<AttractionDto>> getAttractionsByCity(@PathVariable String city) {

        return ResponseEntity.ok(attractionService.getAttractionsByCity(city));
    }

    /**
     * Получает все достопримечательности по региону.
     *
     * @param region название региона
     * @return {@link ResponseEntity} с списком {@link AttractionDto} и статусом 200 OK
     */
    @GetMapping("/region/{region}")
    @Operation(summary = "Get all attractions", description = "Get all attractions by region from the database")
    public ResponseEntity<List<AttractionDto>> getAttractionsByRegion(@PathVariable String region) {

        return ResponseEntity.ok(attractionService.getAttractionsByRegion(region));
    }

    /**
     * Получает все достопримечательности по имени услуги.
     *
     * @param serviceName название услуги
     * @return {@link ResponseEntity} с списком {@link AttractionDto} и статусом 200 OK
     */
    @GetMapping("/service/{serviceName}")
    @Operation(summary = "Get all attractions", description = "Get all attractions by serviceName from the database")
    public ResponseEntity<List<AttractionDto>> getAttractionsByServices(@PathVariable String serviceName) {
        return ResponseEntity.ok(attractionService.getAttractionsByServices(serviceName));
    }

    /**
     * Ищет достопримечательности по части названия.
     *
     * @param name часть названия достопримечательности для поиска
     * @return {@link ResponseEntity} с списком {@link AttractionDto} и статусом 200 OK
     */
    @GetMapping("/search")
    @Operation(summary = "Get all attractions", description = "Get all attractions by partial search from the database")
    public ResponseEntity<List<AttractionDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(attractionService.searchByName(name));
    }
}
