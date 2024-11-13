package org.capaub.mscompany;

import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.repository.CompanyRepository;
import org.capaub.mscompany.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCompany_Success() {
        Company company = new Company();
        company.setSiret("123456789");

        when(companyRepository.findBySiret("123456789")).thenReturn(Optional.empty());
        when(companyRepository.save(company)).thenReturn(company);

        Company result = companyService.createCompany(company);

        assertNotNull(result);
        assertEquals("123456789", result.getSiret());
        verify(companyRepository, times(1)).findBySiret("123456789");
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testCreateCompany_AlreadyExists() {
        Company company = new Company();
        company.setSiret("123456789");

        when(companyRepository.findBySiret("123456789")).thenReturn(Optional.of(company));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            companyService.createCompany(company);
        });

        assertEquals("Cette compagnie existe déjà.", exception.getMessage());
        verify(companyRepository, times(1)).findBySiret("123456789");
        verify(companyRepository, never()).save(company);
    }

    @Test
    void testGetCompanyById_Success() {
        Company company = new Company();
        company.setId(1);

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));

        Company result = companyService.getCompanyById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(companyRepository, times(1)).findById(1);
    }

    @Test
    void testGetCompanyById_NotFound() {
        when(companyRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            companyService.getCompanyById(1);
        });

        assertEquals("Cette compagnie n'existe pas", exception.getMessage());
        verify(companyRepository, times(1)).findById(1);
    }

    @Test
    void testGetCompanyBySiret_Success() {
        Company company = new Company();
        company.setSiret("123456789");

        when(companyRepository.findBySiret("123456789")).thenReturn(Optional.of(company));

        Company result = companyService.getCompanyBySiret("123456789");

        assertNotNull(result);
        assertEquals("123456789", result.getSiret());
        verify(companyRepository, times(1)).findBySiret("123456789");
    }

    @Test
    void testGetCompanyBySiret_NotFound() {
        when(companyRepository.findBySiret("123456789")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            companyService.getCompanyBySiret("123456789");
        });

        assertEquals("Cette compagnie n'existe pas", exception.getMessage());
        verify(companyRepository, times(1)).findBySiret("123456789");
    }
}
