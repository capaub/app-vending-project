package org.capaub.mswebapp.service.client;

import org.capaub.mswebapp.service.dto.AddressDTO;
import org.capaub.mswebapp.service.dto.AppUserDTO;
import org.capaub.mswebapp.service.dto.CompanyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("ms-company")
public interface CompanyClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api/users/create", consumes = "application/json")
    AppUserDTO createUser(@RequestBody AppUserDTO appUserDTO);
    @RequestMapping(method = RequestMethod.POST, value = "/api/users/update", consumes = "application/json")
    AppUserDTO updateUser(@RequestBody AppUserDTO appUserDTO);
    @RequestMapping(method = RequestMethod.POST, value = "/api/users/delete/{id}", consumes = "application/json")
    void deleteUser(@PathVariable Integer id);
    @RequestMapping(method = RequestMethod.GET, value = "/api/users/getUserByEmail/{email}", consumes = "application/json")
    AppUserDTO getUserByEmail(@PathVariable("email") String email);
    @RequestMapping(method = RequestMethod.GET, value = "/api/users/getUserById/{id}", consumes = "application/json")
    AppUserDTO getUserById(@PathVariable("id") Integer id);
    @RequestMapping(method = RequestMethod.PUT, value = "api/users/{email}/update-connected-at", consumes = "application/json")
    void updateConnectedAt(@PathVariable("email") String email);
    @RequestMapping(method = RequestMethod.GET, value = "/api/users/getAllUsersByCompanyId/{id}", consumes = "application/json")
    List<AppUserDTO> getAllUsersByCompanyId(@PathVariable("id") Integer id);

    @RequestMapping(method = RequestMethod.POST, value = "/api/companies/create", consumes = "application/json")
    CompanyDTO createCompany(@RequestBody CompanyDTO companyDTO);
    @RequestMapping(method = RequestMethod.GET, value = "/api/companies/getCompanyById/{id}", consumes = "application/json")
    CompanyDTO getCompanyById(@PathVariable Integer id);

    @RequestMapping(method = RequestMethod.POST, value = "/api/addresses/create", consumes = "application/json")
    AddressDTO createAddress(@RequestBody AddressDTO addressDTO);
    @RequestMapping(method = RequestMethod.GET, value = "/api/addresses/getAddressById/{id}", consumes = "application/json")
    AddressDTO getAddressById(@PathVariable("id") Integer id);
    @RequestMapping(method = RequestMethod.GET, value = "/api/addresses/getAllAddressesByCompanyId/{id}", consumes = "application/json")
    List<AddressDTO> getAllAddressesByCompanyId(@PathVariable("id") Integer id);
}