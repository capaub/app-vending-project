package org.capaub.msvending.mapper;

import org.capaub.msvending.entities.SpiralType;
import org.capaub.msvending.service.DTO.SpiralTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpiralTypeMapper {
    SpiralType toSpiralType(SpiralTypeDTO spiralTypeDTO);
    SpiralTypeDTO toSpiralTypeDTO(SpiralType spiralType);
}
