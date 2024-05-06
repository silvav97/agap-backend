package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IPesticideService;
import com.agap.management.domain.dtos.PesticideDTO;
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
@RequestMapping("/api/v1/pesticide")
@PreAuthorize("hasRole('ADMIN')")
public class PesticideController {

    private final IPesticideService pesticideService;

    @GetMapping
    public List<PesticideDTO> getPesticides() {
        return pesticideService.findAll();
    }

    @GetMapping("/page")
    public Page<PesticideDTO> getPesticidesPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return pesticideService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PesticideDTO getPesticideById(@PathVariable Integer id) {
        System.out.println("getPesticideById was called: "+ id);
        return pesticideService.findById(id);
    }

    @PostMapping()
    public PesticideDTO savePesticide(@RequestBody PesticideDTO pesticide) {
        System.out.println("savePesticide was called: "+ pesticide.toString());
        return pesticideService.save(pesticide);
    }

    @PutMapping("/{id}")
    public PesticideDTO updatePesticide(@RequestBody @Valid PesticideDTO pesticideDTO, @PathVariable Integer id) {
        System.out.println("updatePesticide was called: "+ pesticideDTO.toString());
        return pesticideService.update(id, pesticideDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deletePesticide(@PathVariable Integer id) {
        return pesticideService.delete(id);
    }

    @GetMapping("/{id}/relatedCropTypes")
    public List<String> getRelatedCropTypesByPesticideId(@PathVariable Integer id) {
        System.out.println("getRelatedCropTypesByPesticideId was called: " + id);
        List<String> relatedCropTypes = pesticideService.findRelatedCropTypes(id);
        System.out.println("Related CropTypes: " + relatedCropTypes);
        return relatedCropTypes;
    }
}
