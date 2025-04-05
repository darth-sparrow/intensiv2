package com.example.intensiv2;

import com.example.intensiv2.dto.AddressDto;
import com.example.intensiv2.dto.AttractionDto;
import com.example.intensiv2.dto.ServiceDto;
import com.example.intensiv2.dto.TicketInfoDto;
import com.example.intensiv2.repositories.AttractionRepository;
import com.example.intensiv2.services.AttractionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import static com.example.intensiv2.models.AttractionType.MUSEUM;
import static com.example.intensiv2.models.ServiceType.TOUR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
public class AttractionServiceIntegrationTest {
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
    private AttractionService attractionService;

    @Autowired
    private AttractionRepository attractionRepository;

    private AttractionDto attractionDto;
    private AddressDto addressDto;
    private ServiceDto serviceDto;
    private TicketInfoDto ticketInfoDto;

    @AfterEach
    void tearDown() {
        attractionRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        addressDto = AddressDto.builder()
                .building(60)
                .street("Pushkina")
                .city("Minsk")
                .region("Minsk region")
                .build();
        serviceDto = ServiceDto.builder()
                .name("Historical City Tour")
                .description("Explore the rich history and culture of the city with our guided tour.")
                .serviceType(TOUR)
                .build();
        ticketInfoDto = TicketInfoDto.builder()
                .price(BigDecimal.valueOf(500.00))
                .currency("RUB")
                .availability(true)
                .build();
        attractionDto = AttractionDto.builder()
                .name("Art Museum")
                .description("The largest art museum in Belarus.")
                .attractionType(MUSEUM)
                .address(addressDto)
                .services(List.of(serviceDto))
                .ticketInfo(ticketInfoDto)
                .build();
    }

    @Test
    void testCreateAttraction() {
        AttractionDto createdAttraction = attractionService.createAttraction(attractionDto);
        assertThat(createdAttraction).isNotNull();
        assertThat(createdAttraction.getId()).isNotNull();
        assertThat(createdAttraction.getName()).isEqualTo("Art Museum");
        assertThat(createdAttraction.getDescription()).isEqualTo("The largest art museum in Belarus.");
        assertThat(createdAttraction.getAddress().getStreet()).isEqualTo("Pushkina");
        assertThat(createdAttraction.getAddress().getBuilding()).isEqualTo(60);
        assertThat(createdAttraction.getAddress().getCity()).isEqualTo("Minsk");
        assertThat(createdAttraction.getAddress().getRegion()).isEqualTo("Minsk region");
        assertThat(createdAttraction.getTicketInfo().getPrice().compareTo(BigDecimal.valueOf(500.00))).isEqualTo(0);
        assertThat(createdAttraction.getTicketInfo().getCurrency()).isEqualTo("RUB");
        assertThat(createdAttraction.getTicketInfo().getAvailability()).isEqualTo(true);
        assertThat(createdAttraction.getServices().size()).isEqualTo(1);
        assertThat(createdAttraction.getServices().get(0).getName()).isEqualTo("Historical City Tour");
        assertThat(createdAttraction.getServices().get(0).getDescription()).isEqualTo("Explore the rich history and culture of the city with our guided tour.");
        assertThat(createdAttraction.getServices().get(0).getServiceType()).isEqualTo(TOUR);
    }

    @Test
    void testGetAttraction() {
        AttractionDto attraction = attractionService.createAttraction(attractionDto);
        AttractionDto createdAttraction = attractionService.getAttraction(attraction.getId());
        assertThat(createdAttraction).isNotNull();
        assertThat(createdAttraction.getId()).isNotNull();
        assertThat(createdAttraction.getName()).isEqualTo("Art Museum");
        assertThat(createdAttraction.getDescription()).isEqualTo("The largest art museum in Belarus.");
        assertThat(createdAttraction.getAddress().getStreet()).isEqualTo("Pushkina");
        assertThat(createdAttraction.getAddress().getBuilding()).isEqualTo(60);
        assertThat(createdAttraction.getAddress().getCity()).isEqualTo("Minsk");
        assertThat(createdAttraction.getAddress().getRegion()).isEqualTo("Minsk region");
        assertThat(createdAttraction.getTicketInfo().getPrice().compareTo(BigDecimal.valueOf(500.00))).isEqualTo(0);
        assertThat(createdAttraction.getTicketInfo().getCurrency()).isEqualTo("RUB");
        assertThat(createdAttraction.getTicketInfo().getAvailability()).isEqualTo(true);
        assertThat(createdAttraction.getServices().size()).isEqualTo(1);
        assertThat(createdAttraction.getServices().get(0).getName()).isEqualTo("Historical City Tour");
        assertThat(createdAttraction.getServices().get(0).getDescription()).isEqualTo("Explore the rich history and culture of the city with our guided tour.");
        assertThat(createdAttraction.getServices().get(0).getServiceType()).isEqualTo(TOUR);
    }

    @Test
    void testGetAttraction_ThrowException() {
        UUID nonExistentId = UUID.randomUUID();
        assertThatThrownBy(() -> attractionService.getAttraction(nonExistentId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Attraction not found");
    }


    @Test
    void testGetAllAttractions() {
        attractionService.createAttraction(attractionDto);
        List<AttractionDto> attractionDtos = attractionService.getAllAttractions();
        assertThat(attractionDtos.size()).isEqualTo(1);
    }


    @Test
    void testUpdateAttraction() {
        AttractionDto attraction = attractionService.createAttraction(attractionDto);
        attraction.setName("Historical Museum");
        AttractionDto updatedAttraction = attractionService.updateAttraction(attraction.getId(), attraction);
        assertThat(updatedAttraction).isNotNull();
        assertThat(updatedAttraction.getName()).isEqualTo("Historical Museum");
    }

    @Test
    void testDeleteAttraction() {
        AttractionDto createdAttraction = attractionService.createAttraction(attractionDto);
        attractionService.deleteAttraction(createdAttraction.getId());
        assertThatThrownBy(() -> attractionService.getAttraction(createdAttraction.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Attraction not found");
    }

    @Test
    void testDeleteAllAddresses() {
        attractionService.createAttraction(attractionDto);
        List<AttractionDto> attractionDtos = attractionService.getAllAttractions();
        Assertions.assertThat(attractionDtos).hasSize(1);
        attractionService.deleteAll();
        List<AttractionDto> attractionDtos1= attractionService.getAllAttractions();
        Assertions.assertThat(attractionDtos1).isEmpty();
    }
}