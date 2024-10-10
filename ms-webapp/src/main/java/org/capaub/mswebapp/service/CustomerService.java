package org.capaub.mswebapp.service;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.dto.CustomerDTO;
import org.capaub.mswebapp.service.client.CustomerClient;
import org.capaub.mswebapp.service.dto.CustomerWithAddressToSaveDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerClient customerClient;

    public List<CustomerDTO> getAllCustomersByCompanyId(Integer companyId) {
        return customerClient.getAllCustomersByCompanyId(companyId);
    }

    public CustomerDTO createCustomer(CustomerWithAddressToSaveDTO customerDTO) {
        return customerClient.createCustomer(customerDTO);
    }
}
