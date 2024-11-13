package org.capaub.mscompany.service;

import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company) {
        Optional<Company> optionalCompany = companyRepository.findBySiret(company.getSiret());
        if (optionalCompany.isPresent()) {
            throw new IllegalArgumentException("Cette compagnie existe déjà.");
        }
        return companyRepository.save(company);
    }

    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) {
            throw new IllegalArgumentException("Cette compagnie n'existe pas");
        }
        return optionalCompany.get();
    }

    public Company getCompanyBySiret(String siret) {
        Optional<Company> optionalCompany = companyRepository.findBySiret(siret);
        if (optionalCompany.isEmpty()) {
            throw new IllegalArgumentException("Cette compagnie n'existe pas");
        }
        return optionalCompany.get();
    }
}