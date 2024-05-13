package com.agap.management.application.services;

import com.agap.management.application.ports.ICropService;
import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.ports.ISupplyService;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.domain.entities.Pesticide;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyService implements ISupplyService {

    private final ICropService cropService;
    private final IEmailService emailService;

    //@Scheduled(fixedRate = 10000)   // Ejecuta cada 10 segundos (para pruebas)
    @Scheduled(cron = "0 0 5 * * *")  // Ejecuta a las 6:00 AM todos los días
    @Override
    public void validateCropList() {
        List<CropResponseDTO> cropList = cropService.findAll();
        LocalDate today = LocalDate.now();

        cropList.parallelStream().forEach(crop -> {
            validateSupplyNecessity(crop, today, Fertilizer.class);
            validateSupplyNecessity(crop, today, Pesticide.class);
        });
    }

    private <T> void validateSupplyNecessity(CropResponseDTO crop, LocalDate today, Class<T> supplyClass) {
        CropTypeResponseDTO cropType = crop.getProjectApplication().getProject().getCropType();
        ProjectApplicationResponseDTO projectApplication = crop.getProjectApplication();

        float totalPlantAmount = (float) cropType.getPlantQuantityPerSquareMeter() /
                projectApplication.getArea();
        int supplyFrequency = supplyClass.equals(Fertilizer.class) ? cropType.getFertilizerFrequency() :
                cropType.getPesticideFrequency();
        int supplyQuantityPerPlant = supplyClass.equals(Fertilizer.class) ? cropType.getFertilizerQuantityPerPlant() :
                cropType.getPesticideQuantityPerPlant();

        LocalDate startDate = crop.getStartDate();
        LocalDate endDate = crop.getEndDate();

        long daysBetweenStartAndToday = ChronoUnit.DAYS.between(startDate, today);
        long daysBetweenTodayAndEnd = ChronoUnit.DAYS.between(today, endDate);

        if (today.isBefore(endDate) && daysBetweenTodayAndEnd >= supplyFrequency) {
            if(daysBetweenStartAndToday % supplyFrequency == 0) {
                float supplyAmountToApply = totalPlantAmount * supplyQuantityPerPlant;
                sendSupplyNotification(projectApplication.getApplicant().getEmail(),
                        supplyAmountToApply, (supplyClass.equals(Fertilizer.class) ? "fertilizante" : "pesticida"));

            }
        }
    }

    private void sendSupplyNotification(String to, float supplyAmount, String supplyType) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedAmount = df.format(supplyAmount);

        String subject = "Recordatorio de Mantenimiento de Cultivo";
        String bodyMessage = "Querido usuario de AGAP, recuerda que tu cultivo necesita recibir " +
                formattedAmount + " gr de " + supplyType + " el día de hoy.";

        try {
            emailService.sendEmail(to, subject, bodyMessage, null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
