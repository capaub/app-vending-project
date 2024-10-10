package org.capaub.msvending.service;

import lombok.AllArgsConstructor;
import org.capaub.msvending.entities.SpiralType;
import org.capaub.msvending.mapper.SpiralTypeMapper;
import org.capaub.msvending.repository.SpiralTypeRepository;
import org.capaub.msvending.service.DTO.SpiralTypeDTO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpiralTypeService {
    private final SpiralTypeRepository spiralTypeRepository;
    private final SpiralTypeMapper spiralTypeMapper;

    public SpiralTypeDTO createSpiralType(SpiralTypeDTO spiralTypeDTO) {
        SpiralType spiralTypeSaved = spiralTypeRepository.save(spiralTypeMapper.toSpiralType(spiralTypeDTO));
        return spiralTypeMapper.toSpiralTypeDTO(spiralTypeSaved);
    }

    public SpiralTypeDTO getSpiralTypeById(Integer id) {
        SpiralType spiralType = spiralTypeRepository.findById(id).orElse(null);
        return spiralTypeMapper.toSpiralTypeDTO(spiralType);
    }
}
