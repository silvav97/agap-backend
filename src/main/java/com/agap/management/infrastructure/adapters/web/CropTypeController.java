package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.ICropTypeService;
import com.agap.management.domain.dtos.request.CropTypeRequestDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cropType")
@PreAuthorize("hasRole('ADMIN')")
public class CropTypeController {

    private final ICropTypeService cropTypeService;

    @GetMapping
    public List<CropTypeResponseDTO> getCropTypes() {
        return cropTypeService.findAll();
    }

    @GetMapping("/page")
    public Page<CropTypeResponseDTO> getCropTypesPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return cropTypeService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CropTypeResponseDTO getCropTypeById(@PathVariable Integer id) {
        return cropTypeService.findById(id).orElseThrow(
                () -> new EntityNotFoundByFieldException("Tipo de cultivo", "id", id.toString()));
    }

    @PostMapping()
    public CropTypeResponseDTO saveCropType(@RequestBody @Valid CropTypeRequestDTO cropTypeRequestDTO) {
        return cropTypeService.save(cropTypeRequestDTO);
    }

    @PutMapping("/{id}")
    public CropTypeResponseDTO updateCropType(@RequestBody @Valid CropTypeRequestDTO cropTypeRequestDTO,
                                              @PathVariable Integer id) {
        return cropTypeService.update(id, cropTypeRequestDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCropType(@PathVariable Integer id) {
        return cropTypeService.delete(id);
    }

}
