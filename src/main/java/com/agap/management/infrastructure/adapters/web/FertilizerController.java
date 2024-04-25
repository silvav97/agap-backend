package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IFertilizerService;
import com.agap.management.domain.dtos.FertilizerDTO;
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
@RequestMapping("/api/v1/fertilizer")
@PreAuthorize("hasRole('ADMIN')")
public class FertilizerController {

    private final IFertilizerService fertilizerService;

    @GetMapping
    public List<FertilizerDTO> getFertilizers() {
        return fertilizerService.findAll();
    }

    @GetMapping("/page")
    public Page<FertilizerDTO> getFertilizersPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return fertilizerService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public FertilizerDTO getFertilizerById(@PathVariable Integer id) {
        return fertilizerService.findById(id).orElseThrow(
                () -> new EntityNotFoundByFieldException("Fertilizante", "id", id.toString()));
    }

    @PostMapping()
    public FertilizerDTO saveFertilizer(@RequestBody @Valid FertilizerDTO fertilizerDTO) {
        System.out.println("SaveFertilizer was called: "+ fertilizerDTO.toString());
        return fertilizerService.save(fertilizerDTO);
    }

    @PutMapping("/{id}")
    public FertilizerDTO updateFertilizer(@RequestBody @Valid FertilizerDTO fertilizerDTO, @PathVariable Integer id) {
        return fertilizerService.update(id, fertilizerDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteFertilizer(@PathVariable Integer id) {
        return fertilizerService.delete(id);
    }


}
