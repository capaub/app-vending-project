package org.capaub.sprint_chatjs.repository;

import org.capaub.sprint_chatjs.entity.VendingPerCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendingPerCustomerRepository extends JpaRepository<VendingPerCustomer,Integer> {


}