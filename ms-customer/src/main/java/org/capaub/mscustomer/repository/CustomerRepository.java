package org.capaub.mscustomer.repository;

import org.capaub.mscustomer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findBySiret(String siret);

    Optional<List<Customer>> findAllByCompanyId(Integer customerId);
}
