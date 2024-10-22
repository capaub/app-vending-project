package org.capaub.mswebapp.service;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.client.VendingPHPClient;
import org.capaub.mswebapp.service.dto.CustomerDataVendingsDTO;
import org.capaub.mswebapp.service.dto.VendingDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VendingService {

    private final VendingPHPClient vendingPHPClient;

    public List<VendingDTO> createVending(VendingDTO vending) {
        return vendingPHPClient.createVending(vending);
    }

    public List<VendingDTO> getAllVending(Integer companyId) {
        return vendingPHPClient.findAllVending(companyId);
    }

    public List<VendingDTO> findAvailableVending(Integer vendingId) {
        return vendingPHPClient.findAvailableVending(vendingId);
    }

    public CustomerDataVendingsDTO getVendingDataByCustomerList(List<Integer> customerIds) {
        return vendingPHPClient.retrieveVendingDetailsByCustomerIds(customerIds);
    }

    public List<VendingDTO> getAvailableVending(Integer companyId) {
        return vendingPHPClient.findAvailableVending(companyId);
    }

    public CustomerDataVendingsDTO addVendingToCustomer(Integer vendingId, Integer customerId, String vendingName) {

        return vendingPHPClient.addingVendingRetrieveCustomerDetails(vendingId, customerId, vendingName);
    }
}
