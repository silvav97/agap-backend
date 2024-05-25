package com.agap.management.services;

import com.agap.management.application.services.PesticideService;
import com.agap.management.domain.dtos.PesticideDTO;
import com.agap.management.domain.entities.CropType;
import com.agap.management.domain.entities.Pesticide;
import com.agap.management.infrastructure.adapters.persistence.ICropTypeRepository;
import com.agap.management.infrastructure.adapters.persistence.IPesticideRepository;
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
public class PesticideServiceTest {

    @Mock private IPesticideRepository pesticideRepository;
    @Mock private ICropTypeRepository cropTypeRepository;
    @Mock private ModelMapper modelMapper;
    @InjectMocks private PesticideService pesticideService;

    private Pesticide pesticide;
    private PesticideDTO pesticideDTO;
    private CropType cropType;

    @BeforeEach
    void setUp() {
        pesticide = new Pesticide();
        pesticide.setId(1);

        pesticideDTO = new PesticideDTO();
        pesticideDTO.setId(1);

        cropType = new CropType();
        cropType.setId(1);
    }

    @Test
    void testFindAll() {
        when(pesticideRepository.findAll()).thenReturn(Collections.singletonList(pesticide));
        when(modelMapper.map(any(Pesticide.class), eq(PesticideDTO.class))).thenReturn(pesticideDTO);

        List<PesticideDTO> result = pesticideService.findAll();
        assertEquals(1, result.size());
        assertEquals(pesticideDTO, result.get(0));
    }

    @Test
    void testFindAllPageable() {
        Page<Pesticide> page = new PageImpl<>(Collections.singletonList(pesticide));
        when(pesticideRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(Pesticide.class), eq(PesticideDTO.class))).thenReturn(pesticideDTO);

        Page<PesticideDTO> result = pesticideService.findAll(PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
        assertEquals(pesticideDTO, result.getContent().get(0));
    }

    @Test
    void testFindById() {
        when(pesticideRepository.findById(anyInt())).thenReturn(Optional.of(pesticide));
        when(modelMapper.map(any(Pesticide.class), eq(PesticideDTO.class))).thenReturn(pesticideDTO);

        PesticideDTO result = pesticideService.findById(1);
        assertEquals(pesticideDTO, result);
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(PesticideDTO.class), eq(Pesticide.class))).thenReturn(pesticide);
        when(pesticideRepository.save(any(Pesticide.class))).thenReturn(pesticide);
        when(modelMapper.map(any(Pesticide.class), eq(PesticideDTO.class))).thenReturn(pesticideDTO);

        PesticideDTO result = pesticideService.save(pesticideDTO);
        assertEquals(pesticideDTO, result);
    }

    @Test
    void testUpdate() {
        when(pesticideRepository.findById(anyInt())).thenReturn(Optional.of(pesticide));
        when(pesticideRepository.save(any(Pesticide.class))).thenReturn(pesticide);
        doAnswer(invocation -> {
            invocation.getArgument(0);
            invocation.getArgument(1);
            return null;
        }).when(modelMapper).map(any(PesticideDTO.class), any(Pesticide.class));
        when(modelMapper.map(any(Pesticide.class), eq(PesticideDTO.class))).thenReturn(pesticideDTO);

        PesticideDTO result = pesticideService.update(1, pesticideDTO);
        assertEquals(pesticideDTO, result);
    }

    @Test
    void testDelete() {
        List<CropType> cropTypes = List.of(cropType);
        when(cropTypeRepository.findByPesticide_Id(anyInt())).thenReturn(cropTypes);
        doAnswer(invocation -> null).when(cropTypeRepository).saveAll(anyList());
        doAnswer(invocation -> null).when(pesticideRepository).deleteById(anyInt());

        Boolean result = pesticideService.delete(1);
        assertTrue(result);
        verify(cropTypeRepository, times(1)).saveAll(anyList());
        verify(pesticideRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testGetPesticideById() {
        when(pesticideRepository.findById(anyInt())).thenReturn(Optional.of(pesticide));

        Pesticide result = pesticideService.getPesticideById(1);
        assertEquals(pesticide, result);
    }

    @Test
    void testFindRelatedCropTypes() {
        cropType.setName("CropType1");
        when(cropTypeRepository.findByPesticide_Id(anyInt())).thenReturn(Collections.singletonList(cropType));

        List<String> result = pesticideService.findRelatedCropTypes(1);
        assertEquals(1, result.size());
        assertEquals("CropType1", result.get(0));
    }
}
