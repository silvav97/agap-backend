package com.agap.management.infrastructure.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    /**
     * Maps from FertilizerDTO to Fertilizer
     * Maps from Fertilizer to FertilizerDTO
     * Maps from PesticideDTO to Pesticide not explicitly necessary
     * Maps from Pesticide to PesticideDTO not explicitly necessary
     * @return ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        return modelMapper;
    }
}
