package com.example.intensiv2;

import com.example.intensiv2.dto.TicketInfoDto;
import com.example.intensiv2.repositories.TicketInfoRepository;
import com.example.intensiv2.services.TicketInfoService;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
public class TicketInfoIntegrationTest {

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
    private TicketInfoService ticketInfoService;

    @Autowired
    private TicketInfoRepository ticketInfoRepository;

    @AfterEach
    void tearDown() {
        ticketInfoRepository.deleteAll();
    }

    @Test
    void testCreateTicketInfo() {

        TicketInfoDto createdTicketInfo = ticketInfoService.createTicketInfo(TicketInfoDto.builder()
                .price(BigDecimal.valueOf(500.00))
                .currency("RUB")
                .availability(true)
                .build());
        assertThat(createdTicketInfo).isNotNull();
        assertThat(createdTicketInfo.getPrice()).isEqualTo(BigDecimal.valueOf(500.00));
        assertThat(createdTicketInfo.getCurrency()).isEqualTo("RUB");
        assertThat(createdTicketInfo.getAvailability()).isEqualTo(true);
    }

    @Test
    void testGetTicketInfo() {
        TicketInfoDto createdTicketInfo = ticketInfoService.createTicketInfo(TicketInfoDto.builder()
                .price(BigDecimal.valueOf(500.00))
                .currency("RUB")
                .availability(true)
                .build());
        TicketInfoDto ticketInfoDto = ticketInfoService.getTicketInfo(createdTicketInfo.getId());
        assertThat(ticketInfoDto).isNotNull();
        assertThat(ticketInfoDto.getPrice().compareTo(BigDecimal.valueOf(500.00))).isEqualTo(0);
        assertThat(ticketInfoDto.getCurrency()).isEqualTo("RUB");
        assertThat(ticketInfoDto.getAvailability()).isEqualTo(true);
    }

    @Test
    void testGetAllTicketInfo() {
        ticketInfoService.createTicketInfo(TicketInfoDto.builder()
                .price(BigDecimal.valueOf(500.00))
                .currency("RUB")
                .availability(true)
                .build());
        ticketInfoService.createTicketInfo(TicketInfoDto.builder()
                .price(BigDecimal.valueOf(600.00))
                .currency("BYN")
                .availability(true)
                .build());
        List<TicketInfoDto> ticketInfoDtos = ticketInfoService.getAllTicketInfo();
        assertThat(ticketInfoDtos).hasSize(2);
    }

    @Test
    void testUpdateTicketInfo() {
        TicketInfoDto createdTicketInfo = ticketInfoService.createTicketInfo(TicketInfoDto.builder()
                .price(BigDecimal.valueOf(500.00))
                .currency("RUB")
                .availability(true)
                .build());
        createdTicketInfo.setCurrency("BYN");
        TicketInfoDto updatedTicketInfo = ticketInfoService.updateTicketInfo(createdTicketInfo.getId(), createdTicketInfo);
        assertThat(updatedTicketInfo).isNotNull();
        assertThat(updatedTicketInfo.getCurrency()).isEqualTo("BYN");
    }

    @Test
    void testDeleteTicketInfo() {
        TicketInfoDto createdTicketInfo = ticketInfoService.createTicketInfo(TicketInfoDto.builder()
                .price(BigDecimal.valueOf(500.00))
                .currency("RUB")
                .availability(true)
                .build());
        ticketInfoService.deleteTicketInfo(createdTicketInfo.getId());
        assertThatThrownBy(() -> ticketInfoService.getTicketInfo(createdTicketInfo.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("TicketInfo not found");
    }

    @Test
    void testDeleteAllTicketInfo() {
        ticketInfoService.createTicketInfo(TicketInfoDto.builder()
                .price(BigDecimal.valueOf(500.00))
                .currency("RUB")
                .availability(true)
                .build());
        ticketInfoService.createTicketInfo(TicketInfoDto.builder()
                .price(BigDecimal.valueOf(600.00))
                .currency("BYN")
                .availability(true)
                .build());
        List<TicketInfoDto> ticketInfoDtos = ticketInfoService.getAllTicketInfo();
        assertThat(ticketInfoDtos).hasSize(2);
        ticketInfoService.deleteAll();
        List<TicketInfoDto> ticketInfoDtos1 = ticketInfoService.getAllTicketInfo();
        assertThat(ticketInfoDtos1).isEmpty();
    }
}
