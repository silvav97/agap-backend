package com.agap.management.infrastructure.configuration;

import com.agap.management.domain.dtos.request.CropTypeRequestDTO;
import com.agap.management.domain.dtos.request.ProjectApplicationRequestDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.entities.CropType;
import com.agap.management.domain.entities.ProjectApplication;
import org.modelmapper.ModelMapper;
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
                });

        // Asegúrate de no mapear las listas que pueden causar recursividad en otras entidades
        modelMapper.typeMap(ProjectApplication.class, ProjectApplicationResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getProject().getId(), ProjectApplicationResponseDTO::setProject);
                    mapper.map(src -> src.getApplicant().getId(), ProjectApplicationResponseDTO::setApplicant);
                });

        return modelMapper;
    }
}
