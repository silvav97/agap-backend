package com.agap.management.application.services;

import com.agap.management.application.ports.ICropService;
import com.agap.management.application.ports.ISupplyService;
import com.agap.management.domain.dtos.request.CropTypeRequestDTO;
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
            CropTypeRequestDTO cropType = crop.getProject().getCropType();
            int fertilizerFrequency = crop.getProject().getCropType().getFertilizerFrequency();
            int pesticideFrequency = crop.getProject().getCropType().getPesticideFrequency();

            LocalDate startDate = crop.getStartDate();
            LocalDate endDate = crop.getEndDate();

            long daysBetweenStartAndToday = ChronoUnit.DAYS.between(startDate, today);
            long daysBetweenTodayAndEnd = ChronoUnit.DAYS.between(today, endDate);

            if (today.isBefore(endDate) && daysBetweenTodayAndEnd >= fertilizerFrequency) {
                if(daysBetweenStartAndToday % fertilizerFrequency == 0) {
                    int totalPlantAmount = 1;
                    System.out.println("Hay que aplicar fertilizante hoy");
                }
            }

            if (today.isBefore(endDate) && daysBetweenTodayAndEnd >= pesticideFrequency) {
                if(daysBetweenStartAndToday % pesticideFrequency == 0) {
                    System.out.println("Hay que aplicar pesticida hoy");
                }
            }

        });
    }

}
