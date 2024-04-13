package com.agap.management.infrastructure.configuration;

import com.agap.management.domain.dtos.FertilizerDTO;
import com.agap.management.domain.dtos.PesticideDTO;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.domain.entities.Pesticide;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);


        // Map from FertilizerDTO to Fertilizer
        // Map from Fertilizer to FertilizerDTO
        // Map from PesticideDTO to Pesticide    not explicitly necessary
        // Map from Pesticide to PesticideDTO    not explicitly necessary



        return modelMapper;
    }
}
