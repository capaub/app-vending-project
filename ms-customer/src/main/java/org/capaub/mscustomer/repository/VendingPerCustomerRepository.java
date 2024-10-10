package org.capaub.mscustomer.repository;

import org.capaub.mscustomer.entities.VendingPerCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendingPerCustomerRepository extends JpaRepository<VendingPerCustomer, Integer> {
}
