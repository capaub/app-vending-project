package org.capaub.msvending.mapper;

import org.capaub.msvending.entities.Tray;
import org.capaub.msvending.service.DTO.TrayDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrayMapper {
    @Mapping(source = "vendingId", target = "vending.id")
    Tray toTray(TrayDTO trayDTO);
    @Mapping(source = "vending.id", target = "vendingId")
    TrayDTO toTrayDTO(Tray tray);
}
