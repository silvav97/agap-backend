package com.agap.management.application.services;

import com.agap.management.application.ports.ICropService;
import com.agap.management.application.ports.ISupplyService;
import com.agap.management.domain.dtos.response.CropResponseDTO;
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
            int frequency = crop.getProject().getCropType().getFertilizerFrequency();
            LocalDate startDate = crop.getStartDate();
            LocalDate endDate = crop.getEndDate();

            if (today.isBefore(endDate) && ChronoUnit.DAYS.between(today, endDate) >= frequency) {
                if(ChronoUnit.DAYS.between(startDate, today) % frequency == 0) {
                    System.out.println("Hay que aplicar alguna monda hoy");
                }
            }
        });
    }

}
