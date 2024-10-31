package org.capaub.mscompany.service;

import org.capaub.mscompany.entity.Company;
import org.capaub.mscompany.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company) {
        if (companyRepository.findBySiret(company.getSiret()).isPresent()) {
            throw new IllegalArgumentException("Cette companie existe déjà.");
        }
        return companyRepository.save(company);
    }

    public Company getCompanyById(Integer id) {
        if (companyRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Cette companie n'existe pas");
        }
        return companyRepository.findById(id).get();
    }

    public Company getCompanyBySiret(String siret) {
        if (companyRepository.findBySiret(siret).isEmpty()) {
            throw new IllegalArgumentException("Cette companie n'existe pas");
        }
        return companyRepository.findBySiret(siret).get();
    }
}