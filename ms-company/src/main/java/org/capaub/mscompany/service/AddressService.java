package org.capaub.mscompany.service;

import lombok.AllArgsConstructor;
import org.capaub.mscompany.entity.Address;
import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.mapper.AddressMapper;
import org.capaub.mscompany.repository.AddressRepository;
import org.capaub.mscompany.repository.CompanyRepository;
import org.capaub.mscompany.service.dto.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final CompanyRepository companyRepository;
    private final AddressMapper addressMapper;

    public AddressDTO createAddress(AddressDTO addressDTO) {
        Company company = companyRepository.findById(addressDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Compagnie introuvable."));

        Address address = addressMapper.toAddress(addressDTO);
        address.setCompany(company);
        Address addressSaved = addressRepository.save(address);
        return addressMapper.toAddressDTO(addressSaved);
    }

    public AddressDTO getAddressById(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("L'adresse est introuvable."));
        return addressMapper.toAddressDTO(address);
    }

    public List<AddressDTO> getAllAddressesByCompanyId(Integer companyId) {
        List<Address> addresses = addressRepository.findAllByCompanyId(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Aucune adresse trouvée pour cette compagnie."));

        return addresses.stream()
                .map(addressMapper::toAddressDTO)
                .collect(Collectors.toList());
    }


    // autres méthodes...
}