package org.capaub.mscompany.service;

import lombok.AllArgsConstructor;
import org.capaub.mscompany.entity.AppRole;
import org.capaub.mscompany.entity.AppUser;
import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.mapper.AppUserMapper;
import org.capaub.mscompany.repository.AddressRepository;
import org.capaub.mscompany.repository.CompanyRepository;
import org.capaub.mscompany.repository.AppUserRepository;
import org.capaub.mscompany.service.dto.AppUserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.*;

@Service
@AllArgsConstructor
public class AppUserService {
    private final PasswordEncoder passwordEncoder;

    private final AppUserRepository appUserRepository;
    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;
    private final AppUserMapper appUserMapper;


    public AppUserDTO createUser(AppUserDTO appUserDTO) {
        if (companyRepository.findById(appUserDTO.getCompanyId()).isEmpty()) {
            throw new IllegalArgumentException("Invalid company id");
        }

        if (appUserRepository.findByEmail(appUserDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà.");
        }

        Company company = companyRepository.findById(appUserDTO.getCompanyId()).get();

        AppUser appUser = appUserMapper.toAppUser(appUserDTO);
        appUser.setCompany(company);
        appUser.setAddress(null);
        if (appUserDTO.getPassword() != null) {
            appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        }
        appUser.setAuthorities(Collections.singletonList(AppRole.USER));
        AppUser appUserSaved = appUserRepository.save(appUser);

        return appUserMapper.toAppUserDTO(appUserSaved);
    }

    public AppUserDTO updateUser(AppUserDTO appUserDTO) {
        AppUser appUser = appUserMapper.toAppUser(appUserDTO);
        if (appUser.getAddress() != null) {
            if (appUser.getAddress().getId() == null) {
                addressRepository.save(appUser.getAddress());
            }
        } else {
            appUser.setAddress(null);
        }
        return appUserMapper.toAppUserDTO(appUserRepository.save(appUser));
    }

    public void updateConnectedAt(String email) {
        Optional<AppUser> appUserOpt = appUserRepository.findByEmail(email);
        if (appUserOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        AppUser appUser = appUserOpt.get();
        appUser.setConnectedAt(new Date());
        appUserRepository.save(appUser);  // Mettre à jour la dernière connexion
    }

    public AppUserDTO getUserByEmail(String email) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable."));
        return appUserMapper.toAppUserDTO(appUser);
    }

    public AppUserDTO getUserById(Integer id) {
        if (appUserRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Utilisateur introuvable.");
        }
        AppUser appUser = appUserRepository.findById(id).get();
        return appUserMapper.toAppUserDTO(appUser);
    }

    public List<AppUserDTO> getAllUsersByCompanyId(Integer companyId) {
        List<AppUser> appUsers = appUserRepository.findAllByCompanyId(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Aucun utilisateur trouvé."));

        return appUsers.stream()
                .map(appUserMapper::toAppUserDTO)
                .collect(Collectors.toList());
    }

}