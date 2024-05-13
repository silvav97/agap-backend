package com.agap.management.application.ports;

import com.agap.management.domain.dtos.request.CropRequestDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.entities.Crop;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICropService {
    List<CropResponseDTO> findAll();
    Page<CropResponseDTO> findAll(Pageable pageable);
    Page<CropResponseDTO> findAllByUserId(Pageable pageable, Integer userId);
    CropResponseDTO findById(Integer id);
    CropResponseDTO save(CropRequestDTO cropRequestDTO) throws MessagingException;
    CropResponseDTO update(Integer id, CropRequestDTO cropRequestDTO);
    Boolean delete(Integer id);
    Crop getCropById(Integer id);
}
