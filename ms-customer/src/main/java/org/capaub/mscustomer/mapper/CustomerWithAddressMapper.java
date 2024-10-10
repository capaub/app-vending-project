package org.capaub.mscustomer.mapper;

import org.capaub.mscustomer.service.DTO.AddressDTO;
import org.capaub.mscustomer.service.DTO.CustomerDTO;
import org.capaub.mscustomer.service.DTO.CustomerWithAddressToSaveDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerWithAddressMapper {
    @Mapping(source = "streetAddress", target = "streetAddress")
    @Mapping(source = "postalCode", target = "postalCode")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "companyId", target = "companyId")
    AddressDTO toAddressDTO(CustomerWithAddressToSaveDTO customerWithAddressToSaveDTO);
    @Mapping(source = "siret", target = "siret")
    @Mapping(source = "companyName", target = "companyName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "companyId", target = "companyId")
    CustomerDTO toCustomerDTO(CustomerWithAddressToSaveDTO customerWithAddressToSaveDTO);
}
