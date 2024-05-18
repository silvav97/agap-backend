package com.agap.management.application.services;

import com.agap.management.application.ports.ICropService;
import com.agap.management.application.ports.IEmailService;
import com.agap.management.domain.dtos.request.CropRequestDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.entities.Crop;
import com.agap.management.domain.entities.ProjectApplication;
import com.agap.management.domain.enums.ApplicationStatus;
import com.agap.management.domain.enums.ProcessStatus;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.ICropRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectApplicationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CropService implements ICropService {

    private final ICropRepository cropRepository;
    private final IProjectApplicationRepository projectApplicationRepository;
    private final IEmailService emailService;
    private final ModelMapper modelMapper;

    @Override
    public List<CropResponseDTO> findAll() {
        return cropRepository.findAll().stream()
                .map(crop -> modelMapper.map(crop, CropResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CropResponseDTO> findAll(Pageable pageable) {
        Page<Crop> page = cropRepository.findAll(pageable);
        return page.map(crop -> modelMapper.map(crop, CropResponseDTO.class));
    }

    @Override
    public Page<CropResponseDTO> findAllByUserId(Pageable pageable, Integer userId) {
        Page<Crop> page = cropRepository.findByProjectApplication_Applicant_Id(pageable, userId);
        return page.map(crop -> modelMapper.map(crop, CropResponseDTO.class));
    }

    @Override
    public CropResponseDTO findById(Integer id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Crop", "id", id.toString()));
        return modelMapper.map(crop, CropResponseDTO.class);
    }

    @Transactional
    @Override
    public CropResponseDTO save(CropRequestDTO cropRequestDTO) throws MessagingException {
        Crop crop = modelMapper.map(cropRequestDTO, Crop.class);

        ProjectApplication projectApplication = projectApplicationRepository.findById(cropRequestDTO.getProjectApplicationId())
                        .orElseThrow(() -> new EntityNotFoundByFieldException("ProjectApplication", "id", cropRequestDTO.getProjectApplicationId().toString()));

        projectApplication.setApplicationStatus(ApplicationStatus.APROBADO);
        projectApplicationRepository.save(projectApplication);

        crop.setProjectApplication(projectApplication);
        crop.setStatus(ProcessStatus.CREADO);

        Crop savedCrop = cropRepository.save(crop);
        emailService.sendEmail(crop.getProjectApplication().getApplicant().getEmail(),"Aplicación a proyecto aprobada", "La aplicación al proyecto fue aprobada","","");
        return modelMapper.map(savedCrop, CropResponseDTO.class);
    }

    @Transactional
    @Override
    public CropResponseDTO update(Integer id, CropRequestDTO cropRequestDTO) {
            Crop crop = cropRepository.findById(id).orElseThrow(() -> new EntityNotFoundByFieldException("Cultivo", "id", id.toString()));
            modelMapper.map(cropRequestDTO, crop);
            Crop savedCrop = cropRepository.save(crop);
        return modelMapper.map(savedCrop, CropResponseDTO.class);
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            cropRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public CropResponseDTO finish(Integer id, Float saleValue) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Cultivo", "id", id.toString()));

        crop.setStatus(ProcessStatus.CERRADO);
        crop.setSaleValue(saleValue);
        System.out.println("CROP SERVICE, UPDATE; crop status: " + crop.getStatus());

        Crop savedCrop = cropRepository.save(crop);
        System.out.println("CROP SERVICE, UPDATE; savedCrop status: " + savedCrop.getStatus());

        CropResponseDTO cropResponseDTO = modelMapper.map(savedCrop, CropResponseDTO.class);
        System.out.println("CROP SERVICE, UPDATE; cropResponseDTO status: " + cropResponseDTO.getStatus());

        return cropResponseDTO;
    }

    @Override
    public Crop getCropById(Integer id) {
        return cropRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Cultivo", "id", id.toString()));
    }

}
