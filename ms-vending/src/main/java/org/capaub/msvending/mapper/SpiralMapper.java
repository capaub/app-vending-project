package org.capaub.msvending.mapper;

import org.capaub.msvending.entities.Spiral;
import org.capaub.msvending.service.DTO.SpiralDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpiralMapper {
    @Mapping(source = "tray.id", target = "trayId")
    @Mapping(source = "vending.id", target = "vendingId")
    @Mapping(source = "spiralType.id", target = "spiralTypeId")
    SpiralDTO toSpiralDTO(Spiral spiral);
    @Mapping(source = "trayId", target = "tray.id")
    @Mapping(source = "vendingId", target = "vending.id")
    @Mapping(source = "spiralTypeId", target = "spiralType.id")
    Spiral toSpiral(SpiralDTO spiralDTO);
}
