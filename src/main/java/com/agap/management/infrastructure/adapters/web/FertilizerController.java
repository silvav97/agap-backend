package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IFertilizerService;
import com.agap.management.domain.dtos.FertilizerDTO;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fertilizers")
@PreAuthorize("hasRole('ADMIN')")
public class FertilizerController {

    private final IFertilizerService fertilizerService;

    @GetMapping
    public List<Fertilizer> getFertilizers() {
        return fertilizerService.findAll();
    }

    @GetMapping("/page")
    public Page<Fertilizer> getFertilizersPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return fertilizerService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Fertilizer getFertilizerById(@PathVariable Integer id) {
        return fertilizerService.findById(id).orElseThrow(
                () -> new EntityNotFoundByFieldException("Fertilizer", "id", id.toString()));
    }

    @PostMapping()
    public Fertilizer saveFertilizer(@RequestBody FertilizerDTO fertilizer) {
        return fertilizerService.save(fertilizer);
    }

}
