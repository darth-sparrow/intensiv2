package com.example.intensiv2;

import com.example.intensiv2.dto.AddressDto;
import com.example.intensiv2.repositories.AddressRepository;
import com.example.intensiv2.services.AddressService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.util.List;
import java.util.NoSuchElementException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
public class AddressServiceIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    void tearDown() {
        addressRepository.deleteAll();
    }

    @Test
    void testCreateAddress() {
        AddressDto createdAddress = addressService.createAddress(AddressDto.builder()
                .building(60)
                .street("Pushkina")
                .city("Minsk")
                .region("Minsk region")
                .build());
        assertThat(createdAddress).isNotNull();
        assertThat(createdAddress.getBuilding()).isEqualTo(60);
        assertThat(createdAddress.getStreet()).isEqualTo("Pushkina");
        assertThat(createdAddress.getCity()).isEqualTo("Minsk");
        assertThat(createdAddress.getRegion()).isEqualTo("Minsk region");
    }

    @Test
    void testGetAddress() {
        AddressDto createdAddress = addressService.createAddress(AddressDto.builder()
                .building(60)
                .street("Pushkina")
                .city("Minsk")
                .region("Minsk region")
                .build());
        AddressDto addressDto = addressService.getAddress(createdAddress.getId());
        assertThat(addressDto).isNotNull();
        assertThat(addressDto.getBuilding()).isEqualTo(60);
        assertThat(addressDto.getStreet()).isEqualTo("Pushkina");
        assertThat(addressDto.getCity()).isEqualTo("Minsk");
        assertThat(addressDto.getRegion()).isEqualTo("Minsk region");
    }

    @Test
    void testGetAllAddresses() {
        addressService.createAddress(AddressDto.builder()
                .building(60)
                .street("Pushkina")
                .city("Minsk")
                .region("Minsk region")
                .build());
        addressService.createAddress(AddressDto.builder()
                .building(28)
                .street("Mogilevskaya")
                .city("Orsha")
                .region("Vitebsk region")
                .build());
        List<AddressDto> addresses = addressService.getAllAddresses();
        assertThat(addresses).hasSize(2);
    }

    @Test
    void testUpdateAddress() {
        AddressDto createdAddress = addressService.createAddress(AddressDto.builder()
                .building(60)
                .street("Pushkina")
                .city("Minsk")
                .region("Minsk region")
                .build());
        createdAddress.setStreet("Mogilevskaya");
        AddressDto updatedAddress = addressService.updateAddress(createdAddress.getId(), createdAddress);
        assertThat(updatedAddress).isNotNull();
        assertThat(updatedAddress.getStreet()).isEqualTo("Mogilevskaya");
    }

    @Test
    void testDeleteAddress() {
        AddressDto createdAddress = addressService.createAddress(AddressDto.builder()
                .building(60)
                .street("Pushkina")
                .city("Minsk")
                .region("Minsk region")
                .build());
        addressService.deleteAddress(createdAddress.getId());
        assertThatThrownBy(() -> addressService.getAddress(createdAddress.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Address not found");
    }

    @Test
    void testDeleteAllAddresses() {
        addressService.createAddress(AddressDto.builder()
                .building(60)
                .street("Pushkina")
                .city("Minsk")
                .region("Minsk region")
                .build());
        addressService.createAddress(AddressDto.builder()
                .building(28)
                .street("Mogilevskaya")
                .city("Orsha")
                .region("Vitebsk region")
                .build());
        List<AddressDto> addresses = addressService.getAllAddresses();
        assertThat(addresses).hasSize(2);
        addressService.deleteAll();
        List<AddressDto> addresses1 = addressService.getAllAddresses();
        assertThat(addresses1).isEmpty();
    }

}
