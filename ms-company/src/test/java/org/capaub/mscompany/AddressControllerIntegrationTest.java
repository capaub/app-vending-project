package org.capaub.mscompany;
import org.capaub.mscompany.entity.Address;
import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.repository.AddressRepository;
import org.capaub.mscompany.repository.CompanyRepository;
import org.capaub.mscompany.service.dto.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    void testCreateAddress_Success() throws Exception {
        Company company = new Company();
        company.setSiret("123456789");
        company.setName("Test Company");
        company = companyRepository.save(company);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreetAddress("123 Main St");
        addressDTO.setCity("Paris");
        addressDTO.setPostalCode("75001");
        addressDTO.setCountry("France");
        addressDTO.setCompanyId(company.getId());

        // Act & Assert: Exécute une requête POST pour créer l'adresse
        mockMvc.perform(post("/api/addresses/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.streetAddress", is("123 Main St")))
                .andExpect(jsonPath("$.city", is("Paris")))
                .andExpect(jsonPath("$.companyId", is(company.getId())));
    }

    @Test
    void testGetAddressById_Success() throws Exception {
        // Arrange: Crée une compagnie et une adresse associée
        Company company = new Company();
        company.setSiret("123456789");
        company.setName("Test Company");
        company = companyRepository.save(company);

        Address address = new Address();
        address.setStreetAddress("123 Main St");
        address.setCity("Paris");
        address.setPostalCode("75001");
        address.setCountry("France");
        address.setCompany(company);
        address = addressRepository.save(address);

        // Act & Assert: Exécute une requête GET pour récupérer l'adresse par son ID
        mockMvc.perform(get("/api/addresses/getAddressById/" + address.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.streetAddress", is("123 Main St")))
                .andExpect(jsonPath("$.city", is("Paris")))
                .andExpect(jsonPath("$.postalCode", is("75001")))
                .andExpect(jsonPath("$.companyId", is(company.getId())));
    }

    @Test
    void testGetAddressById_NotFound() throws Exception {
        mockMvc.perform(get("/api/addresses/getAddressById/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("L'adresse est introuvable."));
    }

    @Test
    void testGetAllAddressesByCompanyId_Success() throws Exception {
        // Arrange: Crée une compagnie et deux adresses associées
        Company company = new Company();
        company.setSiret("123456789");
        company.setName("Test Company");
        company = companyRepository.save(company);

        Address address1 = new Address();
        address1.setStreetAddress("123 Main St");
        address1.setCity("Paris");
        address1.setPostalCode("75001");
        address1.setCountry("France");
        address1.setCompany(company);

        Address address2 = new Address();
        address2.setStreetAddress("456 Side St");
        address2.setCity("Lyon");
        address2.setPostalCode("69001");
        address2.setCountry("France");
        address2.setCompany(company);

        addressRepository.saveAll(List.of(address1, address2));

        mockMvc.perform(get("/api/addresses/getAllAddressesByCompanyId/" + company.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].streetAddress", is("123 Main St")))
                .andExpect(jsonPath("$[1].streetAddress", is("456 Side St")));
    }
}