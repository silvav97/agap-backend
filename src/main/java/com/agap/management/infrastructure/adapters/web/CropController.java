package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.ICropService;
import com.agap.management.domain.dtos.CropDTO;
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
    public List<CropDTO> getCrops() {
        return cropService.findAll();
    }

    @GetMapping("/page")
    public Page<CropDTO> getCropsPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return cropService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CropDTO getCropTypeById(@PathVariable Integer id) {
        return cropService.findById(id).orElseThrow(
                () -> new EntityNotFoundByFieldException("Cultivo", "id", id.toString()));
    }

    @PostMapping()
    public CropDTO saveCropType(@RequestBody @Valid CropDTO cropDTO) {
        return cropService.save(cropDTO);
    }

    @PutMapping("/{id}")
    public CropDTO updateCropType(@RequestBody @Valid CropDTO cropDTO, @PathVariable Integer id) {
        return cropService.update(id, cropDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCrop(@PathVariable Integer id) {
        return cropService.delete(id);
    }

}