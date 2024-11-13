package org.capaub.mswebapp.service;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.client.VendingPHPClient;
import org.capaub.mswebapp.service.dto.BuildVendingDTO;
import org.capaub.mswebapp.service.dto.CustomerDataVendingsDTO;
import org.capaub.mswebapp.service.dto.VendingMongoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class VendingService {

    private final VendingPHPClient vendingPHPClient;

    public List<VendingMongoDTO> createVending(VendingMongoDTO vending) {
        return vendingPHPClient.createVending(vending);
    }

    public List<VendingMongoDTO> getAllVending(Integer companyId) {
        return vendingPHPClient.findAllVending(companyId);
    }

    public List<VendingMongoDTO> findAvailableVending(Integer vendingId) {
        return vendingPHPClient.findAvailableVending(vendingId);
    }

    public CustomerDataVendingsDTO getVendingDataByCustomerList(List<Integer> customerIds) {
        return vendingPHPClient.retrieveVendingDetailsByCustomerIds(customerIds);
    }

    public CustomerDataVendingsDTO addVendingToCustomer(String vendingId, Integer customerId, String vendingName) {

        return vendingPHPClient.addingVendingRetrieveCustomerDetails(vendingId, customerId, vendingName);
    }

    public BuildVendingDTO getDataForBuildVending(String vendingId) {
        return vendingPHPClient.buildVending(vendingId);
    }

    public void addBatchToVending(String location,
                                  String vendingId,
                                  Integer batchId,
                                  Integer quantity) {
        vendingPHPClient.addBatchToVending(location, vendingId, batchId, quantity);
    }

    public void cancelLastUpdateSpiralStock(String vendingId) {
        vendingPHPClient.cancelLastUpdatedSpiralStock(vendingId);
    }

    public Map<String, String> getVendingsByCustomer(Integer customerId) {
        return vendingPHPClient.vendingListByCustomerId(customerId);
    }
}