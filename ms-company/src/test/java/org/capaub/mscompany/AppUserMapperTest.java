package org.capaub.mscompany;

import org.capaub.mscompany.entity.AppUser;
import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.entity.Address;
import org.capaub.mscompany.entity.AppRole;
import org.capaub.mscompany.mapper.AppUserMapper;
import org.capaub.mscompany.service.dto.AppUserDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AppUserMapperTest {

    private final AppUserMapper appUserMapper = Mappers.getMapper(AppUserMapper.class);

    @Test
    void testToAppUserDTO() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setEmail("user@example.com");

        Address address = new Address();
        address.setId(2);
        appUser.setAddress(address);

        Company company = new Company();
        company.setId(10);
        appUser.setCompany(company);

        appUser.setAuthorities(new ArrayList<>(Set.of(AppRole.USER)));

        AppUserDTO appUserDTO = appUserMapper.toAppUserDTO(appUser);

        assertNotNull(appUserDTO);
        assertEquals(appUser.getId(), appUserDTO.getId());
        assertEquals(appUser.getEmail(), appUserDTO.getEmail());
        assertEquals(appUser.getAddress().getId(), appUserDTO.getAddressId());
        assertEquals(appUser.getCompany().getId(), appUserDTO.getCompanyId());
        assertEquals(1, appUserDTO.getAuthorities().size());
        assertTrue(appUserDTO.getAuthorities().contains("USER"));
    }

    @Test
    void testToAppUser() {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setId(1);
        appUserDTO.setEmail("user@example.com");
        appUserDTO.setAddressId(2);
        appUserDTO.setCompanyId(10);
        appUserDTO.setAuthorities(new ArrayList<>(Set.of("USER")));

        AppUser appUser = appUserMapper.toAppUser(appUserDTO);

        assertNotNull(appUser);
        assertEquals(appUserDTO.getId(), appUser.getId());
        assertEquals(appUserDTO.getEmail(), appUser.getEmail());
        assertEquals(appUserDTO.getAddressId(), appUser.getAddress().getId());
        assertEquals(appUserDTO.getCompanyId(), appUser.getCompany().getId());
        assertEquals(1, appUser.getAuthorities().size());
        assertTrue(appUser.getAuthorities().contains(AppRole.USER));
    }
}
