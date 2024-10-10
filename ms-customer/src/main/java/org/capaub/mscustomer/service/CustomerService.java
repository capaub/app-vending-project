package org.capaub.mscustomer.service;

import lombok.AllArgsConstructor;
import org.capaub.mscustomer.entities.Customer;
import org.capaub.mscustomer.mapper.CustomerMapper;
import org.capaub.mscustomer.mapper.CustomerWithAddressMapper;
import org.capaub.mscustomer.repository.CustomerRepository;
import org.capaub.mscustomer.service.DTO.AddressDTO;
import org.capaub.mscustomer.service.DTO.CustomerDTO;
import org.capaub.mscustomer.service.DTO.CustomerWithAddressToSaveDTO;
import org.capaub.mscustomer.service.client.CompanyClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CompanyClient companyClient;
    private final CustomerWithAddressMapper customerWithAddressMapper;
    private final CustomerMapper customerMapper;

    // Méthode pour ajouter un nouveau client avec vérification
    public CustomerDTO createCustomer(CustomerWithAddressToSaveDTO customerWithAddressToSaveDTO) {
        // Vérification de l'existence par SIRET
        if (customerRepository.findBySiret(customerWithAddressToSaveDTO.getSiret()).isPresent()) {
            throw new IllegalArgumentException("Un client avec ce SIRET existe déjà.");
        }

        // Vérification de l'existence de la company
        if (companyClient.getCompanyById(customerWithAddressToSaveDTO.getCompanyId()) == null){
            throw new IllegalArgumentException("Company introuvable.");
        }

        AddressDTO addressDTO = companyClient.createAddress(
                customerWithAddressMapper.toAddressDTO(customerWithAddressToSaveDTO)
        );

        CustomerDTO customerDTOToSave = customerWithAddressMapper.toCustomerDTO(customerWithAddressToSaveDTO);
        customerDTOToSave.setAddressId(addressDTO.getId());
        Customer customerSaved = customerRepository.save(customerMapper.toCustomer(customerDTOToSave));

        return customerMapper.toCustomerDTO(customerSaved);
    }

    public Customer updateCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());
        if (existingCustomer.isPresent()) {
            return customerRepository.save(customer);
        } else {
            throw new IllegalArgumentException("Le client n'existe pas.");
        }
    }

    public Customer getCustomerById(Integer id) {
        if (customerRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Le client n'existe pas.");
        }
        return customerRepository.findById(id).get();
    }
    public List<Customer> getAllCustomersByCompanyId(Integer companyId) {
        if (companyClient.getCompanyById(companyId) == null){
            throw new IllegalArgumentException("Company introuvable.");
        }
        return customerRepository.findAllByCompanyId(companyId).get();
    }

    public void deleteCustomer(Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Le client n'existe pas.");
        }
    }
}
