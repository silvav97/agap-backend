package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IPesticideService;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.domain.entities.Pesticide;
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
@RequestMapping("/api/v1/pesticides")
@PreAuthorize("hasRole('ADMIN')")
public class PesticideController {

    private final IPesticideService pesticideService;

    @GetMapping
    public List<Pesticide> getPesticides() {
        return pesticideService.findAll();
    }

    @GetMapping("/page")
    public Page<Pesticide> getPesticidesPagination(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return pesticideService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Pesticide getPesticideById(@PathVariable Integer id) {
        return pesticideService.findById(id).orElseThrow(
                () -> new EntityNotFoundByFieldException("Pesticide", "id", id.toString()));
    }

}
