package org.capaub.mscustomer.service.client;

import org.capaub.mscustomer.service.DTO.AddressDTO;
import org.capaub.mscustomer.service.DTO.CompanyDTO;
import org.capaub.mscustomer.service.DTO.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("ms-company")
public interface CompanyClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/companies/getCompanyById/{id}",consumes = "application/json")
    CompanyDTO getCompanyById(@PathVariable("id") Integer id);

    @RequestMapping(method = RequestMethod.POST, value = "/api/addresses/create", consumes = "application/json")
    AddressDTO createAddress(@RequestBody AddressDTO addressDTO);
    @RequestMapping(method = RequestMethod.GET, value = "/api/addresses/getAddressById/{id}",consumes = "application/json")
    AddressDTO getAddressById(@PathVariable("id") Integer id);
}
