package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.ICropService;
import com.agap.management.domain.dtos.request.CropRequestDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.entities.User;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crop")
//@PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/mine/page")
    public Page<CropResponseDTO> getMyCrops(
            @RequestParam Integer pageNumber, @RequestParam Integer pageSize, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return cropService.findAllByUserId(pageable, user.getId());
    }

    @GetMapping("/{id}")
    public CropResponseDTO getCropById(@PathVariable Integer id) {
        return cropService.findById(id);
    }

    @PostMapping()
    public CropResponseDTO saveCrop(@RequestBody @Valid CropRequestDTO cropRequestDTO) throws MessagingException {
        return cropService.save(cropRequestDTO);
    }

    @PutMapping("/{id}")
    public CropResponseDTO updateCrop(@RequestBody @Valid CropRequestDTO cropRequestDTO, @PathVariable Integer id) {
        return cropService.update(id, cropRequestDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCrop(@PathVariable Integer id) {
        return cropService.delete(id);
    }

}
