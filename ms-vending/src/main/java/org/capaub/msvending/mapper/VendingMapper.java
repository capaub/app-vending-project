package org.capaub.msvending.mapper;

import org.capaub.msvending.entities.Vending;
import org.capaub.msvending.service.DTO.VendingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendingMapper {
    Vending toVending(VendingDTO vendingDTO);
    VendingDTO toVendingDTO(Vending vending);
}
