package org.capaub.mscustomer.mapper;

import org.capaub.mscustomer.entities.Customer;
import org.capaub.mscustomer.service.DTO.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerDTO customerDTO);
    CustomerDTO toCustomerDTO(Customer customer);
}