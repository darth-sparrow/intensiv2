package com.example.intensiv2.services;

import com.example.intensiv2.dto.AddressDto;
import com.example.intensiv2.mappers.AddressMapper;
import com.example.intensiv2.models.Address;
import com.example.intensiv2.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для управления адресами.
 * Предоставляет CRUD-операции над сущностью {@link Address}, включая проверку уникальности по полям здания, улицы и города.
 */
@Service
@RequiredArgsConstructor
public class AddressService {
    /** Репозиторий для доступа к данным об адресах. */
    private final AddressRepository addressRepository;
    /** Маппер для преобразования между {@link Address} и {@link AddressDto}. */
    private final AddressMapper addressMapper;

    /**
     * Создает новый адрес на основе переданного DTO.
     *
     * @param addressDto DTO с данными адреса
     * @return созданный {@link AddressDto}
     * @throws IllegalArgumentException если такой адрес уже существует (по зданию, улице и городу)
     */
    public AddressDto createAddress(AddressDto addressDto) {
        if(addressRepository.findByBuildingAndStreetAndCity(addressDto.getBuilding(), addressDto.getStreet(), addressDto.getCity()).isPresent()){
            throw new IllegalArgumentException("Address already exists");
        }
        Address address = addressMapper.toEntity(addressDto);
        return addressMapper.toDto(addressRepository.save(address));
    }

    /**
     * Получает список всех адресов.
     *
     * @return список {@link AddressDto}
     */
    public List<AddressDto> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Получает адрес по его идентификатору.
     *
     * @param id уникальный идентификатор адреса
     * @return {@link AddressDto}, соответствующий ID
     * @throws NoSuchElementException если адрес не найден
     */
    public AddressDto getAddress(UUID id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Address not found"));
        return addressMapper.toDto(address);
    }

    /**
     * Обновляет данные об адресе по его ID.
     *
     * @param id идентификатор обновляемого адреса
     * @param addressDto DTO с обновлёнными данными
     * @return обновлённый {@link AddressDto}
     * @throws NoSuchElementException если адрес не найден
     */
    public AddressDto updateAddress(UUID id, AddressDto addressDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Address not found"));
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setRegion(addressDto.getRegion());
        address.setBuilding(addressDto.getBuilding());
        return addressMapper.toDto(addressRepository.save(address));
    }

    /**
     * Удаляет адрес по его идентификатору.
     *
     * @param id идентификатор адреса
     * @throws NoSuchElementException если адрес не найден
     */
    public void deleteAddress(UUID id) {
        if (!addressRepository.existsById(id)) {
            throw new NoSuchElementException("Address not found");
        }
        addressRepository.deleteById(id);
    }

    /** Удаляет все адреса из базы данных. */
    public void deleteAll() {
        addressRepository.deleteAll();
    }
}