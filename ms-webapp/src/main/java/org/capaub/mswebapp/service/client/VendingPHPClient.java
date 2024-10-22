package org.capaub.mswebapp.service.client;

import org.capaub.mswebapp.config.FeignConfig;
import org.capaub.mswebapp.service.dto.CustomerDataVendingsDTO;
import org.capaub.mswebapp.service.dto.VendingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "vending-service", configuration = FeignConfig.class)
public interface VendingPHPClient {
    @RequestMapping(method = RequestMethod.POST, value = "/php/createVending", consumes = "application/json")
    List<VendingDTO> createVending(@RequestBody VendingDTO vendingDTO);

    @RequestMapping(method = RequestMethod.POST, value = "/php/vendings-data/by-customers", consumes = "application/json")
    CustomerDataVendingsDTO retrieveVendingDetailsByCustomerIds(@RequestBody List<Integer> customerList);

    @RequestMapping(method = RequestMethod.GET,value = "/php/getAllVending/{companyId}",consumes = "application/json")
    List<VendingDTO> findAllVending(@PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/php/getAvailableVending/{companyId}", consumes = "application/json")
    List<VendingDTO> findAvailableVending(@PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.POST, value = "/php/addVendingToCustomer/{vendingId}/{customerId}/{vendingName}", consumes = "application/json")
    CustomerDataVendingsDTO addingVendingRetrieveCustomerDetails(@PathVariable Integer vendingId, @PathVariable Integer customerId, @PathVariable String vendingName);
}
