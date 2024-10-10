package org.capaub.mswebapp.service;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.dto.AddressDTO;
import org.capaub.mswebapp.service.dto.AppUserDTO;
import org.capaub.mswebapp.service.dto.CompanyDTO;
import org.capaub.mswebapp.service.client.CompanyClient;
import org.capaub.mswebapp.service.dto.CompleteRegistrationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppUserService {

    private CompanyClient companyClient;

    public AppUserDTO completeRegistration(CompleteRegistrationDTO completeRegistrationDTO) {
        CompanyDTO companyDTOToSave = new CompanyDTO();
        companyDTOToSave.setName(completeRegistrationDTO.getName());
        companyDTOToSave.setSiret(completeRegistrationDTO.getSiret());
        CompanyDTO companyDTO = companyClient.createCompany(companyDTOToSave);

        AddressDTO addressDTOToSave = new AddressDTO();
        addressDTOToSave.setCountry(completeRegistrationDTO.getCountry());
        addressDTOToSave.setPostalCode(completeRegistrationDTO.getPostalCode());
        addressDTOToSave.setCity(completeRegistrationDTO.getCity());
        addressDTOToSave.setPostalCode(completeRegistrationDTO.getPostalCode());
        addressDTOToSave.setCompanyId(companyDTO.getId());
        AddressDTO addressDTO = companyClient.createAddress(addressDTOToSave);

        AppUserDTO appUserDTOToSave = new AppUserDTO();
        appUserDTOToSave.setFirstname(completeRegistrationDTO.getFirstname());
        appUserDTOToSave.setLastname(completeRegistrationDTO.getLastname());
        appUserDTOToSave.setEmail(completeRegistrationDTO.getEmail());
        appUserDTOToSave.setPassword(completeRegistrationDTO.getPassword());
        appUserDTOToSave.setCompanyId(companyDTO.getId());
        appUserDTOToSave.setAddressId(addressDTO.getId());

        return companyClient.createUser(appUserDTOToSave);
    }


    public List<AppUserDTO> getAllUsersByCompanyId(Integer companyId) {
        return companyClient.getAllUsersByCompanyId(companyId);
    }
}
