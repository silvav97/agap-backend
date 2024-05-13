package com.agap.management.application.services;

import com.agap.management.application.ports.ICropService;
import com.agap.management.application.ports.ISupplyService;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyService implements ISupplyService {

    private final ICropService cropService;

    @Override
    public void validateCropList() {
        List<CropResponseDTO> cropList = cropService.findAll();
        LocalDate today = LocalDate.now();

        cropList.parallelStream().forEach(crop -> {
            CropTypeResponseDTO cropType = crop.getProjectApplication().getProject().getCropType();
            int fertilizerFrequency = cropType.getFertilizerFrequency();
            int pesticideFrequency = cropType.getPesticideFrequency();
            int totalPlantAmount = cropType.getPlantQuantityPerSquareMeter()/
                    crop.getProjectApplication().getArea();

            LocalDate startDate = crop.getStartDate();
            LocalDate endDate = crop.getEndDate();

            long daysBetweenStartAndToday = ChronoUnit.DAYS.between(startDate, today);
            long daysBetweenTodayAndEnd = ChronoUnit.DAYS.between(today, endDate);

            if (today.isBefore(endDate) && daysBetweenTodayAndEnd >= fertilizerFrequency) {
                if(daysBetweenStartAndToday % fertilizerFrequency == 0) {
                    int fertilizerAmountToApply = totalPlantAmount*cropType.getFertilizerQuantityPerPlant();
                    System.out.printf("Hay que aplicar %d gr de fertilizante hoy ", fertilizerAmountToApply);
                }
            }

            if (today.isBefore(endDate) && daysBetweenTodayAndEnd >= pesticideFrequency) {
                if(daysBetweenStartAndToday % pesticideFrequency == 0) {
                    int pesticideAmountToApply = totalPlantAmount*cropType.getPesticideQuantityPerPlant();
                    System.out.printf("Hay que aplicar %d gr de pesticida hoy ", pesticideAmountToApply);
                }
            }

        });
    }

}
