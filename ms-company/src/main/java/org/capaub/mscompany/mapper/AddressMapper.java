package org.capaub.mscompany.mapper;

import org.capaub.mscompany.entity.Address;
import org.capaub.mscompany.service.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(source = "company.id", target = "companyId")
    AddressDTO toAddressDTO(Address address);
    @Mapping(source = "companyId", target = "company.id")
    Address toAddress(AddressDTO addressDTO);
}