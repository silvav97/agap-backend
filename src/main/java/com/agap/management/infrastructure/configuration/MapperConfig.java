package com.agap.management.infrastructure.configuration;

import com.agap.management.domain.dtos.UserDTO;
import com.agap.management.domain.dtos.request.CropTypeRequestDTO;
import com.agap.management.domain.dtos.request.ProjectApplicationRequestDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import com.agap.management.domain.entities.CropType;
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

        // Asegúrate de no mapear las listas que pueden causar recursividad en otras entidades
        /*modelMapper.typeMap(ProjectApplication.class, ProjectApplicationResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(ProjectApplication::getProject);
                    mapper.map(src -> src.getProject() != null ? modelMapper.map(src.getProject(), ProjectResponseDTO.class) : null, ProjectApplicationResponseDTO::setProject);
                    mapper.map(src -> src.getApplicant() != null ? modelMapper.map(src.getApplicant(), UserDTO.class) : null, ProjectApplicationResponseDTO::setApplicant);
                    mapper.map(ProjectApplication::getApplicationStatus, ProjectApplicationResponseDTO::setApplicationStatus);
                    mapper.map(ProjectApplication::getApplicationDate, ProjectApplicationResponseDTO::setApplicationDate);
                    mapper.map(ProjectApplication::getReviewDate, ProjectApplicationResponseDTO::setReviewDate);
                    mapper.map(ProjectApplication::getAdminComment, ProjectApplicationResponseDTO::setAdminComment);

                });*/

        /*modelMapper.addMappings(new PropertyMap<ProjectApplication, ProjectApplicationResponseDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setProject(source.getProject());
            }
        });*/

        modelMapper.addMappings(new PropertyMap<ProjectApplication, ProjectApplicationResponseDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map(source.getProject(), destination.getProject());
                map(source.getApplicant(), destination.getApplicant());
                map().setApplicationStatus(source.getApplicationStatus());
                map().setApplicationDate(source.getApplicationDate());
                map().setReviewDate(source.getReviewDate());
                ///map().setAdminComment(source.getAdminComment());
            }
        });

        return modelMapper;
    }
}
