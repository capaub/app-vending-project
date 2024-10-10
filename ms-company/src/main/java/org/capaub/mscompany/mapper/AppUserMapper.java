package org.capaub.mscompany.mapper;


import org.capaub.mscompany.entity.AppRole;
import org.capaub.mscompany.entity.AppUser;
import org.capaub.mscompany.service.dto.AppUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "authorities", target = "authorities")
    AppUserDTO toAppUserDTO(AppUser appUser);
    @Mapping(source = "addressId", target = "address.id")
    @Mapping(source = "companyId", target = "company.id")
    @Mapping(source = "authorities", target = "authorities")
    AppUser toAppUser(AppUserDTO appUserDTO);

    default String map(AppRole role) {
        return role.name();
    }

    default AppRole map(String role) {
        return AppRole.valueOf(role);
    }
}
