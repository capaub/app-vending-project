package org.capaub.mswebapp.service.client;

import org.capaub.mswebapp.service.dto.BuildVendingDTO;
import org.capaub.mswebapp.service.dto.CustomerDataVendingsDTO;
import org.capaub.mswebapp.service.dto.VendingMongoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

//@FeignClient(value = "vending-service", configuration = FeignConfig.class) //pour docker
@FeignClient(name = "vending-service") //, url = "http://localhost:8000" pour contourner eureka
public interface VendingPHPClient {
    @RequestMapping(method = RequestMethod.POST, value = "/php/mongoDB/createVending", consumes = "application/json")
    List<VendingMongoDTO> createVending(@RequestBody VendingMongoDTO vendingDTO);

    @RequestMapping(method = RequestMethod.POST, value = "/php/mongoDB/vendings-data/by-customers", consumes = "application/json")
    CustomerDataVendingsDTO retrieveVendingDetailsByCustomerIds(@RequestBody List<Integer> customerList);

    @RequestMapping(method = RequestMethod.GET, value = "/php/mongoDB/getAllVending/{companyId}", consumes = "application/json")
    List<VendingMongoDTO> findAllVending(@PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/php/mongoDB/getAvailableVending/{companyId}", consumes = "application/json")
    List<VendingMongoDTO> findAvailableVending(@PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/php/buildVending/{vendingId}", consumes = "application/json")
    BuildVendingDTO buildVending(@PathVariable String vendingId);


    @RequestMapping(method = RequestMethod.POST, value = "/php/batch/addToVending/{location}/{vendingId}/{batchId}/{quantity}", consumes = "application/json")
    void addBatchToVending(@PathVariable String location,
                           @PathVariable String vendingId,
                           @PathVariable Integer batchId,
                           @PathVariable Integer quantity);

    @RequestMapping(method = RequestMethod.POST, value = "/php/batch/removeLastUpdatedStock/{vendingId}", consumes = "application/json")
    void cancelLastUpdatedSpiralStock(@PathVariable String vendingId);

    @RequestMapping(method = RequestMethod.POST, value = "/php/addVendingToCustomer/{vendingId}/{customerId}/{vendingName}", consumes = "application/json")
    CustomerDataVendingsDTO addingVendingRetrieveCustomerDetails(@PathVariable String vendingId,
                                                                 @PathVariable Integer customerId,
                                                                 @PathVariable String vendingName);

    @RequestMapping(method = RequestMethod.GET, value = "/php/vendingListByCustomer/{customerId}", consumes = "application/json")
    Map<String, String> vendingListByCustomerId(@PathVariable Integer customerId);
}