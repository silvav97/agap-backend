package com.agap.management.services;

import com.agap.management.application.services.FertilizerService;
import com.agap.management.domain.dtos.FertilizerDTO;
import com.agap.management.domain.entities.CropType;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.infrastructure.adapters.persistence.ICropTypeRepository;
import com.agap.management.infrastructure.adapters.persistence.IFertilizerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FertilizerServiceTest {

    @Mock
    private IFertilizerRepository fertilizerRepository;
    @Mock
    private ICropTypeRepository cropTypeRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private FertilizerService fertilizerService;

    private Fertilizer fertilizer;
    private FertilizerDTO fertilizerDTO;
    private CropType cropType;

    @BeforeEach
    void setUp() {
        fertilizer = new Fertilizer();
        fertilizer.setId(1);

        fertilizerDTO = new FertilizerDTO();
        fertilizerDTO.setId(1);

        cropType = new CropType();
        cropType.setId(1);
    }

    @Test
    void testFindAll() {
        when(fertilizerRepository.findAll()).thenReturn(Collections.singletonList(fertilizer));
        when(modelMapper.map(any(Fertilizer.class), eq(FertilizerDTO.class))).thenReturn(fertilizerDTO);

        List<FertilizerDTO> result = fertilizerService.findAll();
        assertEquals(1, result.size());
        assertEquals(fertilizerDTO, result.get(0));
    }

    @Test
    void testFindAllPageable() {
        Page<Fertilizer> page = new PageImpl<>(Collections.singletonList(fertilizer));
        when(fertilizerRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(Fertilizer.class), eq(FertilizerDTO.class))).thenReturn(fertilizerDTO);

        Page<FertilizerDTO> result = fertilizerService.findAll(PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
        assertEquals(fertilizerDTO, result.getContent().get(0));
    }

    @Test
    void testFindById() {
        when(fertilizerRepository.findById(anyInt())).thenReturn(Optional.of(fertilizer));
        when(modelMapper.map(any(Fertilizer.class), eq(FertilizerDTO.class))).thenReturn(fertilizerDTO);

        FertilizerDTO result = fertilizerService.findById(1);
        assertEquals(fertilizerDTO, result);
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(FertilizerDTO.class), eq(Fertilizer.class))).thenReturn(fertilizer);
        when(fertilizerRepository.save(any(Fertilizer.class))).thenReturn(fertilizer);
        when(modelMapper.map(any(Fertilizer.class), eq(FertilizerDTO.class))).thenReturn(fertilizerDTO);

        FertilizerDTO result = fertilizerService.save(fertilizerDTO);
        assertEquals(fertilizerDTO, result);
    }

    @Test
    void testUpdate() {
        when(fertilizerRepository.findById(anyInt())).thenReturn(Optional.of(fertilizer));
        when(fertilizerRepository.save(any(Fertilizer.class))).thenReturn(fertilizer);
        doAnswer(invocation -> {
            invocation.getArgument(0);
            invocation.getArgument(1);
            return null;
        }).when(modelMapper).map(any(FertilizerDTO.class), any(Fertilizer.class));
        when(modelMapper.map(any(Fertilizer.class), eq(FertilizerDTO.class))).thenReturn(fertilizerDTO);

        FertilizerDTO result = fertilizerService.update(1, fertilizerDTO);
        assertEquals(fertilizerDTO, result);
    }

    @Test
    void testDelete() {
        List<CropType> cropTypes = List.of(cropType);
        when(cropTypeRepository.findByFertilizer_Id(anyInt())).thenReturn(cropTypes);
        doAnswer(invocation -> null).when(cropTypeRepository).saveAll(anyList());
        doAnswer(invocation -> null).when(fertilizerRepository).deleteById(anyInt());

        Boolean result = fertilizerService.delete(1);
        assertTrue(result);
        verify(cropTypeRepository, times(1)).saveAll(anyList());
        verify(fertilizerRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testGetFertilizerById() {
        when(fertilizerRepository.findById(anyInt())).thenReturn(Optional.of(fertilizer));

        Fertilizer result = fertilizerService.getFertilizerById(1);
        assertEquals(fertilizer, result);
    }

    @Test
    void testFindRelatedCropTypes() {
        cropType.setName("CropType1");
        when(cropTypeRepository.findByFertilizer_Id(anyInt())).thenReturn(Collections.singletonList(cropType));

        List<String> result = fertilizerService.findRelatedCropTypes(1);
        assertEquals(1, result.size());
        assertEquals("CropType1", result.get(0));
    }
}
