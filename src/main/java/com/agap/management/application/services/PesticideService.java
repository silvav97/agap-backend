package com.agap.management.application.services;

import com.agap.management.application.ports.IPesticideService;
import com.agap.management.domain.dtos.PesticideDTO;
import com.agap.management.domain.entities.CropType;
import com.agap.management.domain.entities.Pesticide;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.ICropTypeRepository;
import com.agap.management.infrastructure.adapters.persistence.IPesticideRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PesticideService implements IPesticideService {

    private final IPesticideRepository pesticideRepository;
    private final ICropTypeRepository  cropTypeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PesticideDTO> findAll() {
        return pesticideRepository.findAll().stream()
                .map(pesticide -> modelMapper.map(pesticide, PesticideDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PesticideDTO> findAll(Pageable pageable) {
        Page<Pesticide> page = pesticideRepository.findAll(pageable);
        return page.map(pesticide -> modelMapper.map(pesticide, PesticideDTO.class));
    }

    @Override
    public PesticideDTO findById(Integer id) {
        return modelMapper.map(getPesticideById(id), PesticideDTO.class);
    }

    @Override
    public PesticideDTO save(PesticideDTO pesticideDTO) {
        Pesticide pesticide = modelMapper.map(pesticideDTO, Pesticide.class);
        Pesticide savedPesticide = pesticideRepository.save(pesticide);
        return modelMapper.map(savedPesticide, PesticideDTO.class);
    }

    @Override
    public PesticideDTO update(Integer id, PesticideDTO pesticideDTO) {
        Pesticide pesticide = getPesticideById(id);

        modelMapper.map(pesticideDTO, pesticide);
        Pesticide savedPesticide = pesticideRepository.save(pesticide);
        return modelMapper.map(savedPesticide, PesticideDTO.class);
    }

    @Override
    public Boolean delete(Integer pesticideId) {
        try {
            List<CropType> cropTypes = cropTypeRepository.findByPesticide_Id(pesticideId);
            cropTypes.forEach(cropType -> cropType.setPesticide(null));
            cropTypeRepository.saveAll(cropTypes);
            pesticideRepository.deleteById(pesticideId);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Pesticide getPesticideById(Integer id) {
        return pesticideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Pesticida", "id", id.toString()));
    }

    @Override
    public List<String> findRelatedCropTypes(Integer pesticideId) {
        return cropTypeRepository.findByPesticide_Id(pesticideId).stream()
                .map(CropType::getName)
                .collect(Collectors.toList());
    }

}
