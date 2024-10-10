package org.capaub.mscompany.controller;

import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company savedCompany = companyService.createCompany(company);
        return ResponseEntity.ok(savedCompany);
    }

    @GetMapping("/getCompanyById/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer id) {
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @GetMapping("/getCompanyBySiret/{siret}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String siret) {
        Company company = companyService.getCompanyBySiret(siret);
        return ResponseEntity.ok(company);
    }

}