package com.agap.management.infrastructure.configuration;

import com.agap.management.domain.dtos.request.ProjectApplicationRequestDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.entities.Crop;
import com.agap.management.domain.entities.ProjectApplication;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    /**
     * Maps from EntityDTO to Entity
     * Maps from Entity to EntityDTO
     * @return ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Configuración específica para evitar la recursividad en ProjectApplication
        modelMapper.typeMap(ProjectApplicationRequestDTO.class, ProjectApplication.class)
                .addMappings(mapper -> {
                    mapper.skip(ProjectApplication::setProject); // Asignar manualmente en el servicio
                    mapper.skip(ProjectApplication::setApplicant); // Asignar manualmente en el servicio
                    //mapper.map(ProjectApplicationRequestDTO::getAdminComment, ProjectApplication::setAdminComment); // Mapeo directo para adminComment
                });

        modelMapper.addMappings(new PropertyMap<ProjectApplication, ProjectApplicationResponseDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map(source.getProject(), destination.getProject());
                map(source.getApplicant(), destination.getApplicant());
                map().setApplicationStatus(source.getApplicationStatus());
                map().setApplicationDate(source.getApplicationDate());
                map().setReviewDate(source.getReviewDate());
            }
        });

        modelMapper.addMappings(new PropertyMap<Crop, CropResponseDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map(source.getProjectApplication(), destination.getProjectApplication());
                map().setStatus(source.getStatus());
                map().setStartDate(source.getStartDate());
                map().setEndDate(source.getEndDate());
                map().setExpectedExpense(source.getExpectedExpense());
                map().setAssignedBudget(source.getAssignedBudget());
                map().setSaleValue(source.getSaleValue());

            }
        });

        return modelMapper;
    }
}
