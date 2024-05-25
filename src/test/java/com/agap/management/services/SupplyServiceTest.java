package com.agap.management.services;

import com.agap.management.application.ports.ICropService;
import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.services.SupplyService;
import com.agap.management.domain.dtos.UserDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import com.agap.management.domain.dtos.FertilizerDTO;
import com.agap.management.domain.dtos.PesticideDTO;
import com.agap.management.domain.enums.ProcessStatus;
import com.agap.management.domain.enums.WeatherType;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupplyServiceTest {

    @Mock
    private ICropService cropService;

    @Mock
    private IEmailService emailService;

    @InjectMocks
    private SupplyService supplyService;

    private CropResponseDTO crop;
    private ProjectApplicationResponseDTO projectApplication;
    private CropTypeResponseDTO cropType;
    private UserDTO user;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectResponseDTO project;

    @BeforeEach
    void setUp() {
        user = UserDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        FertilizerDTO fertilizer = FertilizerDTO.builder()
                .name("Fertilizante A")
                .brand("Marca A")
                .pricePerGram(0.1f)
                .build();

        PesticideDTO pesticide = PesticideDTO.builder()
                .name("Pesticida B")
                .brand("Marca B")
                .pricePerGram(0.2f)
                .build();

        cropType = CropTypeResponseDTO.builder()
                .plantQuantityPerSquareMeter(100)
                .fertilizerFrequency(7)
                .fertilizerQuantityPerPlant(10)
                .fertilizer(fertilizer)
                .pesticideFrequency(14)
                .pesticideQuantityPerPlant(5)
                .pesticide(pesticide)
                .weather(WeatherType.CALIDO)  // Proporcionar un valor para el campo weather
                .build();

        project = ProjectResponseDTO.builder()
                .id(1)
                .cropType(cropType)
                .name("Proyecto A")
                .status(ProcessStatus.CREADO)
                .startDate(LocalDate.now().minusDays(30))
                .endDate(LocalDate.now().plusDays(30))
                .municipality("Municipio A")
                .totalBudget(1000f)
                .imageUrl("http://example.com/image.jpg")
                .build();

        projectApplication = ProjectApplicationResponseDTO.builder()
                .applicant(user)
                .area(100)
                .project(project)
                .build();

        startDate = LocalDate.now().minusDays(14);
        endDate = LocalDate.now().plusDays(14);

        crop = CropResponseDTO.builder()
                .projectApplication(projectApplication)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    @Test
    void testValidateCropList() throws MessagingException {
        when(cropService.findAll()).thenReturn(Collections.singletonList(crop));

        supplyService.validateCropList();

        verify(emailService, times(2)).sendEmail(
                eq("john.doe@example.com"),
                anyString(),
                anyString(),
                isNull(),
                isNull()
        );
    }

    @Test
    void testValidateCropListNoEmailSent() throws MessagingException {
        endDate = LocalDate.now().minusDays(1);  // End date is in the past
        crop.setEndDate(endDate);

        when(cropService.findAll()).thenReturn(Collections.singletonList(crop));

        supplyService.validateCropList();

        verify(emailService, times(0)).sendEmail(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString()
        );
    }
}
