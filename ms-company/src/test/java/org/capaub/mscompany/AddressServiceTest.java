package org.capaub.mscompany;import org.capaub.mscompany.entity.Address;
import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.mapper.AddressMapper;
import org.capaub.mscompany.repository.AddressRepository;
import org.capaub.mscompany.repository.CompanyRepository;
import org.capaub.mscompany.service.AddressService;
import org.capaub.mscompany.service.dto.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAddress_Success() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCompanyId(1);

        Company company = new Company();
        Address address = new Address();
        Address savedAddress = new Address();

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        when(addressMapper.toAddress(addressDTO)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(savedAddress);
        when(addressMapper.toAddressDTO(savedAddress)).thenReturn(addressDTO);

        AddressDTO result = addressService.createAddress(addressDTO);

        assertNotNull(result);
        verify(companyRepository, times(1)).findById(1);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    void testCreateAddress_CompanyNotFound() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCompanyId(1);

        when(companyRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            addressService.createAddress(addressDTO);
        });

        assertEquals("Compagnie introuvable.", exception.getMessage());
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void testGetAddressById_Success() {
        Address address = new Address();
        AddressDTO addressDTO = new AddressDTO();

        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(addressMapper.toAddressDTO(address)).thenReturn(addressDTO);

        AddressDTO result = addressService.getAddressById(1);

        assertNotNull(result);
        verify(addressRepository, times(1)).findById(1);
    }

    @Test
    void testGetAddressById_NotFound() {
        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            addressService.getAddressById(1);
        });

        assertEquals("L'adresse est introuvable.", exception.getMessage());
    }

    @Test
    void testGetAllAddressesByCompanyId_Success() {
        Address address = new Address();
        AddressDTO addressDTO = new AddressDTO();
        List<Address> addresses = List.of(address);

        when(addressRepository.findAllByCompanyId(1)).thenReturn(Optional.of(addresses));
        when(addressMapper.toAddressDTO(address)).thenReturn(addressDTO);

        List<AddressDTO> result = addressService.getAllAddressesByCompanyId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(addressRepository, times(1)).findAllByCompanyId(1);
    }

    @Test
    void testGetAllAddressesByCompanyId_NotFound() {
        when(addressRepository.findAllByCompanyId(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            addressService.getAllAddressesByCompanyId(1);
        });

        assertEquals("Aucune adresse trouvée pour cette compagnie.", exception.getMessage());
    }
}
