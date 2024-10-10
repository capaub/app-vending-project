package org.capaub.mswebapp.service.client;

import org.capaub.mswebapp.service.dto.CustomerDTO;
import org.capaub.mswebapp.service.dto.CustomerWithAddressToSaveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("ms-customer")
public interface CustomerClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api/customers/create", consumes = "application/json")
    CustomerDTO createCustomer(@RequestBody CustomerWithAddressToSaveDTO customerWithAddressToSaveDTO);
    @RequestMapping(method = RequestMethod.GET, value = "/api/customers/getCustomerById/{id}", consumes = "application/json")
    CustomerDTO getCustomerById(@PathVariable Integer id);
    @RequestMapping(method = RequestMethod.GET, value = "/api/customers/getAllCustomersByCompanyId/{companyId}", consumes = "application/json")
    List<CustomerDTO> getAllCustomersByCompanyId(@PathVariable Integer companyId);
}
