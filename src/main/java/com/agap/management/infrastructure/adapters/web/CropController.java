package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.ICropService;
import com.agap.management.domain.dtos.response.CropResponseDTO;
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
@RequestMapping("/api/v1/crop")
@PreAuthorize("hasRole('ADMIN')")
public class CropController {

    private final ICropService cropService;

    @GetMapping
    public List<CropResponseDTO> getCrops() {
        return cropService.findAll();
    }

    @GetMapping("/page")
    public Page<CropResponseDTO> getCropsPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return cropService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CropResponseDTO getCropTypeById(@PathVariable Integer id) {
        return cropService.findById(id).orElseThrow(
                () -> new EntityNotFoundByFieldException("Cultivo", "id", id.toString()));
    }

    @PostMapping()
    public CropResponseDTO saveCropType(@RequestBody @Valid CropResponseDTO cropResponseDTO) {
        return cropService.save(cropResponseDTO);
    }

    @PutMapping("/{id}")
    public CropResponseDTO updateCropType(@RequestBody @Valid CropResponseDTO cropResponseDTO, @PathVariable Integer id) {
        return cropService.update(id, cropResponseDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCrop(@PathVariable Integer id) {
        return cropService.delete(id);
    }

}
