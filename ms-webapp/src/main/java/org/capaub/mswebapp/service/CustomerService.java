package org.capaub.mswebapp.service;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.dto.*;
import org.capaub.mswebapp.service.client.CustomerClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerClient customerClient;
    private final VendingService vendingService;

    public List<CustomerDTO> getAllCustomersByCompanyId(Integer companyId) {
        return customerClient.getAllCustomersByCompanyId(companyId);
    }
    public CustomerDTO getCustomers(Integer customerId) {
        return customerClient.getCustomerById(customerId);
    }

    public CustomerDTO createCustomer(CustomerWithAddressToSaveDTO customerDTO) {
        return customerClient.createCustomer(customerDTO);
    }

    public List<VendingDTO> getAvailableVending(Integer companyId) {
        return vendingService.findAvailableVending(companyId);
    }

    public CustomerDataVendingsDTO getVendingsByCustomers(Integer companyId) {
        List<CustomerDTO> allByCompany = getAllCustomersByCompanyId(companyId);
        List<Integer> customerIds = allByCompany.stream()
                .map(CustomerDTO::getId)
                .toList();
        return vendingService.getVendingDataByCustomerList(customerIds);
    }

    public CustomerDataVendingsDTO getVendingsByCustomer(Integer customerId) {
        List<Integer> customerIdToList = new ArrayList<>();
        customerIdToList.add(customerId);
        return vendingService.getVendingDataByCustomerList(customerIdToList);
    }

    public CustomerDataVendingsDTO addVending(Integer vendingId, Integer customerId, String vendingName) {

        return vendingService.addVendingToCustomer(vendingId,customerId,vendingName);
    }
}
