package org.capaub.mscompany;

import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.repository.AppUserRepository;
import org.capaub.mscompany.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        appUserRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    void testCreateCompany_Success() throws Exception {
        Company company = new Company();
        company.setSiret("123456789");
        company.setName("Test Company");

        mockMvc.perform(post("/api/companies/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.siret", is("123456789")))
                .andExpect(jsonPath("$.name", is("Test Company")));
    }

    @Test
    void testGetCompanyById_Success() throws Exception {
        Company company = new Company();
        company.setSiret("123456789");
        company.setName("Test Company");
        company = companyRepository.save(company);

        mockMvc.perform(get("/api/companies/getCompanyById/" + company.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.siret", is("123456789")))
                .andExpect(jsonPath("$.name", is("Test Company")));
    }

    @Test
    void testGetCompanyById_NotFound() throws Exception {
        mockMvc.perform(get("/api/companies/getCompanyById/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cette compagnie n'existe pas"));
    }

    @Test
    void testGetCompanyBySiret_Success() throws Exception {
        Company company = new Company();
        company.setSiret("123456789");
        company.setName("Test Company");
        company = companyRepository.save(company);

        mockMvc.perform(get("/api/companies/getCompanyBySiret/" + company.getSiret())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.siret", is("123456789")))
                .andExpect(jsonPath("$.name", is("Test Company")));
    }

    @Test
    void testGetCompanyBySiret_NotFound() throws Exception {
        mockMvc.perform(get("/api/companies/getCompanyBySiret/987654321")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cette compagnie n'existe pas"));
    }
}
