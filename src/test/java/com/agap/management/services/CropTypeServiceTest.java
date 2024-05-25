package com.agap.management.services;

import com.agap.management.application.ports.IFertilizerService;
import com.agap.management.application.ports.IPesticideService;
import com.agap.management.application.services.CropTypeService;
import com.agap.management.domain.dtos.request.CropTypeRequestDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import com.agap.management.domain.entities.CropType;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.domain.entities.Pesticide;
import com.agap.management.domain.entities.Project;
import com.agap.management.infrastructure.adapters.persistence.ICropTypeRepository;
import com.agap.management.infrastructure.adapters.persistence.IFertilizerRepository;
import com.agap.management.infrastructure.adapters.persistence.IPesticideRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectRepository;
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
public class CropTypeServiceTest {

    @Mock
    private ICropTypeRepository cropTypeRepository;
    @Mock
    private IProjectRepository projectRepository;
    @Mock
    private IFertilizerService fertilizerService;
    @Mock
    private IPesticideService pesticideService;
    @Mock
    private IFertilizerRepository fertilizerRepository;
    @Mock
    private IPesticideRepository pesticideRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CropTypeService cropTypeService;

    private CropType cropType;
    private CropTypeRequestDTO cropTypeRequestDTO;
    private CropTypeResponseDTO cropTypeResponseDTO;
    private Fertilizer fertilizer;
    private Pesticide pesticide;

    @BeforeEach
    void setUp() {
        cropType = new CropType();
        cropType.setId(1);

        cropTypeRequestDTO = new CropTypeRequestDTO();
        cropTypeRequestDTO.setFertilizerId(1);
        cropTypeRequestDTO.setPesticideId(1);

        cropTypeResponseDTO = new CropTypeResponseDTO();
        cropTypeResponseDTO.setId(1);

        fertilizer = new Fertilizer();
        fertilizer.setId(1);

        pesticide = new Pesticide();
        pesticide.setId(1);
    }

    @Test
    void testFindAll() {
        when(cropTypeRepository.findAll()).thenReturn(Collections.singletonList(cropType));
        when(modelMapper.map(any(CropType.class), eq(CropTypeResponseDTO.class))).thenReturn(cropTypeResponseDTO);

        List<CropTypeResponseDTO> result = cropTypeService.findAll();
        assertEquals(1, result.size());
        assertEquals(cropTypeResponseDTO, result.get(0));
    }

    @Test
    void testFindAllPageable() {
        Page<CropType> page = new PageImpl<>(Collections.singletonList(cropType));
        when(cropTypeRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(CropType.class), eq(CropTypeResponseDTO.class))).thenReturn(cropTypeResponseDTO);

        Page<CropTypeResponseDTO> result = cropTypeService.findAll(PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
        assertEquals(cropTypeResponseDTO, result.getContent().get(0));
    }

    @Test
    void testFindById() {
        when(cropTypeRepository.findById(anyInt())).thenReturn(Optional.of(cropType));
        when(modelMapper.map(any(CropType.class), eq(CropTypeResponseDTO.class))).thenReturn(cropTypeResponseDTO);

        Optional<CropTypeResponseDTO> result = cropTypeService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(cropTypeResponseDTO, result.get());
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(CropTypeRequestDTO.class), eq(CropType.class))).thenReturn(cropType);
        when(fertilizerService.getFertilizerById(anyInt())).thenReturn(fertilizer);
        when(pesticideService.getPesticideById(anyInt())).thenReturn(pesticide);
        when(cropTypeRepository.save(any(CropType.class))).thenReturn(cropType);
        when(modelMapper.map(any(CropType.class), eq(CropTypeResponseDTO.class))).thenReturn(cropTypeResponseDTO);

        CropTypeResponseDTO result = cropTypeService.save(cropTypeRequestDTO);
        assertEquals(cropTypeResponseDTO, result);
    }

    @Test
    void testUpdate() {
        when(cropTypeRepository.findById(anyInt())).thenReturn(Optional.of(cropType));
        when(fertilizerRepository.findById(anyInt())).thenReturn(Optional.of(fertilizer));
        when(pesticideRepository.findById(anyInt())).thenReturn(Optional.of(pesticide));
        doAnswer(invocation -> {
            invocation.getArgument(0);
            CropType entity = invocation.getArgument(1);
            entity.setFertilizer(fertilizer);
            entity.setPesticide(pesticide);
            return null;
        }).when(modelMapper).map(any(CropTypeRequestDTO.class), any(CropType.class));
        when(cropTypeRepository.save(any(CropType.class))).thenReturn(cropType);
        when(modelMapper.map(any(CropType.class), eq(CropTypeResponseDTO.class))).thenReturn(cropTypeResponseDTO);

        CropTypeResponseDTO result = cropTypeService.update(1, cropTypeRequestDTO);
        assertEquals(cropTypeResponseDTO, result);
    }

    @Test
    void testDelete() {
        List<Project> projects = List.of(new Project());
        when(projectRepository.findByCropType_Id(anyInt())).thenReturn(projects);
        doAnswer(invocation -> null).when(projectRepository).saveAll(anyList());
        doAnswer(invocation -> null).when(cropTypeRepository).deleteById(anyInt());

        Boolean result = cropTypeService.delete(1);
        assertTrue(result);
        verify(projectRepository, times(1)).saveAll(anyList());
        verify(cropTypeRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testFindRelatedProjects() {
        Project project = new Project();
        project.setName("Project1");
        when(projectRepository.findByCropType_Id(anyInt())).thenReturn(Collections.singletonList(project));

        List<String> result = cropTypeService.findRelatedProjects(1);
        assertEquals(1, result.size());
        assertEquals("Project1", result.get(0));
    }

}