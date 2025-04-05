package com.example.intensiv2;

import com.example.intensiv2.dto.ServiceDto;
import com.example.intensiv2.repositories.ServiceRepository;
import com.example.intensiv2.services.ServiceService;
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
import static com.example.intensiv2.models.ServiceType.TOUR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
public class ServiceServiceIntegrationTest {
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
    private ServiceService serviceService;

    @Autowired
    private ServiceRepository serviceRepository;

    @AfterEach
    void tearDown() {
        serviceRepository.deleteAll();
    }

    @Test
    void testCreateService() {

        ServiceDto createdService = serviceService.createService(ServiceDto.builder()
                .name("Historical City Tour")
                .description("Explore the rich history and culture of the city with our guided tour.")
                .serviceType(TOUR)
                .build());
        assertThat(createdService).isNotNull();
        assertThat(createdService.getName()).isEqualTo("Historical City Tour");
        assertThat(createdService.getDescription()).isEqualTo("Explore the rich history and culture of the city with our guided tour.");
        assertThat(createdService.getServiceType()).isEqualTo(TOUR);
    }

    @Test
    void testGetService() {
        ServiceDto createdService = serviceService.createService(ServiceDto.builder()
                .name("Historical City Tour")
                .description("Explore the rich history and culture of the city with our guided tour.")
                .serviceType(TOUR)
                .build());
        ServiceDto serviceDto = serviceService.getService(createdService.getId());
        assertThat(serviceDto).isNotNull();
        assertThat(serviceDto.getName()).isEqualTo("Historical City Tour");
        assertThat(serviceDto.getDescription()).isEqualTo("Explore the rich history and culture of the city with our guided tour.");
        assertThat(serviceDto.getServiceType()).isEqualTo(TOUR);
    }

    @Test
    void testGetAllService() {
        serviceService.createService(ServiceDto.builder()
                .name("Historical City Tour")
                .description("Explore the rich history and culture of the city with our guided tour.")
                .serviceType(TOUR)
                .build());
        serviceService.createService(ServiceDto.builder()
                .name("Adventure Hiking Experience")
                .description("Join us for an unforgettable hiking adventure in the mountains.")
                .serviceType(TOUR)
                .build());
        List<ServiceDto> services = serviceService.getAllServices();
        assertThat(services).hasSize(2);
    }

    @Test
    void testUpdateService() {
        ServiceDto createdService = serviceService.createService(ServiceDto.builder()
                .name("Historical City Tour")
                .description("Explore the rich history and culture of the city with our guided tour.")
                .serviceType(TOUR)
                .build());
        createdService.setName("Adventure Hiking Experience");
        ServiceDto updatedService = serviceService.updateService(createdService.getId(), createdService);
        assertThat(updatedService).isNotNull();
        assertThat(updatedService.getName()).isEqualTo("Adventure Hiking Experience");
    }

    @Test
    void testDeleteService() {
        ServiceDto createdService = serviceService.createService(ServiceDto.builder()
                .name("Historical City Tour")
                .description("Explore the rich history and culture of the city with our guided tour.")
                .serviceType(TOUR)
                .build());
        serviceService.deleteService(createdService.getId());
        assertThatThrownBy(() -> serviceService.getService(createdService.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Service not found");
    }

    @Test
    void testDeleteAllServices() {
        serviceService.createService(ServiceDto.builder()
                .name("Historical City Tour")
                .description("Explore the rich history and culture of the city with our guided tour.")
                .serviceType(TOUR)
                .build());
        serviceService.createService(ServiceDto.builder()
                .name("Adventure Hiking Experience")
                .description("Join us for an unforgettable hiking adventure in the mountains.")
                .serviceType(TOUR)
                .build());

        List<ServiceDto> services = serviceService.getAllServices();
        assertThat(services).hasSize(2);
        serviceService.deleteAll();
        List<ServiceDto> services1 = serviceService.getAllServices();
        assertThat(services1).isEmpty();
    }
}