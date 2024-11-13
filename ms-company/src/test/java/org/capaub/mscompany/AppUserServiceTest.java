package org.capaub.mscompany;

import org.capaub.mscompany.entity.AppUser;
import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.mapper.AppUserMapper;
import org.capaub.mscompany.repository.AppUserRepository;
import org.capaub.mscompany.repository.CompanyRepository;
import org.capaub.mscompany.service.AppUserService;
import org.capaub.mscompany.service.dto.AppUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppUserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private AppUserMapper appUserMapper;

    @InjectMocks
    private AppUserService appUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setEmail("test@example.com");
        appUserDTO.setCompanyId(1);
        appUserDTO.setPassword("password");

        Company company = new Company();
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        when(appUserRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(appUserMapper.toAppUser(appUserDTO)).thenReturn(appUser);
        when(appUserRepository.save(appUser)).thenReturn(appUser);
        when(appUserMapper.toAppUserDTO(appUser)).thenReturn(appUserDTO);

        AppUserDTO result = appUserService.createUser(appUserDTO);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(appUserRepository, times(1)).save(appUser);
    }

    @Test
    void testCreateUser_WithValidCompany_Success() {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setEmail("test@example.com");
        appUserDTO.setCompanyId(1);
        appUserDTO.setPassword("password");

        Company company = new Company();
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        when(appUserRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(appUserMapper.toAppUser(appUserDTO)).thenReturn(appUser);
        when(appUserRepository.save(appUser)).thenReturn(appUser);
        when(appUserMapper.toAppUserDTO(appUser)).thenReturn(appUserDTO);

        AppUserDTO result = appUserService.createUser(appUserDTO);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(appUserRepository, times(1)).save(appUser);
    }

    @Test
    void testCreateUser_CompanyDoesNotExist() {
        // Arrange
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setEmail("test@example.com");
        appUserDTO.setCompanyId(1);

        when(companyRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            appUserService.createUser(appUserDTO);
        });

        assertEquals("Invalid company id", exception.getMessage());
        verify(appUserRepository, never()).save(any(AppUser.class));
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setEmail("test@example.com");
        appUserDTO.setCompanyId(1);

        Company company = new Company();
        AppUser existingUser = new AppUser();

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        when(appUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            appUserService.createUser(appUserDTO);
        });

        assertEquals("Un utilisateur avec cet email existe déjà.", exception.getMessage());
        verify(appUserRepository, never()).save(any(AppUser.class));
    }

    @Test
    void testUpdateUser_Success() {
        AppUserDTO appUserDTO = new AppUserDTO();
        AppUser appUser = new AppUser();

        when(appUserMapper.toAppUser(appUserDTO)).thenReturn(appUser);
        when(appUserRepository.save(appUser)).thenReturn(appUser);
        when(appUserMapper.toAppUserDTO(appUser)).thenReturn(appUserDTO);

        AppUserDTO result = appUserService.updateUser(appUserDTO);

        assertNotNull(result);
        verify(appUserRepository, times(1)).save(appUser);
    }

    @Test
    void testUpdateConnectedAt_UserExists() {
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");

        when(appUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(appUser));

        appUserService.updateConnectedAt("test@example.com");

        verify(appUserRepository, times(1)).save(appUser);
        assertNotNull(appUser.getConnectedAt());
    }

    @Test
    void testGetUserByEmail_Success() {
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setEmail("test@example.com");

        when(appUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(appUser));
        when(appUserMapper.toAppUserDTO(appUser)).thenReturn(appUserDTO);

        AppUserDTO result = appUserService.getUserByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testGetUserByEmail_NotFound() {
        when(appUserRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            appUserService.getUserByEmail("test@example.com");
        });

        assertEquals("Utilisateur introuvable.", exception.getMessage());
    }

    @Test
    void testGetUserById_Success() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        AppUserDTO appUserDTO = new AppUserDTO();

        when(appUserRepository.findById(1)).thenReturn(Optional.of(appUser));
        when(appUserMapper.toAppUserDTO(appUser)).thenReturn(appUserDTO);

        AppUserDTO result = appUserService.getUserById(1);

        assertNotNull(result);
    }

    @Test
    void testGetUserById_NotFound() {
        when(appUserRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            appUserService.getUserById(1);
        });

        assertEquals("Utilisateur introuvable.", exception.getMessage());
    }

    @Test
    void testGetAllUsersByCompanyId_Success() {
        AppUser appUser = new AppUser();
        appUser.setCompany(new Company());
        List<AppUser> appUsers = List.of(appUser);
        AppUserDTO appUserDTO = new AppUserDTO();

        when(appUserRepository.findAllByCompanyId(1)).thenReturn(Optional.of(appUsers));
        when(appUserMapper.toAppUserDTO(appUser)).thenReturn(appUserDTO);

        List<AppUserDTO> result = appUserService.getAllUsersByCompanyId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteUser_Success() {
        appUserService.deleteUser(1);
        verify(appUserRepository, times(1)).deleteById(1);
    }
}