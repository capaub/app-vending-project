package org.capaub.mscompany;
import org.capaub.mscompany.entity.Address;
import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.mapper.AddressMapper;
import org.capaub.mscompany.service.dto.AddressDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class AddressMapperTest {

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void testToAddressDTO() {
        Address address = new Address();
        address.setId(1);
        address.setStreetAddress("123 Main St");
        address.setCity("Paris");

        Company company = new Company();
        company.setId(5);
        address.setCompany(company);

        AddressDTO addressDTO = addressMapper.toAddressDTO(address);

        assertNotNull(addressDTO);
        assertEquals(address.getId(), addressDTO.getId());
        assertEquals(address.getStreetAddress(), addressDTO.getStreetAddress());
        assertEquals(address.getCity(), addressDTO.getCity());
        assertEquals(address.getCompany().getId(), addressDTO.getCompanyId());
    }

    @Test
    void testToAddress() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1);
        addressDTO.setStreetAddress("123 Main St");
        addressDTO.setCity("Paris");
        addressDTO.setCompanyId(5);

        Address address = addressMapper.toAddress(addressDTO);

        assertNotNull(address);
        assertEquals(addressDTO.getId(), address.getId());
        assertEquals(addressDTO.getStreetAddress(), address.getStreetAddress());
        assertEquals(addressDTO.getCity(), address.getCity());
        assertEquals(addressDTO.getCompanyId(), address.getCompany().getId());
    }
}
