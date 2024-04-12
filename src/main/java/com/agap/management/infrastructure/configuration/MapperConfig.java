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

        // Map from FertilizerDTO to Fertilizer
        modelMapper.addMappings(new PropertyMap<FertilizerDTO, Fertilizer>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setBrand(source.getBrand());
                map().setPricePerGram(source.getPricePerGram());
            }
        });

        // Map from PesticideDTO to Pesticide
        modelMapper.addMappings(new PropertyMap<PesticideDTO, Pesticide>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setBrand(source.getBrand());
                map().setPricePerGram(source.getPricePerGram());
            }
        });


        return modelMapper;
    }
}
