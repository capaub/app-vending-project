package org.capaub.mscompany;

import org.capaub.mscompany.entity.Address;
import org.capaub.mscompany.entity.AppUser;
import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.repository.AddressRepository;
import org.capaub.mscompany.repository.AppUserRepository;
import org.capaub.mscompany.repository.CompanyRepository;
import org.capaub.mscompany.service.dto.AppUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AppUserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        appUserRepository.deleteAll();
        addressRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    void testGetUserById_Success() throws Exception {
        Company company = new Company();
        company.setSiret("123456789");
        company.setName("Test Company");
        company = companyRepository.save(company);

        AppUser user = new AppUser();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setCompany(company);
        user = appUserRepository.save(user);

        mockMvc.perform(get("/api/users/getUserById/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is("John")))
                .andExpect(jsonPath("$.lastname", is("Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        mockMvc.perform(get("/api/users/getUserById/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Utilisateur introuvable."));
    }

    @Test
    void testGetUserByEmail_Success() throws Exception {
        Company company = new Company();
        company.setSiret("987654321");
        company.setName("Another Test Company");
        company = companyRepository.save(company);

        AppUser user = new AppUser();
        user.setFirstname("Jane");
        user.setLastname("Smith");
        user.setEmail("jane.smith@example.com");
        user.setCompany(company);
        user = appUserRepository.save(user);

        mockMvc.perform(get("/api/users/getUserByEmail/" + user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is("Jane")))
                .andExpect(jsonPath("$.lastname", is("Smith")))
                .andExpect(jsonPath("$.email", is("jane.smith@example.com")));
    }

    @Test
    void testGetUserByEmail_NotFound() throws Exception {
        mockMvc.perform(get("/api/users/getUserByEmail/unknown@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Utilisateur introuvable."));
    }

    @Test
    void testGetAllUsersByCompanyId_Success() throws Exception {
        Company company = new Company();
        company.setSiret("123456789");
        company.setName("Test Company");
        company = companyRepository.save(company);

        AppUser user1 = new AppUser();
        user1.setFirstname("John");
        user1.setLastname("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setCompany(company);

        AppUser user2 = new AppUser();
        user2.setFirstname("Jane");
        user2.setLastname("Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setCompany(company);

        appUserRepository.saveAll(List.of(user1, user2));

        mockMvc.perform(get("/api/users/getAllUsersByCompanyId/" + company.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstname", is("John")))
                .andExpect(jsonPath("$[1].firstname", is("Jane")));
    }

    @Test
    void testGetAllUsersByCompanyId_NoUsersFound() throws Exception {
        Company company = new Company();
        company.setSiret("987654321");
        company.setName("Empty Company");
        company = companyRepository.save(company);

        mockMvc.perform(get("/api/users/company/" + company.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    void testUpdateConnectedAt_Success() throws Exception {
        AppUser user = new AppUser();
        user.setFirstname("Jane");
        user.setLastname("Doe");
        user.setEmail("jane.doe@example.com");
        user.setConnectedAt(null);
        user = appUserRepository.save(user);

        mockMvc.perform(put("/api/users/" + user.getEmail() + "/update-connected-at" )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        AppUser updatedUser = appUserRepository.findById(user.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getConnectedAt());
    }

    @Test
    void testUpdateConnectedAt_UserNotFound() throws Exception {
        mockMvc.perform(put("/api/users/nonexistent@example.com/update-connected-at")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        AppUser user = new AppUser();
        user.setFirstname("Jane");
        user.setLastname("Doe");
        user.setEmail("jane.doe@example.com");
        user = appUserRepository.save(user);

        mockMvc.perform(delete("/api/users/delete/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertFalse(appUserRepository.findById(user.getId()).isPresent());
    }

    @Test
    void testDeleteUser_UserNotFound() throws Exception {
        mockMvc.perform(delete("/api/users/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
